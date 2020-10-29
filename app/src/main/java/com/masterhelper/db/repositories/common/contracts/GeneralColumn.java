package com.masterhelper.db.repositories.common.contracts;

import com.masterhelper.db.repositories.utils.ContractsUtilities;

public class GeneralColumn {
  private String columnTitle;
  private void setColumnTitle(String columnTitle) { this.columnTitle = columnTitle; }
  public String getColumnTitle() { return columnTitle; }

  private String columnType;
  private void setColumnType(String columnType) { this.columnType = columnType; }
  public String getColumnType() { return columnType; }

  private int length;
  private void setLength(int length) { this.length = length; }
  public int getLength() { return length; }

  private String tableName;
  private void setTableName(String tableName) { this.tableName = tableName; }
  public String getTableName() { return tableName; }

  public GeneralColumn(String tableName, String columnTitle, ColumnTypes columnType, int length, boolean isNull ){
    setColumnTitle(columnTitle);
    setTableName(tableName);
    setLength(length);

    switch (columnType){
      case CharType:
        setColumnType(ContractsUtilities.charProp(columnTitle, length, isNull));
        break;
      case TextTypes:
        setColumnType(ContractsUtilities.textProp(columnTitle, isNull));
        break;
      default: throw new Error("wrong content type");
    }

  }

  public enum ColumnTypes {
    CharType,
    TextTypes
  }
}
