package com.masterhelper.db.repositories.scenes;

import android.database.Cursor;
import com.masterhelper.db.DbHelpers;
import com.masterhelper.db.repositories.common.repositories.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

public class SceneRepository extends AbstractRepository<SceneModel> {

  public SceneRepository(DbHelpers helper) {
    super(new SceneContract(helper), "scene");
  }

  public void setJourneyId(String id){
    ((SceneContract) getContract()).setJourneyId(id);
  }

  @Override
  public SceneModel getDraftRecord() {
    return new SceneModel(this, null, "", "", 0, 0);
  }

  @Override
  public SceneModel[] list(int offset, int limit) {
    List<SceneModel> cacheList = getCacheList(offset, limit);
    if(cacheList != null && cacheList.size() > 0){
      return cacheList.toArray(new SceneModel[0]);
    }

    SceneContract contract = (SceneContract) getContract();
    ArrayList<SceneModel> dbRecords = new ArrayList<>();
    Cursor dbList = getContract().list(offset, limit);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(SceneContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());

      dbRecords.add(
        new SceneModel(
          this,
          dbList.getString(idIndex),
          dbList.getString(nameIndex),
          dbList.getString(descriptionIndex),
          0,
          0
        )
      );
    }
    setItemsToCache(dbRecords, offset);
    return dbRecords.toArray(new SceneModel[0]);
  }

  public int getNameLength(){
    return SceneContract.NAME_COLUMN_LENGTH;
  }
  public int getDescriptionLength(){
    return SceneContract.DESCRIPTION_COLUMN_LENGTH;
  }
}
