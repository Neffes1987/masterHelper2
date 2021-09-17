package com.masterhelper.screens.journey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.masterhelper.R;
import com.masterhelper.screens.CommonScreen;
import com.masterhelper.ux.ApplyButtonFragment;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.propertyBar.PropertyBar;
import com.masterhelper.ux.TextDialogBuilder;

public class JourneyEditorScreen extends CommonScreen {
  PropertyBar nameFragment;
  PropertyBar timeFragment;
  PropertyBar themeFragment;
  PropertyBar restrictionsFragment;

  ContextPopupMenuBuilder popupBuilder;
  TextDialogBuilder textDialogBuilder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_journey_editor_screen);

    setActionBarTitle(R.string.journey_edit_title);

    popupBuilder = new ContextPopupMenuBuilder(new String[]{
      getResources().getString(R.string.edit)
    });
    popupBuilder.setPopupMenuClickHandler(this::onPopupMenuItemClick);
  }

  protected void onInitScreen() {
    ApplyButtonFragment applyButtonFragment = (ApplyButtonFragment) getSupportFragmentManager().findFragmentById(R.id.APPLY_BUTTON_FRAGMENT_ID);
    if (applyButtonFragment != null) {
      applyButtonFragment.setListener(v -> {
        startActivity(CurrentJourneyScreen.getScreenIntent(this));
      });
    }

    nameFragment = initProperty(R.id.JOURNEY_TITLE_FRAGMENT_ID, R.string.journey_title, "");
    timeFragment = initProperty(R.id.JOURNEY_TIMELINE_FRAGMENT_ID, R.string.journey_time, "");
    themeFragment = initProperty(R.id.JOURNEY_THEME_FRAGMENT_ID, R.string.journey_theme, "");
    restrictionsFragment = initProperty(R.id.JOURNEY_RESTRICTIONS_FRAGMENT_ID, R.string.journey_restrictions, "");
  }

  boolean onPopupMenuItemClick(String tag, int menuItemIndex) {
    textDialogBuilder = new TextDialogBuilder(this);
    AlertDialog dialog = textDialogBuilder.setPositiveButton((v) -> {
        Log.i("TAG", "onCreate: " + tag);
        Log.i("TAG", "onCreate: " + menuItemIndex);
      })
      .setTitle("edit")
      .create();

    dialog.show();
    return true;
  }

  private PropertyBar initProperty(int fragmentId, int labelId, String text) {
    PropertyBar property = new PropertyBar(getSupportFragmentManager().findFragmentById(fragmentId));
    property.setLabel(labelId);
    property.setTitle(text);
    property.setCardContextMenu(popupBuilder.cloneBuilder(labelId + ""));

    return property;
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, JourneyEditorScreen.class);
  }
}