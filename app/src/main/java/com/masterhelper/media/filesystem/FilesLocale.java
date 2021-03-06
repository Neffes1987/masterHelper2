package com.masterhelper.media.filesystem;

import android.content.Context;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;

/** static facade class with localized strings of journeys */
public abstract class FilesLocale {

  /** get localize value from vocabulary by key
   * @param localeKey - one of available keys in vocabulary
   * */
  public static String getLocalizationByKey(Keys localeKey){
    Context context = GlobalApplication.getAppContext();
    switch (localeKey){
      case audiosListCaption:
        return context.getResources().getString(R.string.audio_files_caption);
      case imagesListCaption:
        return context.getResources().getString(R.string.images_files_caption);
      case emptyAudioFile:
        return context.getResources().getString(R.string.audio_files_empty_list);
      case removeSourceFiles:
        return context.getResources().getString(R.string.dialog_remove_source_files);
      case editFileName:
        return context.getResources().getString(R.string.dialog_edit_source_file);
      case addNewMedia:
        return context.getResources().getString(R.string.add_files);
    }
    return "";
  }

  /**
   * available vocabulary keys
   */
  public enum Keys {
    audiosListCaption,
    addNewMedia,
    imagesListCaption,
    removeSourceFiles,
    editFileName,
    emptyAudioFile
  }
}
