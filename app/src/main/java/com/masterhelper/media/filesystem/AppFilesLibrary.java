package com.masterhelper.media.filesystem;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import androidx.documentfile.provider.DocumentFile;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.fields.GeneralField;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.media.Formats;
import com.masterhelper.media.repository.MediaModel;
import com.masterhelper.media.repository.MediaRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AppFilesLibrary implements IAppFilesLibrary {
  public static final String FORMAT_IMAGE_PATH = "/images";
  public static final String FORMAT_AUDIO_PATH = "/audio";

  GeneralField<File> workingDirectory = new GeneralField<>();
  MediaRepository repository;
  ArrayList<MediaModel> filesList = new ArrayList<>();
  ContentResolver resolver;
  Formats format;

  public AppFilesLibrary(String workingDirectory, Formats format ){
    File rootAppDir = GlobalApplication.getAppContext().getFilesDir();
    File libDirectory = new File(rootAppDir.getPath()+"/"+ workingDirectory);
    if(!libDirectory.exists()){
      libDirectory.mkdirs();
    }
    this.format = format;
    this.workingDirectory.set(libDirectory);
    resolver = GlobalApplication.getAppContext().getContentResolver();
    repository = GlobalApplication.getAppDB().mediaRepository;
    updateMediaLibrary(format);
  }

  private String getOriginalFileName(Uri path){
    String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
    Cursor cursor = resolver
      .query(path, projection, null, null, null, null);
    String displayName = "file";
    try {
      if (cursor != null && cursor.moveToFirst()) {
        displayName = cursor.getString(0);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    assert cursor != null;
    cursor.close();
    return displayName;
  }

  private String getFileExtension(Uri uri) {
    String extension;
    MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
    extension = mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    return '.' + extension;
  }

  @Override
  public void copyFileToMediaLibrary(Uri path) {
    Date currentDate = new Date();
    String libFilName = this.format + "_" + currentDate.getTime();
    File libraryFile = new File(workingDirectory.get().getPath() + "/" + libFilName + getFileExtension(path));

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

    MediaModel mediaRecord =  repository.getDraftRecord();
    mediaRecord.filePath.set(libraryFile.getPath());
    mediaRecord.fileName.set(getOriginalFileName(path));
    mediaRecord.fileType.set(format);

    mediaRecord.save();
    filesList.add(mediaRecord);
  }

  @Override
  public void copyFilesBunchToMediaLibrary(Uri[] files) {
    if (files == null) {
      return;
    }
    for (Uri path : files) {
      copyFileToMediaLibrary(path);
    }
  }

  public void removeSourceFilesBunch(Uri[] files) {
    if (files == null) {
      return;
    }
    for (Uri path : files) {
      DocumentFile file = DocumentFile.fromSingleUri(GlobalApplication.getAppContext(), path);
      file.delete();
    }
  }

  @Override
  public void updateMediaLibrary(Formats type) {
    MediaModel[] list = repository.list(type.name());
    this.filesList.clear();
    if (list != null) {
      this.filesList.addAll(Arrays.asList(list));
    }
  }

  @Override
  public void deleteRecordFromMediaLibrary(DataID id) {
    MediaModel model = repository.getRecord(id);
    File file = new File(model.filePath.get());
    if(file.delete()){
      filesList.remove(model);
      repository.delete(model.id);
    }
  }

  @Override
  public File getFileByPosition(int position) {
    if (filesList.size() == 0) {
      return null;
    }
    MediaModel model = filesList.get(position);
    return new File(model.filePath.get());
  }

  public String getFileNameByPosition(int position) {
    if (filesList.size() == 0) {
      return null;
    }
    MediaModel model = filesList.get(position);
    return model.fileName.get();
  }

  @Override
  public MediaModel[] getFilesLibraryList() {
    return filesList.toArray(new MediaModel[0]);
  }


  public void updateFileName(String id, String newName) {
    if (newName.length() == 0) {
      return;
    }
    MediaModel model = repository.getRecord(id);
    model.fileName.set(newName);
    model.save();
  }

}
