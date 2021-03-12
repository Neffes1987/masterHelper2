package com.masterhelper.global.db.repositories.common.repositories;

import android.database.Cursor;
import com.masterhelper.global.fields.DataID;

public interface IRepository<RecordType> {
  RecordType getRecord(DataID elementId);
  void removeRecord(DataID deletedElementId);
  RecordType findRecordById(DataID elementId);
  RecordType getDraftRecord();
  void createTable();

  void saveRecord(RecordType updatedElement);

  RecordType[] list(int offset, int limit, String searchStr);

  RecordType getRecord(String recordId);

  RecordType extractDataFromCursor(Cursor cursor);
}
