package com.masterhelper.db.contracts.common;

import android.database.Cursor;
import com.masterhelper.baseclasses.model.GeneralModel;
import com.masterhelper.baseclasses.fields.DataID;

public interface IContract<Model extends GeneralModel> {
  void createTable();
  void insertRecord(Model record);
  void updateRecord(Model record);
  void deleteRecord(DataID recordId);
  void initContract(String tableName, GeneralColumn[] columns);
  GeneralContract getContract();
  Cursor list(int offset, int limit);
}
