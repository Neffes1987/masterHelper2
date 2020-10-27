package com.masterhelper.db.contracts;
public interface IContract<DataModel> {
  String createTable();
  String insertRecord(DataModel record);
  String updateRecord(DataModel record);
  String deleteRecord(DataModel record);
}
