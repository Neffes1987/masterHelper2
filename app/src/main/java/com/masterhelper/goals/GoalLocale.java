package com.masterhelper.goals;

import android.content.Context;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;

/** static facade class with localized strings of journeys */
public abstract class GoalLocale {

  /** get localize value from vocabulary by key
   * @param localeKey - one of available keys in vocabulary
   * */
  public static String getLocalizationByKey(Keys localeKey){
    Context context = GlobalApplication.getAppContext();
    switch (localeKey){
      case sceneName: return context.getResources().getString(R.string.scene_name);
      case listCaption: return context.getResources().getString(R.string.scene_list);
      case createScene: return context.getResources().getString(R.string.scene_create_title);
      case updateScene:return context.getResources().getString(R.string.screen_name_scene_update);
      case shortDescription: return context.getResources().getString(R.string.scene_description);
      case scriptsProgressTitle: return context.getResources().getString(R.string.screen_events_progress);
    }
    return "";
  }

  /** available vocabulary keys */
  public enum Keys{
    listCaption,
    sceneName,
    shortDescription,
    createScene,
    updateScene,
    scriptsProgressTitle
  }
}
