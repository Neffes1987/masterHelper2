package com.masterhelper.npc;

import android.content.Context;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;

/**
 * static facade class with localized strings of journeys
 */
public abstract class NPCLocale {

  /**
   * get localize value from vocabulary by key
   *
   * @param localeKey - one of available keys in vocabulary
   */
  public static String getLocalizationByKey(Keys localeKey) {
    Context context = GlobalApplication.getAppContext();
    switch (localeKey) {
      case listCaption:
        return context.getResources().getString(R.string.npc_list_caption);
      case createNewNPC:
        return context.getResources().getString(R.string.npc_new_npc);
      case updateNewNPC:
        return context.getResources().getString(R.string.npc_update_name_npc);
    }
    return "";
  }

  /**
   * available vocabulary keys
   */
  public enum Keys {
    listCaption,
    createNewNPC,
    updateNewNPC,
  }
}
