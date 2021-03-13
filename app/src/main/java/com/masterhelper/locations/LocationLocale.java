package com.masterhelper.locations;

import android.content.Context;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;

/** static facade class with localized strings of journeys */
public abstract class LocationLocale {

  /** get localize value from vocabulary by key
   * @param localeKey - one of available keys in vocabulary
   * */
  public static String getLocalizationByKey(Keys localeKey){
    Context context = GlobalApplication.getAppContext();
    switch (localeKey){
      case locationName:
        return context.getResources().getString(R.string.location_name);
      case listCaption:
        return context.getResources().getString(R.string.locations_list);
      case createLocation:
        return context.getResources().getString(R.string.event_create_title);
      case updateLocation:
        return context.getResources().getString(R.string.event_update_title);
      case shortDescription:
        return context.getResources().getString(R.string.location_description);
      case saveLocation:
        return context.getResources().getString(R.string.event_update_media_title);
    }
    return "";
  }

  /**
   * available vocabulary keys
   */
  public enum Keys {
    listCaption,
    locationName,
    shortDescription,
    createLocation,
    updateLocation,
    saveLocation,
  }
}
