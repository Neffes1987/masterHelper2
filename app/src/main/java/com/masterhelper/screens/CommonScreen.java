package com.masterhelper.screens;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.propertyBar.PropertyBar;

public abstract class CommonScreen extends AppCompatActivity {
  Boolean isInit = false;
  Menu actionBarMenu;
  Integer[] actionBarMenuItems;
  ActionBar bar;

  public void setActionBarTitle(String toolbarTitle) {
    init();
    bar.setTitle(toolbarTitle);
  }

  public void setActionBarTitle(int toolbarTitle) {
    init();
    bar.setTitle(toolbarTitle);
  }

  public void showBackButton(Boolean isVisible) {
    init();
    bar.setDisplayShowHomeEnabled(isVisible);
    bar.setDisplayHomeAsUpEnabled(isVisible);
  }

  private void init() {
    bar = getSupportActionBar();
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (!isInit) {
      onInitScreen();

      isInit = true;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);

    actionBarMenu = menu;

    if (actionBarMenuItems != null) {
      for (Integer title : this.actionBarMenuItems) {
        actionBarMenu.add(title);
      }
      return true;
    }

    return true;
  }

  protected abstract void onInitScreen();

  public void addContextMenuItems(Integer[] actionBarMenuItems) {
    this.actionBarMenuItems = actionBarMenuItems;
  }

  @Override
  public boolean onSupportNavigateUp() {
    super.onSupportNavigateUp();

    onBackPressed();

    return true;
  }

  public void saveSetting(Setting settingName, String value) {
    SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

    preferences.edit().putString(settingName.name(), value).apply();
  }

  public void cleanSetting(Setting settingName) {
    SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

    preferences.edit().putString(settingName.name(), "").apply();
  }

  public String getSetting(Setting settingName) {
    SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

    if (preferences.contains(settingName.name())) {
      return preferences.getString(settingName.name(), "");
    }

    return "";
  }

  public enum Setting {
    JourneyId
  }

  protected PropertyBar initProperty(int fragmentId, int labelId, String text, String tag, ContextPopupMenuBuilder popupBuilder) {
    PropertyBar property = new PropertyBar(getSupportFragmentManager().findFragmentById(fragmentId));
    property.setLabel(labelId);
    property.setTitle(text);
    property.setCardContextMenu(popupBuilder.cloneBuilder(tag));

    return property;
  }
}