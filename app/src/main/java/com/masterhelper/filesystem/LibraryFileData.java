package com.masterhelper.filesystem;

import com.masterhelper.baseclasses.fields.DataID;

import java.io.File;

public class LibraryFileData {
  DataID id = new DataID();
  File file;
  public Boolean isSelected;
  public Boolean isPlayed;

  public LibraryFileData(String id, String filePath, boolean isSelected, boolean isPlayed){
    if(id == null){
      this.id.generateId();
    } else {
      this.id.fromString(id);
    }

    if(filePath != null){
      file = new File(filePath);
    }

    this.isSelected = isSelected;
    this.isPlayed = isPlayed;
  }

  public String getFileName() {
    return file.getName();
  }

  public File getFile(){
    return file;
  }
}
