package com.masterhelper.goals;

import android.content.Intent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.goals.repository.GoalModel;
import com.masterhelper.goals.repository.GoalRepository;
import com.masterhelper.locations.PageControlsListener;
import com.masterhelper.locations.PageLocationsList;
import com.masterhelper.locations.repository.LocationModel;
import com.masterhelper.media.Formats;
import com.masterhelper.media.filesystem.AppFilesLibrary;
import com.masterhelper.media.filesystem.AudioPlayer;
import com.masterhelper.media.filesystem.EffectsPlayer;
import com.masterhelper.media.music_player.IMusicPlayerWidget;
import com.masterhelper.media.repository.MediaModel;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.image.ComponentUIImage;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.masterhelper.goals.GoalLocale.getLocalizationByKey;
import static com.masterhelper.media.filesystem.AppFilesLibrary.FORMAT_AUDIO_PATH;

public class PageGoal extends AppCompatActivity implements IMusicPlayerWidget {
  public static final String INTENT_GOAL_ID = "goalId";
  public static final int INTENT_RESULT_ID = 10000;
  public static final int GOAL_LOCATION_PLAYER_ID = R.id.GOAL_MUSIC_PLAYER_ID;

  GoalModel currentGoal;
  LocationModel attachedLocation;

  FragmentManager mn;
  GoalRepository repository;

  View locationPlayerWidget;

  ComponentUIDialog dialog;
  AudioPlayer player;
  EffectsPlayer effectsPlayer;
  AppFilesLibrary library;

  private ComponentUILabel description;

  void setAppBarLabel(String newName, String newProgress) {
    UIToolbar.setTitle(this, newName, newProgress);
  }

  void setDescriptionLabel(String newDescription) {
    if (description == null) {
      description = ComponentUILabel.cast(mn.findFragmentById(R.id.GOAL_DESCRIPTION_ID));
    }
    description.controls.setText(newDescription);
  }

  void initUpdateButton() {
    ComponentUIFloatingButton floatingButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.GOAL_EDIT_ID));
    floatingButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.pencil));
    floatingButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
    floatingButton.controls.setOnClick(new SetBtnLocation() {
      @Override
      public void onClick(int btnId, String tag) {
        dialog.setTitle(GoalLocale.getLocalizationByKey(GoalLocale.Keys.updateGoal));
        dialog.pNameField.setText(currentGoal.name.get());
        dialog.pDescriptionField.setText(currentGoal.description.get());
        int selectedProgressOption = Arrays.asList(GoalModel.dialogProgressOptionsValues).indexOf(currentGoal.progress.get());
        dialog.pRadioGroup.setSelectedItem(selectedProgressOption);
        dialog.setListener(new ComponentUIDialog.DialogClickListener() {
          @Override
          public void onResolve() {
            GoalModel.GoalProgress selectedProgressOption = Arrays.asList(GoalModel.dialogProgressOptionsValues).get(dialog.pRadioGroup.getSelectedItemIndex());
            currentGoal.name.set(dialog.pNameField.getText());
            currentGoal.description.set(dialog.pDescriptionField.getText());
            currentGoal.progress.set(selectedProgressOption);
            currentGoal.save();

            setDescriptionLabel(currentGoal.description.get());
            setAppBarLabel(currentGoal.name.get(), currentGoal.progressToString());
          }

          @Override
          public void onReject() {

          }
        });
        dialog.show();
      }

      @Override
      public void onLongClick(int btnId) {

      }
    });
  }

  void initSelectLocationBtn() {
    ComponentUIImageButton selectLocationBtn = ComponentUIImageButton.cast(mn.findFragmentById(R.id.GOAL_ASSIGN_LOCATION_BTN_ID));
    selectLocationBtn.controls.setOnClick(new SetBtnLocation() {
      @Override
      public void onClick(int btnId, String tag) {
        Intent locationListIntent = new Intent(PageGoal.this, PageLocationsList.class);
        locationListIntent.putExtra(PageLocationsList.INTENT_LOCATION_SELECTION_MODE, 1);
        startActivityForResult(locationListIntent, INTENT_RESULT_ID);
      }

      @Override
      public void onLongClick(int btnId) {

      }
    });
  }

  void initLocationMeta(String locationId) {
    String title = getLocalizationByKey(GoalLocale.Keys.goalLocationPlaceholder);
    String locationUrl = "";
    if (locationId != null && locationId.length() != 0) {
      attachedLocation = GlobalApplication.getAppDB().locationRepository.getRecord(locationId);
      title = attachedLocation.name.get();
      locationUrl = attachedLocation.previewUrl.get();
    }
    ComponentUILabel locationTitle = ComponentUILabel.cast(mn.findFragmentById(R.id.GOAL_ASSIGNED_LOCATION_ID));
    locationTitle.controls.setText(title);
    locationTitle.controls.setOnClickListener(v -> {
      if (locationId != null && locationId.length() != 0) {
        Intent locationListIntent = new Intent(PageGoal.this, PageControlsListener.class);
        locationListIntent.putExtra(PageLocationsList.INTENT_LOCATION_ID, currentGoal.assignedLocation.get().toString());
        startActivity(locationListIntent);
      }
    });

    ComponentUIImage locationPreview = ComponentUIImage.cast(mn.findFragmentById(R.id.GOAL_ATTACHED_LOCATION_PREVIEW_ID));
    if (locationUrl != null && locationUrl.length() > 0) {
      locationPreview.controls.setFile(new File(locationUrl));
      locationPreview.controls.show();
    } else {
      locationPreview.controls.hide();
    }

  }

  ComponentUIDialog initDialog(int nameMaxLength, int descriptionLength) {
    ComponentUIDialog dialog = new ComponentUIDialog(this);
    dialog.pNameLabel.show();
    dialog.pNameLabel.setText(GoalLocale.getLocalizationByKey(GoalLocale.Keys.goalName));

    dialog.pNameField.setText("");
    dialog.pNameField.setMaxLength(nameMaxLength);
    dialog.pNameField.show();

    dialog.pDescriptionLabel.show();
    dialog.pDescriptionLabel.setText(GoalLocale.getLocalizationByKey(GoalLocale.Keys.shortDescription));

    dialog.pDescriptionField.setText("");
    dialog.pDescriptionLabel.setMaxLength(descriptionLength);
    dialog.pDescriptionField.show();

    dialog.pRadioGroup.setList(Arrays.asList(GoalModel.dialogProgressOptionsTitles));
    dialog.pRadioGroup.show();

    return dialog;
  }

  void reInitMusicPlayer() {
    if (attachedLocation == null || attachedLocation.getMusicIds().length == 0) {
      locationPlayerWidget.setVisibility(View.GONE);
      return;
    }

    locationPlayerWidget.setVisibility(View.VISIBLE);
    MediaModel[] mediaModels = library.getFilesLibraryList();
    Collection<String> currentSelectedUris = new ArrayList<>();
    Collection<String> currentSelectedList = new ArrayList<>(Arrays.asList(attachedLocation.getMusicIds()));
    for (MediaModel model : mediaModels) {
      if (currentSelectedList.contains(model.id.toString())) {
        currentSelectedUris.add(model.filePath.get());
      }
    }
    player.setMediaListOfUri(currentSelectedUris.toArray(new String[0]));

    Collection<String> currentEffectsUris = new ArrayList<>();
    Collection<String> currentEffectsSelectedList = new ArrayList<>(Arrays.asList(attachedLocation.getMusicEffectsIds()));
    for (MediaModel model : mediaModels) {
      if (currentEffectsSelectedList.contains(model.id.toString())) {
        currentEffectsUris.add(model.filePath.get());
      }
    }

    effectsPlayer.setMediaListOfUri(currentEffectsUris.toArray(new String[0]));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_goal);
    player = GlobalApplication.getPlayer();
    effectsPlayer = GlobalApplication.getEffectsPlayer();
    String goalId = getIntent().getStringExtra(INTENT_GOAL_ID);
    mn = getSupportFragmentManager();
    repository = GlobalApplication.getAppDB().goalRepository;
    currentGoal = repository.getRecord(goalId);
    dialog = initDialog(
      repository.getNameLength(),
      repository.getDescriptionLength()
    );

    locationPlayerWidget = findViewById(GOAL_LOCATION_PLAYER_ID);

    setAppBarLabel(currentGoal.name.get(), currentGoal.progressToString());
    setDescriptionLabel(currentGoal.description.get());
    initUpdateButton();

    initSelectLocationBtn();

    library = new AppFilesLibrary(FORMAT_AUDIO_PATH, Formats.audio);
  }

  @Override
  protected void onStart() {
    super.onStart();
    initLocationMeta(currentGoal.assignedLocation.toString());
    reInitMusicPlayer();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != RESULT_OK || data == null) {
      return;
    }
    String locationId = data.getStringExtra(PageLocationsList.INTENT_LOCATION_ID);

    if (locationId != null) {
      initLocationMeta(locationId);
      currentGoal.assignedLocation.fromString(locationId);
      currentGoal.save();
    }
  }

  @Override
  public void next() {
    player.startNextMediaFile();
    effectsPlayer.next();
  }

  @Override
  public void play() {
    player.startNextMediaFile();
    effectsPlayer.start();
  }

  @Override
  public void stop() {
    player.stopMediaRecord();
    effectsPlayer.stop();
  }

  @Override
  public String getCurrentTrackName() {
    File currentTrack = library.getFileByPosition(player.getCurrentAudioIndex());
    if (currentTrack == null) {
      return "";
    }
    return currentTrack.getName();
  }

  @Override
  public boolean checkIsPlaying() {
    return player.isPlayed();
  }
}