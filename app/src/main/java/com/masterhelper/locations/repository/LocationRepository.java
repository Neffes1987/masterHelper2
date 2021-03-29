package com.masterhelper.locations.repository;

import android.database.Cursor;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.repositories.AbstractRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class LocationRepository extends AbstractRepository<LocationModel> {

  public LocationRepository(DbHelpers helper) {
    super(new LocationContract(helper), "location");
  }


  @Override
  public LocationModel getDraftRecord() {
    return new LocationModel(this, null, "", "", null, "", "");
  }


  @Override
  public LocationModel[] list(int offset, int limit, String searchStr) {
    ArrayList<LocationModel> dbRecords = new ArrayList<>();
    Cursor dbList = ((LocationContract) getContract()).list(searchStr);
    while (dbList.moveToNext()) {
      dbRecords.add(extractDataFromCursor(dbList));
    }
    dbList.close();
    setItemsToCache(dbRecords, offset);
    return dbRecords.toArray(new LocationModel[0]);
  }

  public HashMap<String, String> getDropdownList(String searchStr) {
    HashMap<String, String> dbRecords = new HashMap<>();
    Cursor dbList = ((LocationContract) getContract()).getLocationsDropDownList(searchStr);
    while (dbList.moveToNext()) {
      int idIndex = dbList.getColumnIndex(LocationContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(LocationContract.title.getColumnTitle());
      dbRecords.put(dbList.getString(idIndex), dbList.getString(nameIndex));
    }
    dbList.close();
    return dbRecords;
  }

  public LocationModel getRecord(String id) {
    DataID dataID = new DataID();
    dataID.fromString(id);
    LocationModel event = findRecordById(dataID);
    if (event != null) {
      return event;
    }
    Cursor dbList = getContract().getRecord(id);
    LocationModel foundedRecord = getDraftRecord();
    while (dbList.moveToNext()) {
      foundedRecord = extractDataFromCursor(dbList);
    }
    dbList.close();
    setItemToCache(foundedRecord, 0);
    return foundedRecord;
  }

  @Override
  public LocationModel extractDataFromCursor(Cursor cursor) {
    LocationModel foundedRecord = getDraftRecord();
    LocationContract contract = (LocationContract) getContract();
    int idIndex = cursor.getColumnIndex(LocationContract.id.getColumnTitle());
    int nameIndex = cursor.getColumnIndex(LocationContract.title.getColumnTitle());
    int descriptionIndex = cursor.getColumnIndex(contract.description.getColumnTitle());
    int previewIdIndex = cursor.getColumnIndex(contract.previewUrlId.getColumnTitle());
    int previewUrlIndex = cursor.getColumnIndex("previewUrl");
    int musicListIndex = cursor.getColumnIndex(contract.musicList.getColumnTitle());
    int musicEffectsListIndex = cursor.getColumnIndex(contract.musicEffects.getColumnTitle());

    foundedRecord.id.fromString(cursor.getString(idIndex));
    foundedRecord.name.set(cursor.getString(nameIndex));
    foundedRecord.description.set(cursor.getString(descriptionIndex));

    foundedRecord.previewId.set(cursor.getString(previewIdIndex));
    foundedRecord.previewUrl.set(previewUrlIndex != -1 ? cursor.getString(previewUrlIndex) : null);
    foundedRecord.musicList.set(cursor.getString(musicListIndex));
    foundedRecord.musicEffects.set(musicEffectsListIndex != -1 ? cursor.getString(musicEffectsListIndex) : null);
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
