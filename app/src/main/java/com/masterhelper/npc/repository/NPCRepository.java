package com.masterhelper.npc.repository;

import android.database.Cursor;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.repositories.AbstractRepository;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.locations.repository.LocationContract;

import java.util.ArrayList;
import java.util.HashMap;

public class NPCRepository extends AbstractRepository<NPCModel> {

  public NPCRepository(DbHelpers helper) {
    super(new NPCContract(helper), "npc");
  }

  @Override
  public NPCModel getDraftRecord() {
    return new NPCModel(this);
  }

  public HashMap<String, String> getDropdownList(String searchStr) {
    HashMap<String, String> dbRecords = new HashMap<>();
    Cursor dbList = ((NPCContract) getContract()).getDropDownList(searchStr);
    while (dbList.moveToNext()) {
      int idIndex = dbList.getColumnIndex(LocationContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(LocationContract.title.getColumnTitle());
      dbRecords.put(dbList.getString(idIndex), dbList.getString(nameIndex));
    }
    dbList.close();
    return dbRecords;
  }

  @Override
  public NPCModel[] list(int offset, int limit, String searchStr) {
    ArrayList<NPCModel> dbRecords = new ArrayList<>();
    Cursor dbList = ((NPCContract) getContract()).list(searchStr);
    while (dbList.moveToNext()) {
      dbRecords.add(extractDataFromCursor(dbList));
    }
    dbList.close();
    setItemsToCache(dbRecords, offset);
    return dbRecords.toArray(new NPCModel[0]);
  }

  public NPCModel getRecord(String id) {
    DataID dataID = new DataID();
    dataID.fromString(id);
    NPCModel event = findRecordById(dataID);
    if (event != null) {
      return event;
    }
    Cursor dbList = getContract().getRecord(id);
    NPCModel foundedRecord = getDraftRecord();
    while (dbList.moveToNext()) {
      foundedRecord = extractDataFromCursor(dbList);
    }
    dbList.close();
    setItemToCache(foundedRecord, 0);
    return foundedRecord;
  }

  @Override
  public NPCModel extractDataFromCursor(Cursor cursor) {
    NPCModel foundedRecord = getDraftRecord();
    NPCContract contract = (NPCContract) getContract();
    int idIndex = cursor.getColumnIndex(NPCContract.id.getColumnTitle());
    int nameIndex = cursor.getColumnIndex(contract.title.getColumnTitle());
    int ageIndex = cursor.getColumnIndex(contract.age.getColumnTitle());
    int characterIndex = cursor.getColumnIndex(contract.character.getColumnTitle());
    int relationsIndex = cursor.getColumnIndex(contract.relations.getColumnTitle());
    int goalsIndex = cursor.getColumnIndex(contract.goals.getColumnTitle());
    int backgroundIndex = cursor.getColumnIndex(contract.background.getColumnTitle());

    int previewIdIndex = cursor.getColumnIndex(contract.previewUrlId.getColumnTitle());
    int previewUrlIndex = cursor.getColumnIndex("previewUrl");
    int musicEffectsListIndex = cursor.getColumnIndex(contract.musicEffects.getColumnTitle());

    foundedRecord.id.fromString(cursor.getString(idIndex));
    foundedRecord.name.set(cursor.getString(nameIndex));
    foundedRecord.character.set(cursor.getString(characterIndex));
    foundedRecord.relations.set(cursor.getString(relationsIndex));
    foundedRecord.goals.set(cursor.getString(goalsIndex));
    foundedRecord.background.set(cursor.getString(backgroundIndex));
    foundedRecord.age.set(cursor.getString(ageIndex));

    foundedRecord.previewId.set(cursor.getString(previewIdIndex));
    foundedRecord.previewUrl.set(previewUrlIndex != -1 ? cursor.getString(previewUrlIndex) : null);
    foundedRecord.musicEffects.set(musicEffectsListIndex != -1 ? cursor.getString(musicEffectsListIndex) : null);
    setItemToCache(foundedRecord, 0);
    return foundedRecord;
  }

  public int getNameLength() {
    return NPCContract.NAME_COLUMN_LENGTH;
  }

  public int getCharacterLength() {
    return NPCContract.CHARACTER_COLUMN_LENGTH;
  }

  public int getRelationLength() {
    return NPCContract.RELATIONS_COLUMN_LENGTH;
  }

  public int getGoalsLength() {
    return NPCContract.GOALS_COLUMN_LENGTH;
  }

  public int getBackgroundLength() {
    return NPCContract.BACKGROUND_COLUMN_LENGTH;
  }
}
