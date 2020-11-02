package com.masterhelper.filesystem;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.global.GlobalApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AppFilesLibrary implements IAppFilesLibrary {
  GeneralField<File> workingDirectory = new GeneralField<>();
  ArrayList<File> filesList = new ArrayList<>();
  ContentResolver resolver;

  public AppFilesLibrary(String workingDirectory){
    File rootAppDir = GlobalApplication.getAppContext().getFilesDir();
    File libDirectory = new File(rootAppDir.getPath()+"/"+ workingDirectory);
    if(!libDirectory.exists()){
      libDirectory.mkdirs();
    }
    this.workingDirectory.set(libDirectory);
    resolver = GlobalApplication.getAppContext().getContentResolver();
    updateMediaLibrary();
  }

  private String getOriginalFileName(Uri path){
    Cursor cursor = resolver
      .query(path, null, null, null, null, null);
    String displayName = "file";
    try {
      if (cursor != null && cursor.moveToFirst()) {
        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    assert cursor != null;
    cursor.close();
    return displayName;
  }

  @Override
  public void copyFileToMediaLibrary(Uri path) {
    String fileName = getOriginalFileName(path);

    File libraryFile =  new File(workingDirectory.get().getPath() + "/" + fileName);

    try (InputStream in = resolver.openInputStream(path)) {
      try (OutputStream out = new FileOutputStream(libraryFile)) {
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
          out.write(buf, 0, len);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    filesList.add(libraryFile);
  }

  @Override
  public void copyFilesBunchToMediaLibrary(Uri[] files) {
    if(files == null){ return; }

    for (Uri path : files) {
      copyFileToMediaLibrary(path);
    }
  }

  @Override
  public void updateMediaLibrary() {
    File[] list = workingDirectory.get().listFiles();
    this.filesList.clear();
    if(list != null){
      this.filesList.addAll(Arrays.asList(list));
    }
  }

  @Override
  public void deleteRecordFromMediaLibrary(int position) {
    File file = getFileByPosition(position);
    if(file.delete()){
      filesList.remove(file);
    }
  }

  @Override
  public File getFileByPosition(int position) {
    return filesList.toArray(new File[0])[position];
  }

  @Override
  public File[] getFilesLibraryList() {
    return filesList.toArray(new File[0]);
  }
}
