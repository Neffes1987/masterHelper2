package com.masterhelper.global.db.repositories.common.contracts;

import android.database.Cursor;
import com.masterhelper.global.db.repositories.common.model.GeneralModel;
import com.masterhelper.global.fields.DataID;

public interface IContract<Model extends GeneralModel> {
  void createTable();
  void deleteTable();
  void insertRecord(Model record);
  void updateRecord(Model record);
  void deleteRecord(DataID recordId);
  void initContract(String tableName, GeneralColumn[] columns);
  GeneralContract getContract();
  Cursor list(int offset, int limit);
  Cursor getRecord(String recordId);
}
