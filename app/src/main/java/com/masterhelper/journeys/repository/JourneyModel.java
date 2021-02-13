package com.masterhelper.journeys.repository;

import com.masterhelper.global.db.repositories.common.model.GeneralModel;
import com.masterhelper.global.fields.GeneralField;

public class JourneyModel extends GeneralModel<JourneyRepository> {
  public final GeneralField<String> name = new GeneralField<>();

  public JourneyModel(JourneyRepository repository, String defaultId, String defaultName){
    super(repository, defaultId);
    name.set(defaultName);
  }
}
