package com.masterhelper.ux.components.widgets.musicButton;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.masterhelper.filesystem.AudioPlayer;
import com.masterhelper.filesystem.FilesLocale;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.buttons.floating.FloatingButtonsPreset;
import com.masterhelper.ux.media.FileViewerWidget;

import static com.masterhelper.ux.media.FileViewerWidget.WIDGET_RESULT_CODE;
import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.musicStarted;
import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.primary;

public class WidgetMusicFloatingButton extends ComponentUIFloatingButton implements IMusicButton {
  AppCompatActivity currentActivity;
  boolean isMusicActive;

  void toggleMusicControl(){
    isMusicActive = !isMusicActive;
  }

  /**
   * check that component is instance of current ui fragment
   * @param uiComponent - fragment ui for testing
   */
  public static WidgetMusicFloatingButton cast(Fragment uiComponent) {
    if(uiComponent instanceof ComponentUIFloatingButton){
      return (WidgetMusicFloatingButton) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUIFloatingButton");
  }

  @Override
  public void init(AppCompatActivity currentActivity) {
    this.currentActivity = currentActivity;
    FloatingButtonsPreset.setPreset(FloatingButtonsPreset.Presets.music, this);
    this.controls.setOnClick((SetBtnEvent) currentActivity);
  }

  @Override
  public void setBackgroundMusicState(String[] musicList) {
    AudioPlayer player = GlobalApplication.getPlayer();
    toggleMusicControl();

    if(musicList.length == 0){
      Toast.makeText(currentActivity, FilesLocale.getLocalizationByKey(FilesLocale.Keys.emptyAudioFile), Toast.LENGTH_SHORT).show();
      isMusicActive = false;
    }

    player.setMediaListOfUri(musicList);

    if(isMusicActive){
      player.startNextMediaFile();
      this.controls.setIconColor(musicStarted );
    } else {
      player.stopMediaRecord();
      this.controls.setIconColor(primary);
    }
  }

  @Override
  public void openMusicConsole(String[] currentList) {
    currentActivity.startActivityForResult(FileViewerWidget.getWidgetIntent(
      currentActivity,
      FileViewerWidget.Formats.audio,
      FileViewerWidget.Layout.selectable,
      currentList
    ), WIDGET_RESULT_CODE);
  }
}
