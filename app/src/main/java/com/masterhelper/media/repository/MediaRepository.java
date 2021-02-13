package com.masterhelper.media.repository;

import android.database.Cursor;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.repositories.AbstractRepository;

import java.util.ArrayList;

public class MediaRepository extends AbstractRepository<MediaModel> {

  public MediaRepository(DbHelpers helper) {
    super(new MediaContract(helper), "media");
  }

  @Override
  public MediaModel getDraftRecord() {
    return new MediaModel(this, null, "");
  }

  @Override
  public MediaModel[] list(int offset, int limit) {
    MediaContract contract = (MediaContract) getContract();
    ArrayList<MediaModel> dbRecords = new ArrayList<>();
    Cursor dbList = getContract().list(offset, limit);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(com.masterhelper.locations.repository.LocationContract.id.getColumnTitle());
      int pathIndex = dbList.getColumnIndex(contract.filePath.getColumnTitle());

      dbRecords.add(
        new MediaModel(
          this,
          dbList.getString(idIndex),
          dbList.getString(pathIndex)
        )
      );
    }
    dbList.close();
    setItemsToCache(dbRecords, offset);
    return dbRecords.toArray(new MediaModel[0]);
  }

  public MediaModel getRecord(String id){
    DataID dataID = new DataID();
    dataID.fromString(id);
    MediaModel event = findRecordById(dataID);
    if(event != null){
      return event;
    }

    MediaContract contract = (MediaContract) getContract();
    MediaModel foundedRecord = getDraftRecord();
    Cursor dbList = getContract().getRecord(id);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(com.masterhelper.locations.repository.LocationContract.id.getColumnTitle());
      int filePath = dbList.getColumnIndex(contract.filePath.getColumnTitle());

      foundedRecord.id.fromString(dbList.getString(idIndex));
      foundedRecord.filePath.set(dbList.getString(filePath));
    }
    dbList.close();
    setItemToCache(foundedRecord, 0);
    return foundedRecord;
  }

}
