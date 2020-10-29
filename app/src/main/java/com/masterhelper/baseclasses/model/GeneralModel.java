package com.masterhelper.baseclasses.model;

import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.baseclasses.repository.AbstractRepository;

public class GeneralModel<Repository extends AbstractRepository> implements IModel<Repository> {
  Repository repo = null;
  public final GeneralField<String> type = new GeneralField<>();

  @Override
  public void save() {
    repo.saveRecord(this);
  }

  @Override
  public void setRepo(Repository repo) {
    this.repo = repo;
  }
}
