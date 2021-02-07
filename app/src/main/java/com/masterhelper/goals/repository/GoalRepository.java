package com.masterhelper.goals.repository;

import android.database.Cursor;
import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.db.DbHelpers;
import com.masterhelper.db.repositories.common.repositories.AbstractRepository;

import java.util.ArrayList;

public class GoalRepository extends AbstractRepository<GoalModel> {

  public GoalRepository(DbHelpers helper) {
    super(new GoalContract(helper), "scene");
  }

  public void setJourneyId(String id){
    ((GoalContract) getContract()).setJourneyId(id);
  }

  @Override
  public GoalModel getDraftRecord() {
    return new GoalModel(this, null, "", "", 0, null);
  }

  @Override
  public GoalModel[] list(int offset, int limit) {
    GoalContract contract = (GoalContract) getContract();
    ArrayList<GoalModel> dbRecords = new ArrayList<>();
    Cursor dbList = getContract().list(offset, limit);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(GoalContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());

      dbRecords.add(
        new GoalModel(
          this,
          dbList.getString(idIndex),
          dbList.getString(nameIndex),
          dbList.getString(descriptionIndex),
          0,
          null
        )
      );
    }
    dbList.close();
    setItemsToCache(dbRecords, offset);
    return dbRecords.toArray(new GoalModel[0]);
  }

  public GoalModel[] listByAct(int currentAct) {
    GoalContract contract = (GoalContract) getContract();
    ArrayList<GoalModel> dbRecords = new ArrayList<>();
    Cursor dbList = contract.listByAct(currentAct);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(GoalContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());

      dbRecords.add(
        new GoalModel(
          this,
          dbList.getString(idIndex),
          dbList.getString(nameIndex),
          dbList.getString(descriptionIndex),
          0,
          null
        )
      );
    }
    dbList.close();
    setItemsToCache(dbRecords, 0);
    return dbRecords.toArray(new GoalModel[0]);
  }


  @Override
  public GoalModel getRecord(String recordId) {
    DataID dataID = new DataID();
    dataID.fromString(recordId);
    GoalModel scene = findRecordById(dataID);
    if(scene != null){
      return scene;
    }

    GoalContract contract = (GoalContract) getContract();
    GoalModel foundedRecord = getDraftRecord();
    Cursor dbList = getContract().getRecord(recordId);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(GoalContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());
      int actIndex = dbList.getColumnIndex(contract.actNumber.getColumnTitle());
      int locationIndex = dbList.getColumnIndex(contract.assignedLocation.getColumnTitle());
      foundedRecord.description.set(dbList.getString(descriptionIndex));
      foundedRecord.name.set(dbList.getString(nameIndex));
      foundedRecord.id.fromString(dbList.getString(idIndex));
      foundedRecord.assignedLocation.fromString(dbList.getString(locationIndex));
      foundedRecord.actNumber.set(dbList.getInt(actIndex));
    }
    dbList.close();
    setItemToCache(foundedRecord, 0);
    return foundedRecord;

  }

  public int getNameLength(){
    return GoalContract.NAME_COLUMN_LENGTH;
  }
  public int getDescriptionLength(){
    return GoalContract.DESCRIPTION_COLUMN_LENGTH;
  }
}
