package com.masterhelper.ux.components.library.appBar;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.R;
import com.masterhelper.locations.PageLocationsList;
import com.masterhelper.media.FileViewerWidget;
import org.jetbrains.annotations.NotNull;

public abstract class AppMenuActivity extends AppCompatActivity {
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.app_bar_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
    Intent openSettingsScreen;
    switch (item.getItemId()){
      case R.id.MENU_LOCATIONS_ID:
        openSettingsScreen = new Intent(this, PageLocationsList.class);
        startActivity(openSettingsScreen);
        break;
      case R.id.MENU_ADD_SOUNDS_ID:
        openSettingsScreen = FileViewerWidget.getWidgetIntent(
          this,
          FileViewerWidget.Formats.audio,
          FileViewerWidget.Layout.global,
          null
        );
        startActivity(openSettingsScreen);
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
