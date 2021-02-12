package com.masterhelper.goals.repository;
import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.db.repositories.common.model.GeneralModel;
import com.masterhelper.goals.GoalLocale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GoalModel extends GeneralModel<GoalRepository> {
  public final GeneralField<String> name = new GeneralField<>();
  public final GeneralField<String> description = new GeneralField<>();
  public final GeneralField<GoalProgress> progress = new GeneralField<>();
  public final GeneralField<Integer> actNumber = new GeneralField<>();
  public final DataID assignedLocation = new DataID();
  public static GoalProgress[] dialogProgressOptionsValues = new GoalProgress[]{GoalProgress.inProgress, GoalProgress.complete, GoalProgress.failed };
  public static String[] dialogProgressOptionsTitles = new String[]{localizeGoalProgress(GoalProgress.inProgress), localizeGoalProgress(GoalProgress.complete), localizeGoalProgress(GoalProgress.failed) };

  public GoalModel(
    GoalRepository repository,
    String defaultId,
    String defaultName,
    String defaultDescription,
    int defaultActNumber,
    UUID defaultLocation,
    GoalProgress goalProgress
  ){
    super(repository, defaultId);
    assignedLocation.set(defaultLocation);
    name.set(defaultName);
    description.set(defaultDescription);
    actNumber.set(defaultActNumber);
    progress.set(goalProgress != null ? goalProgress : GoalProgress.inProgress);
  }

  public static String  localizeGoalProgress(GoalProgress key){
    switch (key){
      case failed: return GoalLocale.getLocalizationByKey(GoalLocale.Keys.goalFailed);
      case complete: return GoalLocale.getLocalizationByKey(GoalLocale.Keys.goalComplete);
      case inProgress: return GoalLocale.getLocalizationByKey(GoalLocale.Keys.goalInProgress);
    }
    return "";
  }

  public String progressToString(){
    return localizeGoalProgress(progress.get());
  }

  public enum GoalProgress {
    inProgress,
    failed,
    complete
  }
}
