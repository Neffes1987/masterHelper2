package com.masterhelper.baseclasses;

import java.util.UUID;

public class DataID extends Field<UUID> {
  public void generateId(){
    set(UUID.randomUUID());
  }
}
