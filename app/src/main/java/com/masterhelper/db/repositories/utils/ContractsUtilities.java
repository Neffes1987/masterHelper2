package com.masterhelper.db.repositories.utils;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ContractsUtilities implements BaseColumns {
  public final static String _ID = BaseColumns._ID;
  public final static String INDEX_KEY = _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";

  public static String generateInsertQuery(String tableName, String[] columns, String[] values){
    StringBuilder valuesResult = new StringBuilder();
    StringBuilder columnsResult = new StringBuilder();
    int columnsLastIndex = columns.length -1;
    for(int i=0; i<=columnsLastIndex; i++){
      String columnName = columns[i];
      String value = values[i];

      columnsResult.append(columnName);
      valuesResult.append("'").append(value).append("'");
      if(i <  columnsLastIndex){
        columnsResult.append(",");
        valuesResult.append(",");
      }
    }

    return "INSERT INTO " + tableName + " (" + columnsResult + ") VALUES ("+ valuesResult +")";
  }

  public static String generateUpdateValues(String tableName, String searchColumnName, String tableRecordId, String[] columns, String[] values){
    return commonUpdateGenerator(tableName, columns, values) +" WHERE " + searchColumnName + "='"+tableRecordId+"'";
  }

  public static String commonUpdateGenerator(String tableName, String[] columns, String[] values){
    StringBuilder result = new StringBuilder();
    int columnsLastIndex = columns.length -1;

    for(int i=0; i<=columnsLastIndex; i++){
      String columnName = columns[i];
      String value = values[i];

      result.append(columnName).append("='").append(value).append("'");
      if(i <  columnsLastIndex){
        result.append(",");
      }
    }
    return "UPDATE " + tableName + " SET "+ result.toString();
  }

  public static String generateTableQuery(String TableName, String[] columns){
    StringBuilder result = new StringBuilder();
    if(columns.length == 0){
      return "";
    }
    result.append(INDEX_KEY);
    int columnsLastIndex = columns.length -1;

    for(int i=0; i<=columnsLastIndex; i++){
      String columnName = columns[i];
      result.append(columnName);
      if(i <  columnsLastIndex){
        result.append(",");
      }
    }
    return "CREATE TABLE " + TableName + " (" +result.toString() + ")";
  }

  public static String generateDeleteItemQuery(String tableName, String searchColumnName, String deletedItemId){
    return "DELETE FROM " + tableName + " WHERE " + searchColumnName + "='"+deletedItemId+"'";
  }

  public static String generateSelectItemQuery(String tableName, String[] columns, int offset, int limit, String order){
    StringBuilder query = new StringBuilder();
    query.append("SELECT ");

    for(int columnIndex = 0; columnIndex < columns.length; columnIndex++){
      query.append(columns[columnIndex]);
      if(columnIndex != columns.length-1){
        query.append(", ");
      }
    }
    query.append(" FROM ").append(tableName);
    if(offset != 0){
      query.append(" OFFSET ").append(offset);
    }

    if(limit != 0){
      query.append(" LIMIT ").append(limit);
    }

    query.append(" ORDER BY ").append(order != null ? order : "_ID DESC");
    Log.i("TAG", "generateSelectItemQuery: " + query.toString());
    return query.toString();
  }

  public static String [] concat(final String[] first, final String[] second) {
    final ArrayList<String> resultList = new ArrayList<>(Arrays.asList(first));
    resultList.addAll(new ArrayList<>(Arrays.asList(second)));
    return resultList.toArray(new String[0]);
  }

  public static String deleteRecordsByIds(String tableName, String ids){
    return "DELETE FROM " + tableName + " WHERE " + _ID + " IN (" +ids+ ")";
  }

  public static String charProp(String columnTitle, int length, boolean isNull){
    return columnTitle + " CHAR(" + length + ") " + (isNull ? " NULL" : " NOT NULL");
  }

  public static String textProp(String columnTitle, boolean isNull){
    return columnTitle + " TEXT " + (isNull ? " NULL" : " NOT NULL");
  }
}
