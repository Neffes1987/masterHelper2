package com.masterhelper.db.contracts;

public class BaseColumn {
  private String columnTitle;
  public void setColumnTitle(String columnTitle) { this.columnTitle = columnTitle; }
  public String getColumnTitle() { return columnTitle; }

  private String columnType;
  public void setColumnType(String columnType) { this.columnType = columnType; }
  public String getColumnType() { return columnType; }

  private int length;
  public void setLength(int length) { this.length = length; }
  public int getLength() { return length; }

  public BaseColumn(String columnTitle, ColumnTypes columnType, int length, boolean isNull ){
    setColumnTitle(columnTitle);
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
