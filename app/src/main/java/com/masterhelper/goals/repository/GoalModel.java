package com.masterhelper.goals.repository;

import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.db.repositories.common.model.GeneralModel;

import java.util.UUID;

public class GoalModel extends GeneralModel<GoalRepository> {
  public final GeneralField<String> name = new GeneralField<>();
  public final GeneralField<String> description = new GeneralField<>();
  public final GeneralField<Integer> actNumber = new GeneralField<>();
  public final DataID assignedLocation = new DataID();

  public GoalModel(GoalRepository repository, String defaultId, String defaultName, String defaultDescription, int defaultActNumber, UUID defaultLocation){
    super(repository, defaultId);
    assignedLocation.set(defaultLocation);
    name.set(defaultName);
    description.set(defaultDescription);
    actNumber.set(defaultActNumber);

  }
}
