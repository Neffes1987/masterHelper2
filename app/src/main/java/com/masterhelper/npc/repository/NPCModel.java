package com.masterhelper.npc.repository;

import android.text.TextUtils;
import com.masterhelper.global.db.repositories.common.model.GeneralModel;
import com.masterhelper.global.fields.GeneralField;

public class NPCModel extends GeneralModel<NPCRepository> {

  public final GeneralField<String> age = new GeneralField<>();
  public final GeneralField<String> character = new GeneralField<>();
  public final GeneralField<String> relations = new GeneralField<>();
  public final GeneralField<String> goals = new GeneralField<>();
  public final GeneralField<String> background = new GeneralField<>();
  public final GeneralField<String> previewId = new GeneralField<>();
  public final GeneralField<String> previewUrl = new GeneralField<>();
  public final GeneralField<String> musicEffects = new GeneralField<>();

  public NPCModel(NPCRepository repository) {
    super(repository, null, "");
    age.set("0");
    character.set("");
    relations.set("");
    goals.set("");
    background.set("");
    previewId.set("");
    previewUrl.set("");
    this.musicEffects.set(null);
  }

  public NPCModel(
    NPCRepository repository,
    String defaultId,
    String defaultName,
    String defaultAge,
    String defaultCharacter,
    String defaultRelations,
    String defaultGoals,
    String defaultBackground,
    String previewUrlId,
    String musicEffects
  ) {
    super(repository, defaultId, defaultName);
    age.set(defaultAge);
    character.set(defaultCharacter);
    relations.set(defaultRelations);
    goals.set(defaultGoals);
    background.set(defaultBackground);
    previewId.set(previewUrlId);
    this.musicEffects.set(musicEffects);
  }

  public void setMusicEffectsIdsArray(String[] MusicPaths) {
    musicEffects.set(TextUtils.join(",", MusicPaths));
  }

  public String[] getMusicEffectsIds() {
    if (musicEffects.get() == null || musicEffects.get().length() == 0) {
      return new String[]{};
    }
    return musicEffects.get().split(",");
  }


}
