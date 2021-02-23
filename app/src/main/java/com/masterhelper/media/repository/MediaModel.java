package com.masterhelper.media.repository;

import com.masterhelper.global.fields.GeneralField;
import com.masterhelper.global.db.repositories.common.model.GeneralModel;
import com.masterhelper.media.Formats;

public class MediaModel extends GeneralModel<MediaRepository> {
  public GeneralField<String> filePath = new GeneralField<>();
  public GeneralField<String> fileName = new GeneralField<>();
  public GeneralField<Formats> fileType = new GeneralField<>();
  public MediaModel(MediaRepository repository, String defaultId, String defaultFilePath, String defaultFileName, Formats defaultType){
    super(repository, defaultId, defaultFileName);
    filePath.set(defaultFilePath);
    fileName.set(defaultFileName);
    fileType.set(defaultType);
  }
}
