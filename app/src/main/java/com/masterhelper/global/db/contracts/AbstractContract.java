package com.masterhelper.global.db.contracts;

import android.database.Cursor;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.global.db.repository.AbstractModel;

import java.text.MessageFormat;

public abstract class AbstractContract {
  private final String tableName;
  private final ContractColumn[] columns;

  public AbstractContract(String tableName, ContractColumn[] columns) {
    this.columns = columns;
    this.tableName = tableName;
  }

  public String getTableName() {
    return tableName;
  }

  public void insert(AbstractModel model) {
    String query = formatQuery("INSERT INTO {0} ({1}) VALUES ({2})", new String[]{getTableName(), getColumnsTitles(), convertToString(extractFields(model))});

    GlobalApplication.getAppDB().write(query);
  }

  public void update(AbstractModel model) {
    String[] columnsValues = extractFields(model);
    StringBuilder result = new StringBuilder();

    int columnsLastIndex = columns.length - 1;

    for (int i = 0; i <= columnsLastIndex; i++) {
      String name = columns[i].getColumnTitle();
      String value = columnsValues[i];

      result.append(name).append("='").append(value.replaceAll("'", "`")).append("'");
      if (i < columnsLastIndex) {
        result.append(",");
      }
    }

    String query = formatQuery("UPDATE {0} SET {1}  WHERE id = '{3}'", new String[]{getTableName(), result.toString(), model.id});

    GlobalApplication.getAppDB().write(query);
  }

  private String getColumnsTitles() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i <= columns.length - 1; i++) {
      String columnName = columns[i].getColumnTitle();

      result.append(columnName);
      if (i < columns.length - 1) {
        result.append(",");
      }
    }

    return result.toString();
  }

  private String convertToString(String[] values) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i <= values.length - 1; i++) {
      String columnName = values[i];

      result.append(columnName);
      if (i < values.length - 1) {
        result.append(",");
      }
    }

    return result.toString();
  }

  private String formatQuery(String template, String[] params) {
    return MessageFormat.format(template, (Object[]) params);
  }

  public void createTable() {
    String query = formatQuery("CREATE TABLE {0} ({1})", new String[]{tableName, getColumnsTitles()});

    GlobalApplication.getAppDB().write(query);
  }

  public void delete(String deletedElementId) {
    String query = formatQuery("DELETE FROM {0} WHERE id='{1}'", new String[]{tableName, deletedElementId});

    GlobalApplication.getAppDB().write(query);
  }

  public Cursor list(int offset, int limit, String ordering, String where) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT ");

    for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
      query.append(columns[columnIndex].getColumnTitle());
      if (columnIndex != columns.length - 1) {
        query.append(", ");
      }
    }
    query.append(" FROM ").append(tableName);
    if (offset != 0) {
      query.append(" OFFSET ").append(offset);
    }

    if (where != null) {
      query.append(" WHERE ").append(where);
    }

    if (limit != 0) {
      query.append(" LIMIT ").append(limit);
    }
    if (ordering != null) {
      query.append(" ORDER BY ").append(ordering);
    }

    return GlobalApplication.getAppDB().read(query.toString());
  }

  public abstract String[] extractFields(AbstractModel newModel);
}
