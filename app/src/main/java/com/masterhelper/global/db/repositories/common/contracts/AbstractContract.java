package com.masterhelper.global.db.repositories.common.contracts;

import com.masterhelper.global.db.repositories.common.model.GeneralModel;
import com.masterhelper.global.db.DbHelpers;

public abstract class AbstractContract<ModelType extends GeneralModel> implements IContract<ModelType> {
  public final static int ID_COLUMN_LENGTH = 200;
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
    deleteTable();
    getDbHelpers().write(contract.createTable());
  }

  @Override
  public void deleteTable() {
    String SQLCreateTemplate = "DROP TABLE IF EXISTS ";
    getDbHelpers().write(SQLCreateTemplate + contract.getTableName());
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
