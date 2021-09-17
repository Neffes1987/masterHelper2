package com.masterhelper.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.R;
import com.masterhelper.ux.ContextPopupMenuBuilder;

public abstract class CommonScreen extends AppCompatActivity {
  Boolean isInit = false;
  Menu actionBarMenu;
  Integer[] actionBarMenuItems;

  public void setActionBarTitle(int toolbarTitle) {
    ActionBar bar = getSupportActionBar();
    assert bar != null;
    bar.setTitle(toolbarTitle);
    bar.setDisplayShowHomeEnabled(true);
    bar.setDisplayHomeAsUpEnabled(true);
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
}