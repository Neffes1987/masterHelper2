package com.masterhelper.global;

import android.app.Application;
import android.content.Context;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.media.filesystem.AudioPlayer;

/** static method for getting application context */
public class GlobalApplication extends Application {

  private static Context appContext;
  private static DbHelpers appDB;
  private static AudioPlayer player;

  @Override
  public void onCreate() {
    super.onCreate();
    appContext = getApplicationContext();
    appDB = new DbHelpers(appContext);
    player = new AudioPlayer();
  }

  public static Context getAppContext() {
    return appContext;
  }

  public static DbHelpers getAppDB() { return appDB; }

  public static AudioPlayer getPlayer() {
    return player;
  }
}
