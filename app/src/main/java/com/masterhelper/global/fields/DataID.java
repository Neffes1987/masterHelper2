package com.masterhelper.global.fields;

import java.util.UUID;

public class DataID extends GeneralField<UUID> {
  public void generateId(){
    set(UUID.randomUUID());
  }

  public void fromString(String id){
    if(id != null && id.length() > 0){
      set(UUID.fromString(id));
    }
  }

  public String toString(){
    if(get() != null){
      return get().toString();
    }
    return "";
  }
}
