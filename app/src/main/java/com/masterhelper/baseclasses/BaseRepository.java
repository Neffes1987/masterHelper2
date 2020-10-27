package com.masterhelper.baseclasses;

import java.util.ArrayList;

public interface BaseRepository<RecordType> {
  ArrayList repositoryList = null;
  void createItem();
  void updateItem(RecordType record);
  void deleteItem(RecordType record);
  RecordType getItem();
}
