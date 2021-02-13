package com.masterhelper.media.repository;

import android.database.Cursor;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.repositories.AbstractRepository;
import com.masterhelper.media.Formats;

import java.util.ArrayList;

public class MediaRepository extends AbstractRepository<MediaModel> {

  public MediaRepository(DbHelpers helper) {
    super(new MediaContract(helper), "media");
  }

  @Override
  public MediaModel getDraftRecord() {
    return new MediaModel(this, null, "", "", null);
  }

  @Override
  public MediaModel[] list(int offset, int limit) {
    return new MediaModel[0];
  }

  public MediaModel[] list(String type) {
    MediaContract contract = (MediaContract) getContract();
    ArrayList<MediaModel> dbRecords = new ArrayList<>();
    Cursor dbList = ((MediaContract) getContract()).listByType(type);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(com.masterhelper.locations.repository.LocationContract.id.getColumnTitle());
      int pathIndex = dbList.getColumnIndex(contract.filePath.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.fileName.getColumnTitle());
      int fileTypeIndex = dbList.getColumnIndex(contract.fileType.getColumnTitle());

      dbRecords.add(
        new MediaModel(
          this,
          dbList.getString(idIndex),
          dbList.getString(pathIndex),
          dbList.getString(nameIndex),
          Formats.valueOf(dbList.getString(fileTypeIndex))
        )
      );
    }
    dbList.close();
    setItemsToCache(dbRecords, getCacheSize());
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
      int nameIndex = dbList.getColumnIndex(contract.fileName.getColumnTitle());
      int fileTypeIndex = dbList.getColumnIndex(contract.fileType.getColumnTitle());

      foundedRecord.id.fromString(dbList.getString(idIndex));
      foundedRecord.filePath.set(dbList.getString(filePath));
      foundedRecord.fileName.set(dbList.getString(nameIndex));
      foundedRecord.fileType.set(Formats.valueOf(dbList.getString(fileTypeIndex)));
    }
    dbList.close();
    setItemToCache(foundedRecord, 0);
    return foundedRecord;
  }

  public void delete(DataID id){
    MediaContract contract = (MediaContract) getContract();
    contract.deleteRecord(id);
  }

}
