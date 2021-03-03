package com.masterhelper.locations.repository;

import android.database.Cursor;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.repositories.AbstractRepository;

import java.util.ArrayList;

public class LocationRepository extends AbstractRepository<LocationModel> {

  public LocationRepository(DbHelpers helper) {
    super(new LocationContract(helper), "location");
  }


  @Override
  public LocationModel getDraftRecord() {
    return new LocationModel(this, null, "", "", null, "", "");
  }

  @Override
  public LocationModel[] list(int offset, int limit) {
    LocationContract contract = (LocationContract) getContract();
    ArrayList<LocationModel> dbRecords = new ArrayList<>();
    Cursor dbList = getContract().list(offset, limit);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(LocationContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());
      int previewIdIndex = dbList.getColumnIndex(contract.previewUrlId.getColumnTitle());
      int previewUrlIndex = dbList.getColumnIndex("previewUrl");
      int musicListIndex = dbList.getColumnIndex(contract.musicList.getColumnTitle());
      LocationModel foundedRecord = getDraftRecord();
      foundedRecord.id.fromString(dbList.getString(idIndex));
      foundedRecord.name.set(dbList.getString(nameIndex));
      foundedRecord.description.set(dbList.getString(descriptionIndex));

      foundedRecord.previewId.set(dbList.getString(previewIdIndex));
      foundedRecord.previewUrl.set(previewUrlIndex != -1 ? dbList.getString(previewUrlIndex) : null);
      foundedRecord.musicList.set(dbList.getString(musicListIndex));

      dbRecords.add(foundedRecord);
    }
    dbList.close();
    setItemsToCache(dbRecords, offset);
    return dbRecords.toArray(new LocationModel[0]);
  }

  public LocationModel getRecord(String id){
    DataID dataID = new DataID();
    dataID.fromString(id);
    LocationModel event = findRecordById(dataID);
    if(event != null){
      return event;
    }

    LocationContract contract = (LocationContract) getContract();
    LocationModel foundedRecord = getDraftRecord();
    Cursor dbList = getContract().getRecord(id);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(LocationContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());
      int previewIdIndex = dbList.getColumnIndex(contract.previewUrlId.getColumnTitle());
      int previewUrlIndex = dbList.getColumnIndex("previewUrl");
      int musicListIndex = dbList.getColumnIndex(contract.musicList.getColumnTitle());
      int musicEffectsListIndex = dbList.getColumnIndex(contract.musicEffects.getColumnTitle());

      foundedRecord.id.fromString(dbList.getString(idIndex));
      foundedRecord.name.set(dbList.getString(nameIndex));
      foundedRecord.description.set(dbList.getString(descriptionIndex));

      foundedRecord.previewId.set(dbList.getString(previewIdIndex));
      foundedRecord.previewUrl.set(previewUrlIndex != -1 ? dbList.getString(previewUrlIndex) : null);
      foundedRecord.musicList.set(dbList.getString(musicListIndex));
      foundedRecord.musicEffects.set(dbList.getString(musicEffectsListIndex));
    }
    dbList.close();
    setItemToCache(foundedRecord, 0);
    return foundedRecord;
  }

  public int getNameLength() {
    return LocationContract.NAME_COLUMN_LENGTH;
  }

  public int getDescriptionLength() {
    return LocationContract.DESCRIPTION_COLUMN_LENGTH;
  }
}
