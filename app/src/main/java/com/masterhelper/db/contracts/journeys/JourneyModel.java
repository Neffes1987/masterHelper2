package com.masterhelper.db.contracts.journeys;

import com.masterhelper.baseclasses.model.GeneralModel;
import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.baseclasses.fields.GeneralField;

import java.util.UUID;

public class JourneyModel extends GeneralModel<JourneyRepository> {
  public final DataID id = new DataID();
  public final GeneralField<String> name = new GeneralField<>();

  public JourneyModel(JourneyRepository repository, String id, String name){
    this.name.set(name);
    if(id != null){
      this.id.set(UUID.fromString(id));
    } else {
      this.id.generateId();
    }
    setRepo(repository);
  }
}
