package com.masterhelper.plotLine;

import android.content.Context;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;

/**
 * static facade class with localized strings of journeys
 */
public abstract class PlotLocale {

  /**
   * get localize value from vocabulary by key
   *
   * @param localeKey - one of available keys in vocabulary
   */
  public static String getLocalizationByKey(Keys localeKey) {
    Context context = GlobalApplication.getAppContext();
    switch (localeKey) {
      case caption:
        return context.getResources().getString(R.string.plots_list_name);
      case name:
        return context.getResources().getString(R.string.plots_name);
      case description:
        return context.getResources().getString(R.string.plots_description);
      case create:
        return context.getResources().getString(R.string.plots_create_title);
      case update:
        return context.getResources().getString(R.string.plots_update_title);
    }
    return "";
  }

  /**
   * available vocabulary keys
   */
  public enum Keys {
    caption,
    name,
    description,
    create,
    update
  }
}
