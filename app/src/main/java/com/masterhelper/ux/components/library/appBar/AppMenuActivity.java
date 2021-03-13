package com.masterhelper.ux.components.library.appBar;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.R;
import com.masterhelper.locations.PageLocationsList;
import com.masterhelper.media.FileViewerWidget;
import com.masterhelper.media.Formats;
import org.jetbrains.annotations.NotNull;

public abstract class AppMenuActivity extends AppCompatActivity {
  String itemControlTitle = "";
  Menu menu;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.app_bar_menu, menu);
    this.menu = menu;
    setMenuItemTitle(itemControlTitle);
    return super.onCreateOptionsMenu(menu);
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
      case R.id.MENU_ITEMS_CONTROLS_BUTTON_ID:
        onItemControl();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  protected abstract void onItemControl();
}
