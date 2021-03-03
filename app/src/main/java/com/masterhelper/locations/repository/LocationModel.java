package com.masterhelper.locations.repository;

import android.text.TextUtils;
import com.masterhelper.global.fields.GeneralField;
import com.masterhelper.global.db.repositories.common.model.GeneralModel;

public class LocationModel extends GeneralModel<LocationRepository> {
  public final GeneralField<String> description = new GeneralField<>();
  public final GeneralField<String> previewId = new GeneralField<>();
  public final GeneralField<String> previewUrl = new GeneralField<>();
  public final GeneralField<String> musicList = new GeneralField<>();
  public final GeneralField<String> musicEffects = new GeneralField<>();

  public LocationModel(LocationRepository repository, String defaultId, String defaultName, String defaultDescription, String previewUrlId, String musicList, String musicEffects) {
    super(repository, defaultId, defaultName);
    description.set(defaultDescription);
    previewId.set(previewUrlId);
    this.musicList.set(musicList);
    this.musicEffects.set(musicEffects);
  }

  public void setMusicIdsArray(String[] MusicPaths) {
    musicList.set(TextUtils.join(",", MusicPaths));
  }

  public String[] getMusicIds() {
    if (musicList.get() == null || musicList.get().length() == 0) {
      return new String[]{};
    }
    return musicList.get().split(",");
  }
}
