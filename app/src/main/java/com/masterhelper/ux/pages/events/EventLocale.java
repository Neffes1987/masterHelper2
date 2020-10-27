package com.masterhelper.ux.pages.events;

import android.content.Context;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;

/** static facade class with localized strings of journeys */
public abstract class EventLocale {

  /** get localize value from vocabulary by key
   * @param localeKey - one of available keys in vocabulary
   * */
  public static String getLocalizationByKey(Keys localeKey){
    Context context = GlobalApplication.getAppContext();
    switch (localeKey){
      case eventName: return context.getResources().getString(R.string.event_name);
      case listCaption: return context.getResources().getString(R.string.events_list);
      case createEvent: return context.getResources().getString(R.string.event_create_title);
      case updateEvent:return context.getResources().getString(R.string.event_update_title);
      case shortDescription: return context.getResources().getString(R.string.event_description);
      case eventAccident: return context.getResources().getString(R.string.event_is_accident);
      case eventBattle: return context.getResources().getString(R.string.event_is_battle);
      case eventMeeting: return context.getResources().getString(R.string.event_is_meeting);
    }
    return "";
  }

  /** available vocabulary keys */
  public enum Keys{
    listCaption,
    eventName,
    shortDescription,
    createEvent,
    updateEvent,
    eventAccident,
    eventMeeting,
    eventBattle,
  }
}
