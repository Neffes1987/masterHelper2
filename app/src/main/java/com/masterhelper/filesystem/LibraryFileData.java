package com.masterhelper.filesystem;

import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.baseclasses.fields.GeneralField;

import java.io.File;

public class LibraryFileData {
  DataID id = new DataID();
  File file;
  public GeneralField<Boolean> isSelected = new GeneralField<>();
  public GeneralField<Boolean> isPlayed = new GeneralField<>();

  public LibraryFileData(String id, String filePath, boolean isSelected, boolean isPlayed){
    if(id == null){
      this.id.generateId();
    } else {
      this.id.fromString(id);
    }

    if(filePath != null){
      file = new File(filePath);
    }

    this.isSelected.set(isSelected);
    this.isPlayed.set(isPlayed);
  }

  public String getFileName() {
    return file.getName();
  }

  public File getFile(){
    return file;
  }
}
