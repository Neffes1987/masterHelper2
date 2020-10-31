package com.masterhelper.db.repositories.events;

import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.db.repositories.common.model.GeneralModel;

import java.io.File;

public class EventModel extends GeneralModel<EventRepository> {
  public final GeneralField<String> name = new GeneralField<>();
  public final GeneralField<String> description = new GeneralField<>();
  public final GeneralField<EventType> type = new GeneralField<>();
  public final DataID previewId = new DataID();

  public EventModel(EventRepository repository, String defaultId, String defaultName, String defaultDescription, EventType defaultType, String previewUrlId){
    super(repository, defaultId);
    name.set(defaultName);
    description.set(defaultDescription);
    type.set(defaultType);
    if(previewUrlId != null){
      previewId.fromString(previewUrlId);
    } else {
      previewId.set(null);
    }
  }

  public enum EventType {
    battle,
    meeting,
    accident
  }
}
