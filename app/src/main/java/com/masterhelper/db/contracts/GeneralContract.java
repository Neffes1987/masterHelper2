package com.masterhelper.db.contracts;

import java.util.ArrayList;

public class GeneralContract {
  private String tableName;
  public void setTableName(String tableName) { this.tableName = tableName; }
  public String getTableName() { return tableName; }

  private final ArrayList<String> createColumns = new ArrayList<>();
  private final ArrayList<String> updateColumns = new ArrayList<>();

  public GeneralContract(String tableName, BaseColumn[] columns){
    setTableName(tableName);
    for (BaseColumn column: columns) {
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

}
