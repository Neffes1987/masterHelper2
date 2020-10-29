package com.masterhelper.baseclasses.repository;

import com.masterhelper.baseclasses.fields.DataID;

public interface IRepository<RecordType> {
  RecordType getRecord(DataID elementId);
  void removeRecord(DataID deletedElementId);
  RecordType findRecordById(DataID elementId);
  RecordType getDraftRecord();
  void saveRecord(RecordType updatedElement);
  RecordType[] list(int offset, int limit);
}
