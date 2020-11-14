package com.masterhelper.db.repositories.scenes;

import android.database.Cursor;
import android.util.Log;
import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.db.DbHelpers;
import com.masterhelper.db.repositories.common.repositories.AbstractRepository;

import java.util.ArrayList;

public class SceneRepository extends AbstractRepository<SceneModel> {

  public SceneRepository(DbHelpers helper) {
    super(new SceneContract(helper), "scene");
  }

  public void setJourneyId(String id){
    ((SceneContract) getContract()).setJourneyId(id);
  }

  @Override
  public SceneModel getDraftRecord() {
    return new SceneModel(this, null, "", "", 0, 0, "");
  }

  @Override
  public SceneModel[] list(int offset, int limit) {
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
          0,
          ""
        )
      );
    }
    dbList.close();
    setItemsToCache(dbRecords, offset);
    return dbRecords.toArray(new SceneModel[0]);
  }

  @Override
  public SceneModel getRecord(String recordId) {
    DataID dataID = new DataID();
    dataID.fromString(recordId);
    SceneModel scene = findRecordById(dataID);
    if(scene != null){
      return scene;
    }

    SceneContract contract = (SceneContract) getContract();
    SceneModel foundedRecord = getDraftRecord();
    Cursor dbList = getContract().getRecord(recordId);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(SceneContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());
      int musicIndex = dbList.getColumnIndex(contract.musicList.getColumnTitle());
      foundedRecord.description.set(dbList.getString(descriptionIndex));
      foundedRecord.name.set(dbList.getString(nameIndex));
      foundedRecord.id.fromString(dbList.getString(idIndex));
      foundedRecord.musicList.set(dbList.getString(musicIndex));
    }
    dbList.close();
    setItemToCache(foundedRecord, 0);
    return foundedRecord;

  }

  public int getNameLength(){
    return SceneContract.NAME_COLUMN_LENGTH;
  }
  public int getDescriptionLength(){
    return SceneContract.DESCRIPTION_COLUMN_LENGTH;
  }
}
