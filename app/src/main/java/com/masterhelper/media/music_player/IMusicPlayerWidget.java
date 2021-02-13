package com.masterhelper.media.music_player;

public interface IMusicPlayerWidget {
  void next();

  void play();

  void stop();

  String getCurrentTrackName();

  boolean checkIsPlaying();
}
