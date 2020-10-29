package com.masterhelper.db.contracts.common;

import com.masterhelper.baseclasses.model.GeneralModel;
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
    String query = contract.createTable();
    getDbHelpers().write(query);
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
