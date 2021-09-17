package com.masterhelper.global.db.repository;

import android.database.Cursor;
import com.masterhelper.global.db.contracts.AbstractContract;

import java.text.MessageFormat;

public abstract class AbstractRepository {
  private final AbstractContract contract;

  public AbstractRepository(AbstractContract contract) {
    this.contract = contract;
  }

  public Cursor get(String id) {
    return contract.list(0, 1, "ASC", MessageFormat.format("id = '{0}'", (Object[]) new String[]{id}));
  }

  ;

  public Cursor list(int offset, int limit, String ordering, String where) {
    return contract.list(offset, limit, ordering, where);
  }

  ;

  public void delete(String deletedElementId) {
    contract.delete(deletedElementId);
  }

  public void create(AbstractModel newModel) {
    contract.insert(newModel);
  }

  public void update(AbstractModel updatedElement) {
    contract.update(updatedElement);
  }

  public void createTable() {
    contract.createTable();
  }
}
