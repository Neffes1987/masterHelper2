package com.masterhelper.global.db.contracts;

import java.text.MessageFormat;

public class ContractColumn {
  private String columnTitle;
  private String columnType;
  private int length;
  private String tableName;

  public String getColumnTitle() {
    return columnTitle;
  }

  public String getColumnType() {
    return columnType;
  }

  public int getLength() {
    return length;
  }

  public String getTableName() {
    return tableName;
  }

  public ContractColumn(String tableName) {
    init(tableName, "id", ColumnType.Primary, 0, false);
  }

  public ContractColumn(String tableName, String columnTitle) {
    init(tableName, columnTitle, ColumnType.TextTypes, 0, false);
  }

  public ContractColumn(String tableName, String columnTitle, ColumnType columnType) {
    init(tableName, columnTitle, columnType, 0, false);
  }

  public ContractColumn(String tableName, String columnTitle, ColumnType columnType, int length) {
    init(tableName, columnTitle, columnType, length, false);
  }

  public ContractColumn(String tableName, String columnTitle, ColumnType columnType, int length, Boolean isNull) {
    init(tableName, columnTitle, columnType, length, isNull);
  }

  private void init(String tableName, String columnTitle, ColumnType columnType, int length, boolean isNull) {
    this.columnTitle = columnTitle;
    this.tableName = tableName;
    this.length = length;

    String nullable = isNull ? " NULL" : " NOT NULL";
    String query;

    switch (columnType) {
      case CharType:
        query = MessageFormat.format("{0} CHAR({1}) {2}", (Object[]) new String[]{columnTitle, length + "", nullable});
        break;
      case TextTypes:
        query = MessageFormat.format("{0} TEXT({1})", (Object[]) new String[]{columnTitle, nullable});
        break;
      case Integer:
        query = MessageFormat.format("{0} INTEGER({1})", (Object[]) new String[]{columnTitle, nullable});
        break;
      case Primary:
        query = "id CHAR(200) PRIMARY KEY";
        break;
      default:
        throw new Error("wrong content type");
    }

    this.columnType = query;
  }

  public String generateForeignKey(ContractColumn externalContractColumn) {
    return MessageFormat.format("FOREIGN KEY {0}  REFERENCES {1}({2}) ON DELETE CASCADE", (Object[]) new String[]{columnTitle, externalContractColumn.getTableName(), externalContractColumn.getColumnTitle()});
  }

  public enum ColumnType {
    Preview,
    Primary,
    CharType,
    TextTypes,
    Integer
  }
}
