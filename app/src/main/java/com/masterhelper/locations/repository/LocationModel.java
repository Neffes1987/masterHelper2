package com.masterhelper.locations.repository;

import android.text.TextUtils;
import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.db.repositories.common.model.GeneralModel;

public class LocationModel extends GeneralModel<LocationRepository> {
  public final GeneralField<String> name = new GeneralField<>();
  public final GeneralField<String> description = new GeneralField<>();
  public final GeneralField<String> previewId = new GeneralField<>();
  public final GeneralField<String> musicList = new GeneralField<>();

  public LocationModel(LocationRepository repository, String defaultId, String defaultName, String defaultDescription, String previewUrlId, String musicList){
    super(repository, defaultId);
    name.set(defaultName);
    description.set(defaultDescription);
    previewId.set(previewUrlId);

    this.musicList.set(musicList);
  }

  public void setMusicPathsArray(String[] MusicPaths){
    musicList.set(TextUtils.join(",", MusicPaths));
  }

  public String[] getMusicHashes(){
    if(musicList.get() == null || musicList.get().length() == 0){
      return new String[]{};
    }
    return musicList.get().split(",");
  }
}
