package com.masterhelper.filesystem;

import android.net.Uri;

import java.io.File;

public interface IAppFilesLibrary {
  void copyFileToMediaLibrary(Uri path);
  void copyFilesBunchToMediaLibrary(Uri[] paths);
  void updateMediaLibrary();

  void deleteRecordFromMediaLibrary(int position);

  File getFileByPosition(int position);
  public File[] getFilesLibraryList();
}
