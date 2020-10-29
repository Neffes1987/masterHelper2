package com.masterhelper.db.repositories.common.contracts;

import com.masterhelper.db.repositories.common.model.GeneralModel;
import com.masterhelper.db.DbHelpers;

public abstract class AbstractContract<ModelType extends GeneralModel> implements IContract<ModelType> {
  private GeneralContract contract;
  private final DbHelpers dbHelpers;

  public DbHelpers getDbHelpers() {
    return dbHelpers;
  }

  protected AbstractContract(DbHelpers dbHelpers){
    this.dbHelpers = dbHelpers;
  }

  @Override
  public void createTable() {
    getDbHelpers().write(contract.createTable());
  }

  @Override
  public void initContract(String tableName, GeneralColumn[] columns) {
    contract = new GeneralContract(tableName, columns);
  }

  @Override
  public GeneralContract getContract() {
    return this.contract;
  }

}
