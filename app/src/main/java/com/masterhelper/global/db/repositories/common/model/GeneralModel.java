package com.masterhelper.global.db.repositories.common.model;

import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.fields.GeneralField;
import com.masterhelper.global.db.repositories.common.repositories.AbstractRepository;

import java.util.UUID;

public class GeneralModel<Repository extends AbstractRepository> implements IModel<Repository> {
  Repository repo;
  public final DataID id = new DataID();
  public final GeneralField<String> name = new GeneralField<>();
  public final GeneralField<String> type = new GeneralField<>();

  public GeneralModel(Repository repository, String defaultId, String name) {
    repo = repository;

    this.name.set(name);

    type.set(repository.repositoryName.get().toString());
    if (defaultId != null) {
      if (defaultId.length() > 0) {
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
