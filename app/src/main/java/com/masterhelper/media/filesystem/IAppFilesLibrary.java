package com.masterhelper.media.filesystem;

import android.net.Uri;

import java.io.File;

public interface IAppFilesLibrary {
  void copyFileToMediaLibrary(Uri path);
  void copyFilesBunchToMediaLibrary(Uri[] paths);
  void updateMediaLibrary();

  void deleteRecordFromMediaLibrary(File file);

  File getFileByPosition(int position);
  public File[] getFilesLibraryList();
}
