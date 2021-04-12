package com.masterhelper.goals;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.annotation.Nullable;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.global.autocomplitefield.editField.MultiAutofillEditTextField;
import com.masterhelper.global.autocomplitefield.label.TextLabel;
import com.masterhelper.goals.repository.GoalModel;
import com.masterhelper.goals.repository.GoalRepository;
import com.masterhelper.locations.PageControlsListener;
import com.masterhelper.locations.PageLocationsList;
import com.masterhelper.locations.repository.LocationModel;
import com.masterhelper.media.filesystem.AudioPlayer;
import com.masterhelper.media.filesystem.EffectsPlayer;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.image.ComponentUIImage;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.global.autocomplitefield.editField.EditTextField;

import java.io.File;

import static com.masterhelper.goals.GoalLocale.getLocalizationByKey;

public class PageGoal extends AppMenuActivity implements View.OnClickListener {
  public static final String INTENT_GOAL_ID = "goalId";
  public static final int INTENT_RESULT_ID = 10000;

  int goalCurrentTab = 1;

  FloatingActionButton applyBtn;
  FloatingActionButton playBtn;

  GoalModel currentGoal;
  LocationModel attachedLocation;

  FragmentManager mn;
  GoalRepository repository;

  AudioPlayer player;
  EffectsPlayer effectsPlayer;

  private TextLabel description;

  void setDescriptionLabel(String newDescription) {
    if (description == null) {
      description = new TextLabel(R.id.GOAL_DESCRIPTION_ID, this);
    }
    description.setLabel(newDescription, true);
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

  void initEditMode(GoalModel model) {
    EditTextField name = new EditTextField(R.id.GOAL_EDIT_NAME_FIELD_ID, this);
    name.setText(model.name.get());
    name.setMaxLength(repository.getNameLength());
    name.setMultiLIneText();

    MultiAutofillEditTextField description = new MultiAutofillEditTextField(R.id.GOAL_EDIT_DESCRIPTION_FIELD_ID, this, repository.getDescriptionLength());
    description.setText(model.description.get());
  }

  void toggleTab(int tabIndex) {
    LinearLayout editContainer = findViewById(R.id.GOAL_EDIT_CONTAINER_ID);
    ScrollView viewContainer = findViewById(R.id.GOAL_VIEW_CONTAINER_ID);
    if (tabIndex == 1) {
      applyBtn.setVisibility(View.GONE);
      editContainer.setVisibility(View.GONE);
      viewContainer.setVisibility(View.VISIBLE);
    } else {
      applyBtn.setVisibility(View.VISIBLE);
      editContainer.setVisibility(View.VISIBLE);
      viewContainer.setVisibility(View.GONE);
      initEditMode(currentGoal);
    }
  }

  void initTabs() {
    TabLayout tabs = findViewById(R.id.GOAL_TABS_BAR_ID);
    tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        goalCurrentTab = tabs.getSelectedTabPosition() + 1;
        toggleTab(goalCurrentTab);
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

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
        Intent locationListIntent = PageControlsListener.getIntent(this, currentGoal.assignedLocation.get().toString());
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_goal);
    setItemControlTitle(GoalLocale.getLocalizationByKey(GoalLocale.Keys.updateGoal));

    applyBtn = findViewById(R.id.GOAL_APPLY_BTN);
    applyBtn.setVisibility(View.GONE);
    applyBtn.setOnClickListener(this);

    playBtn = findViewById(R.id.GOAL_MUSIC_STOP_ID);
    playBtn.setVisibility(View.GONE);
    playBtn.setOnClickListener(this);
    toggleTab(goalCurrentTab);

    player = GlobalApplication.getPlayer();
    effectsPlayer = GlobalApplication.getEffectsPlayer();
    String goalId = getIntent().getStringExtra(INTENT_GOAL_ID);
    mn = getSupportFragmentManager();
    repository = GlobalApplication.getAppDB().goalRepository;
    currentGoal = repository.getRecord(goalId);

    UIToolbar.setTitle(this, currentGoal.name.get(), "");
    setDescriptionLabel(currentGoal.description.get());

    initSelectLocationBtn();
    initTabs();
  }

  @Override
  protected void onStart() {
    super.onStart();
    initLocationMeta(currentGoal.assignedLocation.toString());
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (playBtn != null) {
      playBtn.setVisibility(player.isPlayed() ? View.VISIBLE : View.GONE);
    }
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
  protected void onAppBarMenuItemControl() {
  }

  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  @Override
  public void onClick(View v) {
    if (v == applyBtn) {
      EditText name = findViewById(R.id.GOAL_EDIT_NAME_FIELD_ID);
      EditText description = findViewById(R.id.GOAL_EDIT_DESCRIPTION_FIELD_ID);

      currentGoal.name.set(name.getText().toString());
      currentGoal.description.set(description.getText().toString());
      currentGoal.save();

      setDescriptionLabel(currentGoal.description.get());
      UIToolbar.setTitle(this, currentGoal.name.get(), "");
    }

    if (v == playBtn) {
      player.stopMediaRecord();
      effectsPlayer.stop();
    }
  }
}