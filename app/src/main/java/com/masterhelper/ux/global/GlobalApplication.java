package com.masterhelper.ux.global;

import android.app.Application;
import android.content.Context;

/** static method for getting application context */
public class GlobalApplication extends Application {

  private static Context appContext;

  @Override
  public void onCreate() {
    super.onCreate();
    appContext = getApplicationContext();
  }

  public static Context getAppContext() {
    return appContext;
  }
}
