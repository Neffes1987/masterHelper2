package com.masterhelper.media.repository;

import com.masterhelper.global.fields.GeneralField;
import com.masterhelper.global.db.repositories.common.model.GeneralModel;


public class MediaModel extends GeneralModel<MediaRepository> {
  public GeneralField<String> filePath = new GeneralField<>();
  public MediaModel(MediaRepository repository, String defaultId, String defaultFilePath){
    super(repository, defaultId);
    filePath.set(defaultFilePath);
  }
}
