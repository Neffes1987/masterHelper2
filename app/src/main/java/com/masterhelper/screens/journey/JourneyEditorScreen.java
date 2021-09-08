package com.masterhelper.screens.journey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.masterhelper.R;
import com.masterhelper.screens.CommonScreen;
import com.masterhelper.ux.ApplyButtonFragment;
import com.masterhelper.ux.ContextPopupMenu;
import com.masterhelper.ux.PropertyBarFragment;

public class JourneyEditorScreen extends CommonScreen {
  String[] CONTEXT_MENU_ITEMS = {"Test"};

  PropertyBarFragment nameFragment;
  PropertyBarFragment timeFragment;
  PropertyBarFragment themeFragment;
  PropertyBarFragment restrictionsFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_journey_editor_screen);

    setActionBarTitle(R.string.journey_edit_title);
  }

  protected void onStart() {
    super.onStart();

    ApplyButtonFragment applyButtonFragment = (ApplyButtonFragment) getSupportFragmentManager().findFragmentById(R.id.APPLY_BUTTON_FRAGMENT_ID);
    if (applyButtonFragment != null) {
      applyButtonFragment.setListener(v -> {
        onBackPressed();
      });
    }

    nameFragment = initProperty(R.id.JOURNEY_TITLE_FRAGMENT_ID, R.string.journey_title, "");
    timeFragment = initProperty(R.id.JOURNEY_TIMELINE_FRAGMENT_ID, R.string.journey_time, "");
    themeFragment = initProperty(R.id.JOURNEY_THEME_FRAGMENT_ID, R.string.journey_theme, "");
    restrictionsFragment = initProperty(R.id.JOURNEY_RESTRICTIONS_FRAGMENT_ID, R.string.journey_restrictions, "");
  }

  private PropertyBarFragment initProperty(int fragmentId, int labelId, String text) {
    PropertyBarFragment property = (PropertyBarFragment) getSupportFragmentManager().findFragmentById(fragmentId);
    assert property != null;
    property.setLabel(labelId);
    property.setTitle(text);
    property.setOnPropertyBarClick((tag, menuItemName) -> {

    }, labelId, CONTEXT_MENU_ITEMS);

    return property;
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, JourneyEditorScreen.class);
  }
}