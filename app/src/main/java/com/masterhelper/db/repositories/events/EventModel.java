package com.masterhelper.db.repositories.events;

import android.text.TextUtils;
import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.db.repositories.common.model.GeneralModel;

import java.io.File;

public class EventModel extends GeneralModel<EventRepository> {
  public final GeneralField<String> name = new GeneralField<>();
  public final GeneralField<String> description = new GeneralField<>();
  public final GeneralField<EventType> type = new GeneralField<>();
  public final GeneralField<String> previewId = new GeneralField<>();
  public final GeneralField<String> musicList = new GeneralField<>();

  public EventModel(EventRepository repository, String defaultId, String defaultName, String defaultDescription, EventType defaultType, String previewUrlId, String musicList){
    super(repository, defaultId);
    name.set(defaultName);
    description.set(defaultDescription);
    type.set(defaultType);
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

  public enum EventType {
    battle,
    meeting,
    accident
  }
}
