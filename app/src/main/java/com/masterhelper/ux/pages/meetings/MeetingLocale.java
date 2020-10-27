package com.masterhelper.ux.pages.meetings;

import android.content.Context;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;

/** static facade class with localized strings of journeys */
public abstract class MeetingLocale {

  /** get localize value from vocabulary by key
   * @param localeKey - one of available keys in vocabulary
   * */
  public static String getLocalizationByKey(Keys localeKey){
    Context context = GlobalApplication.getAppContext();
    switch (localeKey){
      case name: return context.getResources().getString(R.string.meeting_name);
      case update:return context.getResources().getString(R.string.meeting_update);
      case description: return context.getResources().getString(R.string.meeting_description);
    }
    return "";
  }

  /** available vocabulary keys */
  public enum Keys{
    name,
    description,
    update
  }
}
