package com.masterhelper.goals;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.masterhelper.media.filesystem.AudioPlayer;
import com.masterhelper.media.filesystem.EffectsPlayer;
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.global.autocomplitefield.editField.EditTextField;

import java.util.Objects;

public class PageGoal extends AppMenuActivity implements View.OnClickListener {
  public static final String INTENT_GOAL_ID = "goalId";
  TabLayout tabs;
  int goalCurrentTab = 1;

  FloatingActionButton applyBtn;

  GoalModel currentGoal;

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
    tabs = findViewById(R.id.GOAL_TABS_BAR_ID);
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_goal);
    setItemControlTitle(GoalLocale.getLocalizationByKey(GoalLocale.Keys.updateGoal));

    applyBtn = findViewById(R.id.GOAL_APPLY_BTN);
    applyBtn.setVisibility(View.GONE);
    applyBtn.setOnClickListener(this);
    toggleTab(goalCurrentTab);

    player = GlobalApplication.getPlayer();
    effectsPlayer = GlobalApplication.getEffectsPlayer();
    String goalId = getIntent().getStringExtra(INTENT_GOAL_ID);
    mn = getSupportFragmentManager();
    repository = GlobalApplication.getAppDB().goalRepository;
    currentGoal = repository.getRecord(goalId);

    UIToolbar.setTitle(this, currentGoal.name.get(), "");
    setDescriptionLabel(currentGoal.description.get());

    initTabs();
  }


  @Override
  protected void onResume() {
    super.onResume();
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
      toggleTab(1);
      tabs.selectTab(tabs.getTabAt(0), true);
    }
  }
}