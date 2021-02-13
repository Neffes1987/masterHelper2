package com.masterhelper.media.filesystem;

import android.net.Uri;

import java.io.File;

public class LibraryFileData {
  public String uri;
  File file;
  public Boolean isSelected;
  public Boolean isPlayed;

  public LibraryFileData(String uri, String filePath, boolean isSelected, boolean isPlayed){
    this.uri = Uri.encode(uri);

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
