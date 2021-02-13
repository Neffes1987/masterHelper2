package com.masterhelper.media.filesystem;

import android.net.Uri;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.media.Formats;
import com.masterhelper.media.repository.MediaModel;

import java.io.File;

public interface IAppFilesLibrary {
  void copyFileToMediaLibrary(Uri path);
  void copyFilesBunchToMediaLibrary(Uri[] paths);
  void updateMediaLibrary(Formats format);

  void deleteRecordFromMediaLibrary(DataID id);

  File getFileByPosition(int position);
  public MediaModel[] getFilesLibraryList();
}
