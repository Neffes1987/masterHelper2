package com.masterhelper.ux.resources.localization;

import android.content.Context;
import com.masterhelper.R;
import com.masterhelper.ux.global.GlobalApplication;

/** static facade class with localized strings of journeys */
public abstract class DialogLocale {

  /** get localize value from vocabulary by key
   * @param localeKey - one of available keys in vocabulary
   * */
  public static String getLocalizationByKey(Keys localeKey){
    Context context = GlobalApplication.getAppContext();
    switch (localeKey){
      case resolveBtn: return context.getResources().getString(R.string.dialog_resolve);
      case rejectBtn: return context.getResources().getString(R.string.dialog_reject);
    }
    return "";
  }

  /** available vocabulary keys */
  public enum Keys{
    resolveBtn,
    rejectBtn,
  }
}
