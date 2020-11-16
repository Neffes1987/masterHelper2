package com.masterhelper.ux.components.widgets.musicButton;

import androidx.appcompat.app.AppCompatActivity;

public interface IMusicButton {
  void init(AppCompatActivity currentActivity);
  void setBackgroundMusicState(String[] musicList);
  void openMusicConsole(String[] currentList);
}
