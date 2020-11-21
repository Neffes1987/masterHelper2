package com.masterhelper.db.repositories.events;

import android.database.Cursor;
import android.util.Log;
import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.db.DbHelpers;
import com.masterhelper.db.repositories.common.repositories.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

public class EventRepository extends AbstractRepository<EventModel> {

  public EventRepository(DbHelpers helper) {
    super(new EventContract(helper), "scene");
  }

  public void setSceneId(String id){
    ((EventContract) getContract()).setSceneId(id);
  }

  @Override
  public EventModel getDraftRecord() {
    return new EventModel(this, null, "", "", EventModel.EventType.battle, null, "");
  }

  @Override
  public EventModel[] list(int offset, int limit) {
    EventContract contract = (EventContract) getContract();
    ArrayList<EventModel> dbRecords = new ArrayList<>();
    Cursor dbList = getContract().list(offset, limit);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(EventContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());
      int typeIndex = dbList.getColumnIndex(contract.type.getColumnTitle());

      dbRecords.add(
        new EventModel(
          this,
          dbList.getString(idIndex),
          dbList.getString(nameIndex),
          dbList.getString(descriptionIndex),
          EventModel.EventType.valueOf(dbList.getString(typeIndex)),
          null,
          ""
        )
      );
    }
    dbList.close();
    setItemsToCache(dbRecords, offset);
    return dbRecords.toArray(new EventModel[0]);
  }

  public EventModel getRecord(String id){
    DataID dataID = new DataID();
    dataID.fromString(id);
    EventModel event = findRecordById(dataID);
    if(event != null){
      return event;
    }

    EventContract contract = (EventContract) getContract();
    EventModel foundedRecord = getDraftRecord();
    Cursor dbList = getContract().getRecord(id);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(EventContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());
      int typeIndex = dbList.getColumnIndex(contract.type.getColumnTitle());
      int previewUrlIndex = dbList.getColumnIndex(contract.previewUrlId.getColumnTitle());
      int musicListIndex = dbList.getColumnIndex(contract.musicList.getColumnTitle());

      foundedRecord.id.fromString(dbList.getString(idIndex));
      foundedRecord.name.set(dbList.getString(nameIndex));
      foundedRecord.description.set(dbList.getString(descriptionIndex));
      foundedRecord.previewId.fromString(dbList.getString(previewUrlIndex));
      foundedRecord.type.set(EventModel.EventType.valueOf(dbList.getString(typeIndex)));
      foundedRecord.musicList.set(dbList.getString(musicListIndex));
    }
    dbList.close();
    setItemToCache(foundedRecord, 0);
    return foundedRecord;
  }

  public int getNameLength(){
    return EventContract.NAME_COLUMN_LENGTH;
  }
  public int getDescriptionLength(){
    return EventContract.DESCRIPTION_COLUMN_LENGTH;
  }
}
