package com.masterhelper.db.repositories.journeys;

import com.masterhelper.db.repositories.common.model.GeneralModel;
import com.masterhelper.baseclasses.fields.GeneralField;

public class JourneyModel extends GeneralModel<JourneyRepository> {
  public final GeneralField<String> name = new GeneralField<>();

  public JourneyModel(JourneyRepository repository, String defaultId, String defaultName){
    super(repository, defaultId);
    name.set(defaultName);
  }
}
