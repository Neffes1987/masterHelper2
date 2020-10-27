package com.masterhelper.baseclasses;

public class Field<DataType> {
  private DataType value;
  public DataType get() { return value; }
  public void set(DataType value) { this.value = value; }
}
