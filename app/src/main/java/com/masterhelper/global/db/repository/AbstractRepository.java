package com.masterhelper.global.db.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.MessageFormat;
import java.util.ArrayList;

public abstract class AbstractRepository<Model extends AbstractModel> {
  private final String tableName;
  private final ContractColumn[] columns;
  private final SQLiteDatabase dbGateway;

  public AbstractRepository(SQLiteDatabase db, String tableName, ContractColumn[] columns) {
    this.tableName = tableName;
    this.columns = columns;
    this.dbGateway = db;
  }

  protected Cursor getRecord(String id) {
    String where = MessageFormat.format("id = \"{0}\"", (Object[]) new String[]{id});

    Cursor cursor = getRecords(0, 1, null, where);

    cursor.moveToNext();

    return cursor;
  }

  public abstract Model get(String id);

  public Cursor getRecords(int offset, int limit, String ordering, String where) {
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

    return dbGateway.rawQuery(query.toString(), null);
  }

  public Cursor getRawData(String query) {
    return dbGateway.rawQuery(query, null);
  }

  public void execQuery(String query) {
    dbGateway.execSQL(query);
  }

  public abstract ArrayList<Model> list(int offset, int limit, String ordering, String where);


  public void delete(String deletedElementId) {
    String query = formatQuery("DELETE FROM {0} WHERE id=\"{1}\"", new String[]{tableName, deletedElementId});

    dbGateway.execSQL(query);
  }

  public void create(Model newModel) {
    String query = formatQuery("INSERT INTO {0} ({1}) VALUES ({2})", new String[]{getTableName(), getColumnsTitles(), convertToString(extractFields(newModel))});

    dbGateway.execSQL(query);
  }


  public void createTable() {
    String query = formatQuery("CREATE TABLE {0} ({1})", new String[]{tableName, getColumnsTitles()});

    dbGateway.execSQL(query);
  }

  public abstract Model draft();

  public String getTableName() {
    return tableName;
  }

  public void update(Model model) {
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

    String query = formatQuery("UPDATE {0} SET {1} WHERE id=\"{2}\"", new String[]{getTableName(), result.toString(), model.getId()});

    dbGateway.execSQL(query);
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
      String columnName = "'" + values[i] + "'";

      result.append(columnName);
      if (i < values.length - 1) {
        result.append(",");
      }
    }

    return result.toString();
  }

  public String formatQuery(String template, String[] params) {
    return MessageFormat.format(template, (Object[]) params);
  }

  public abstract String[] extractFields(Model newModel);
}
