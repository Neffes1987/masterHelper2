package com.masterhelper.baseclasses;

public interface BaseModel {
  DataID id = new DataID();
  Field<String> name = new Field<>();
  Field<String> description = new Field<>();
  void save();
}
