package com.masterhelper.screens;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public abstract class CommonScreen extends AppCompatActivity {
  Boolean isInit = false;
  Menu actionBarMenu;
  Integer[] actionBarMenuItems;
  boolean asActions;

  ActionBar bar;

  public void setActionBarTitle(String toolbarTitle) {
    init();
    bar.setTitle(toolbarTitle);
  }

  public void setActionBarTitle(int toolbarTitle) {
    init();
    bar.setTitle(toolbarTitle);
  }

  public void setActionBarSubtitle(String subtitle) {
    bar.setSubtitle(subtitle);
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
        if (asActions) {
          MenuItem item = actionBarMenu.add("icon");
          item.setIcon(title);
          item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
          actionBarMenu.add(title);
        }
      }

      return true;
    }

    return true;
  }

  protected abstract void onInitScreen();

  public void addContextMenuItems(Integer[] actionBarMenuItems) {
    this.actionBarMenuItems = actionBarMenuItems;
    asActions = false;
  }

  public void addContextMenuItems(Integer[] actionBarMenuItems, Boolean asActions) {
    this.actionBarMenuItems = actionBarMenuItems;
    this.asActions = asActions;
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
}