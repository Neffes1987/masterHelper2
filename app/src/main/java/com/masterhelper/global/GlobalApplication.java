package com.masterhelper.global;

import android.app.Application;
import android.content.Context;
import com.masterhelper.db.DbHelpers;

/** static method for getting application context */
public class GlobalApplication extends Application {

  private static Context appContext;
  private static DbHelpers appDB;

  @Override
  public void onCreate() {
    super.onCreate();
    appContext = getApplicationContext();
    appDB = new DbHelpers(appContext);
  }

  public static Context getAppContext() {
    return appContext;
  }

  public static DbHelpers getAppDB() { return appDB; }
}
