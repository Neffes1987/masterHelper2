package com.masterhelper.baseclasses.fields;

public class GeneralField<DataType> {
  private DataType value;
  public DataType get() { return value; }
  public void set(DataType value) { this.value = value; }
}
