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
      case goalName:
        return context.getResources().getString(R.string.goal_name);
      case listCaption:
        return context.getResources().getString(R.string.goal_list);
      case createGoal:
        return context.getResources().getString(R.string.goal_create_title);
      case updateScene:
        return context.getResources().getString(R.string.goal_popup_update_title);
      case shortDescription:
        return context.getResources().getString(R.string.goal_description);
      case scriptsProgressTitle:
        return context.getResources().getString(R.string.screen_events_progress);
      case actIHint:
        return context.getResources().getString(R.string.screen_intro_tip);
      case actIIHint:
        return context.getResources().getString(R.string.screen_puzzle_intro);
      case actIIIHint:
        return context.getResources().getString(R.string.screen_plot_twist_tip);
      case actIVHint:
        return context.getResources().getString(R.string.screen_main_challenge_tip);
      case actVHint:
        return context.getResources().getString(R.string.screen_call_second_deep_tip);
      case goalInProgress:
        return context.getResources().getString(R.string.goal_in_progress);
      case goalFailed:
        return context.getResources().getString(R.string.goal_failed);
      case goalComplete:
        return context.getResources().getString(R.string.goal_complete);
      case goalLocationPlaceholder:
        return context.getResources().getString(R.string.goal_assigned_location_placeholder);
    }
    return "";
  }

  /**
   * available vocabulary keys
   */
  public enum Keys {
    listCaption,
    goalName,
    shortDescription,
    createGoal,
    updateScene,
    scriptsProgressTitle,
    actIHint,
    actIIHint,
    actIIIHint,
    actIVHint,
    actVHint,
    goalInProgress,
    goalFailed,
    goalComplete,
    goalLocationPlaceholder
  }
}
