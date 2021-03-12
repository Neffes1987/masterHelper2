package com.masterhelper.goals.repository;

import android.database.Cursor;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.repositories.AbstractRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class GoalRepository extends AbstractRepository<GoalModel> {

  public GoalRepository(DbHelpers helper) {
    super(new GoalContract(helper), "goal");
  }

  public void setPlotId(String id) {
    ((GoalContract) getContract()).setPlotId(id);
  }

  @Override
  public GoalModel getDraftRecord() {
    return new GoalModel(this, null, "", "", 1, null, GoalModel.GoalProgress.inProgress);
  }

  @Override
  public GoalModel[] list(int offset, int limit, String searchStr) {
    GoalContract contract = (GoalContract) getContract();
    ArrayList<GoalModel> dbRecords = new ArrayList<>();
    Cursor dbList = getContract().list(offset, limit);
    while (dbList.moveToNext()) {
      int idIndex = dbList.getColumnIndex(GoalContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());
      int progressIndex = dbList.getColumnIndex(contract.progress.getColumnTitle());

      String progress = dbList.getString(progressIndex);
      dbRecords.add(
        new GoalModel(
          this,
          dbList.getString(idIndex),
          dbList.getString(nameIndex),
          dbList.getString(descriptionIndex),
          1,
          null,
          progress != null ? GoalModel.GoalProgress.valueOf(dbList.getString(progressIndex)) : null
        )
      );
    }
    dbList.close();
    setItemsToCache(dbRecords, offset);
    return dbRecords.toArray(new GoalModel[0]);
  }

  public HashMap<String, GoalModel> listByIds(String listByIds) {
    GoalContract contract = (GoalContract) getContract();
    HashMap<String, GoalModel> dbRecords = new HashMap<>();
    if (listByIds.length() == 0) {
      return dbRecords;
    }
    Cursor dbList = contract.listByIds(listByIds);
    while (dbList.moveToNext()) {
      int idIndex = dbList.getColumnIndex(GoalContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());
      int descriptionIndex = dbList.getColumnIndex(contract.description.getColumnTitle());
      int progressIndex = dbList.getColumnIndex(contract.progress.getColumnTitle());
      int actIndex = dbList.getColumnIndex(contract.actNumber.getColumnTitle());

      String progress = dbList.getString(progressIndex);
      dbRecords.put(
        dbList.getString(idIndex),
        new GoalModel(
          this,
          dbList.getString(idIndex),
          dbList.getString(nameIndex),
          dbList.getString(descriptionIndex),
          dbList.getInt(actIndex),
          null,
          progress != null ? GoalModel.GoalProgress.valueOf(dbList.getString(progressIndex)) : null
        )
      );
    }
    dbList.close();
    return dbRecords;
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

  @Override
  public GoalModel extractDataFromCursor(Cursor cursor) {
    return null;
  }

  public int getNameLength() {
    return GoalContract.NAME_COLUMN_LENGTH;
  }

  public int getDescriptionLength() {
    return GoalContract.DESCRIPTION_COLUMN_LENGTH;
  }
}
