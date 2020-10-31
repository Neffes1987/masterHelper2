package com.masterhelper.db.repositories.scenes;

import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.db.repositories.common.model.GeneralModel;

public class SceneModel extends GeneralModel<SceneRepository> {
  public final GeneralField<String> name = new GeneralField<>();
  public final GeneralField<String> description = new GeneralField<>();
  public final GeneralField<Integer> totalEvents = new GeneralField<>();
  public final GeneralField<Integer> finishedEvents = new GeneralField<>();

  public SceneModel(SceneRepository repository, String defaultId, String defaultName, String defaultDescription, int defaultTotalEvents, int defaultFinishedEvents){
    super(repository, defaultId);
    name.set(defaultName);
    description.set(defaultDescription);
    totalEvents.set(defaultTotalEvents);
    finishedEvents.set(defaultFinishedEvents);
  }
}
