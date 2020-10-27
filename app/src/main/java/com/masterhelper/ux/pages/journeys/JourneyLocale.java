package com.masterhelper.ux.pages.journeys;

import android.content.Context;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;

/** static facade class with localized strings of journeys */
public abstract class JourneyLocale {

  /** get localize value from vocabulary by key
   * @param localeKey - one of available keys in vocabulary
   * */
  public static String getLocalizationByKey(Keys localeKey){
    Context context = GlobalApplication.getAppContext();
    switch (localeKey){
      case journeyName: return context.getResources().getString(R.string.journey_name);
      case listCaption: return context.getResources().getString(R.string.journey_list_name);
      case createJourney: return context.getResources().getString(R.string.journey_create_title);
      case updateJourney:return context.getResources().getString(R.string.journey_update_title);
      case shortDescription: return context.getResources().getString(R.string.journey_description);
    }
    return "";
  }

  /** available vocabulary keys */
  public enum Keys{
    listCaption,
    journeyName,
    shortDescription,
    createJourney,
    updateJourney
  }
}
