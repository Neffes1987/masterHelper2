package com.masterhelper.goals;

import android.content.Intent;
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
import java.util.Arrays;

import static com.masterhelper.goals.GoalLocale.getLocalizationByKey;

public class PageGoal extends AppCompatActivity {
  public static final String INTENT_GOAL_ID = "goalId";
  public static final int INTENT_RESULT_ID = 10000;

  GoalModel currentGoal;

  FragmentManager mn;
  GoalRepository repository;

  ComponentUIDialog dialog;

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

    void initUpdateButton(){
      ComponentUIFloatingButton floatingButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.GOAL_EDIT_ID));
      floatingButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.pencil));
      floatingButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
      floatingButton.controls.setOnClick(new SetBtnLocation() {
        @Override
        public void onClick(int btnId, String tag) {
          dialog.setTitle(GoalLocale.getLocalizationByKey(GoalLocale.Keys.updateScene));
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
      LocationModel attachedLocation = GlobalApplication.getAppDB().locationRepository.getRecord(locationId);
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_goal);
    String goalId = getIntent().getStringExtra(INTENT_GOAL_ID);
    mn = getSupportFragmentManager();
    repository = GlobalApplication.getAppDB().goalRepository;
    currentGoal = repository.getRecord(goalId);
    dialog = initDialog(
      repository.getNameLength(),
      repository.getDescriptionLength()
    );

    setAppBarLabel(currentGoal.name.get(), currentGoal.progressToString());
    setDescriptionLabel(currentGoal.description.get());
    initUpdateButton();
    initLocationMeta(currentGoal.assignedLocation.toString());
    initSelectLocationBtn();
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
}