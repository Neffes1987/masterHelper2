package com.masterhelper.ux.components.library.appBar;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.R;
import com.masterhelper.journeys.PageJourneyList;
import com.masterhelper.locations.PageLocationsList;
import com.masterhelper.media.FileViewerWidget;
import com.masterhelper.media.Formats;
import com.masterhelper.npc.NPCPageList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class AppMenuActivity extends AppCompatActivity {
  ArrayList<MENU_ITEMS_CODES> hiddenItemsCodes = new ArrayList<>();
  String itemControlTitle = "";
  Menu menu;

  public void setPageTitle(String title) {
    UIToolbar.setTitle(this, title, "");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.app_bar_menu, menu);
    this.menu = menu;
    setMenuItemTitle(itemControlTitle);
    hideCodes();
    return super.onCreateOptionsMenu(menu);
  }

  void hideCodes() {
    hiddenItemsCodes.forEach(code -> {
      int itemId = 0;
      switch (code) {
        case media:
          itemId = R.id.MENU_ADD_SOUNDS_ID;
          break;
        case create:
          itemId = R.id.MENU_ITEMS_CONTROLS_BUTTON_ID;
          break;
        case location:
          itemId = R.id.MENU_LOCATIONS_ID;
          break;
        case npc:
          itemId = R.id.MENU_NPC_ID;
          break;
      }
      if (itemId > 0) {
        MenuItem item = menu.findItem(itemId);
        item.setVisible(false);
      }
    });
  }

  protected void setHiddenItemsCode(MENU_ITEMS_CODES itemCode) {
    hiddenItemsCodes.add(itemCode);
  }

  void setMenuItemTitle(String title) {
    if (menu == null || title == null) {
      return;
    }

    MenuItem itemControl = menu.findItem(R.id.MENU_ITEMS_CONTROLS_BUTTON_ID);
    if (itemControl == null) {
      return;
    }

    itemControl.setTitle(title);
    itemControl.setVisible(title.length() > 0);
  }

  protected void setItemControlTitle(String title) {
    itemControlTitle = title;
    setMenuItemTitle(itemControlTitle);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
    Intent openSettingsScreen;
    switch (item.getItemId()) {
      case R.id.MENU_LOCATIONS_ID:
        openSettingsScreen = new Intent(this, PageLocationsList.class);
        startActivity(openSettingsScreen);
        break;
      case R.id.MENU_ADD_SOUNDS_ID:
        openSettingsScreen = FileViewerWidget.getWidgetIntent(
          this,
          Formats.audio,
          FileViewerWidget.Layout.global,
          null
        );
        startActivity(openSettingsScreen);
        break;
      case R.id.MENU_NPC_ID:
        openSettingsScreen = new Intent(this, NPCPageList.class);
        startActivity(openSettingsScreen);
        break;
      case R.id.MENU_ITEMS_CONTROLS_BUTTON_ID:
        onAppBarMenuItemControl();
        break;
      case R.id.MENU_ITEM_JOURNEYS_ID:
        openSettingsScreen = new Intent(this, PageJourneyList.class);
        startActivity(openSettingsScreen);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  protected abstract void onAppBarMenuItemControl();

  protected enum MENU_ITEMS_CODES {
    create,
    media,
    location,
    npc
  }
}
