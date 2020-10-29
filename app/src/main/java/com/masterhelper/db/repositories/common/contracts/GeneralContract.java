package com.masterhelper.db.repositories.common.contracts;

import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.db.repositories.utils.ContractsUtilities;

import java.util.ArrayList;

public class GeneralContract {
  private String tableName;
  private void setTableName(String tableName) { this.tableName = tableName; }
  public String getTableName() { return tableName; }

  private final ArrayList<String> createColumns = new ArrayList<>();
  private final ArrayList<String> updateColumns = new ArrayList<>();

  public GeneralContract(String tableName, GeneralColumn[] columns){
    setTableName(tableName);
    for (GeneralColumn column: columns) {
      createColumns.add(column.getColumnType());
      updateColumns.add(column.getColumnTitle());
    }
  }

  public String[] getInitialColumnsProps(){
    return createColumns.toArray(new String[0]);
  }
  public String[] getColumnsTitles(){
    return updateColumns.toArray(new String[0]);
  }

  public void addDeleteForeignKeyColumn(GeneralColumn externalContractColumn, GeneralColumn foreignContractColumn){
    if(updateColumns.contains(foreignContractColumn.getColumnTitle())){
      throw new Error("doDeleteCascade: update columns set does already contain child column " + foreignContractColumn.getColumnTitle() + " please remove it from constructor columns set");
    }
    createColumns.add(foreignContractColumn.getColumnType());
    updateColumns.add(foreignContractColumn.getColumnTitle());

    String cascadeProp = "FOREIGN KEY ("+ foreignContractColumn.getColumnTitle() +") REFERENCES " + externalContractColumn.getTableName() + "("+externalContractColumn.getColumnTitle()+") ON DELETE CASCADE";
    createColumns.add(cascadeProp);
  }

  public String createTable(){
    return ContractsUtilities.generateTableQuery(getTableName(), getInitialColumnsProps());
  }

  public String insertRecord(String[] columnsValues){
    if(columnsValues.length != getColumnsTitles().length){
      throw new Error("insertRecord: columns values quantity does not equal columns count");
    }
    return ContractsUtilities.generateInsertQuery(getTableName(), getColumnsTitles(), columnsValues);
  }

  public String updateRecord(DataID id, String[] columnsValues, String columnName) {
    if(columnsValues.length != getColumnsTitles().length){
      throw new Error("insertRecord: columns values quantity does not equal columns count");
    }
    return ContractsUtilities.generateUpdateValues(getTableName(),columnName, id.get().toString(), getColumnsTitles(), columnsValues);
  }

  public String deleteRecord(DataID id, String columnName) {
    return ContractsUtilities.generateDeleteItemQuery(getTableName(), columnName, id.get().toString());
  }

  public String selectRecords(int offset, int limit, String[] columns, String ordering) {
    return ContractsUtilities.generateSelectItemQuery(getTableName(), columns, offset, limit, ordering);
  }

}
