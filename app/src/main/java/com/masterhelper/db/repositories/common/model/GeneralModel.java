package com.masterhelper.db.repositories.common.model;

import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.db.repositories.common.repositories.AbstractRepository;

import java.util.UUID;

public class GeneralModel<Repository extends AbstractRepository> implements IModel<Repository> {
  Repository repo;
  public final DataID id = new DataID();
  public final GeneralField<String> type = new GeneralField<>();

  public GeneralModel(Repository repository, String defaultId){
    repo = repository;
    type.set(repository.repositoryName.get().toString());
    if(defaultId != null){
      if(defaultId.length() > 0){
        this.id.set(UUID.fromString(defaultId));
        return;
      }
    }
    id.generateId();
  }

  @Override
  public void save() {
    repo.saveRecord(this);
  }
}
