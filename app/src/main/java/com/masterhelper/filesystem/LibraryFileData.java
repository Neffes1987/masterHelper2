package com.masterhelper.filesystem;

import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.baseclasses.fields.GeneralField;

import java.io.File;

public class LibraryFileData {
  DataID id = new DataID();
  File file;
  public GeneralField<Boolean> isSelected = new GeneralField<>();

  public LibraryFileData(String id, String filePath, boolean isSelected){
    if(id == null){
      this.id.generateId();
    } else {
      this.id.fromString(id);
    }

    if(filePath != null){
      file = new File(filePath);
    }

    this.isSelected.set(isSelected);
  }

  public String getFileName() {
    return file.getName();
  }
}
