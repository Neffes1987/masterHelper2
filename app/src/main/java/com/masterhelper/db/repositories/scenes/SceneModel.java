package com.masterhelper.db.repositories.scenes;

import android.text.TextUtils;
import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.db.repositories.common.model.GeneralModel;

public class SceneModel extends GeneralModel<SceneRepository> {
  public final GeneralField<String> name = new GeneralField<>();
  public final GeneralField<String> description = new GeneralField<>();
  public final GeneralField<Integer> totalEvents = new GeneralField<>();
  public final GeneralField<Integer> finishedEvents = new GeneralField<>();
  public final GeneralField<String> musicList = new GeneralField<>();

  public SceneModel(SceneRepository repository, String defaultId, String defaultName, String defaultDescription, int defaultTotalEvents, int defaultFinishedEvents, String musicList){
    super(repository, defaultId);
    name.set(defaultName);
    description.set(defaultDescription);
    totalEvents.set(defaultTotalEvents);
    finishedEvents.set(defaultFinishedEvents);
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
