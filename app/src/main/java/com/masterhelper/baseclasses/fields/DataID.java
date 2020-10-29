package com.masterhelper.baseclasses.fields;

import java.util.UUID;

public class DataID extends GeneralField<UUID> {
  public void generateId(){
    set(UUID.randomUUID());
  }
}
