package com.masterhelper.media.filesystem;

import com.masterhelper.global.fields.DataID;
import com.masterhelper.media.Formats;

import java.io.File;

public class LibraryFileData {
  public DataID id;
  File file;
  public Boolean isSelected;
  public Boolean isPlayed;

  public LibraryFileData(DataID id, String filePath, boolean isSelected, boolean isPlayed){
    this.id = id;

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
