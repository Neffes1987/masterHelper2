package com.masterhelper.journeys.repository;

import android.database.Cursor;
import com.masterhelper.global.db.repositories.common.repositories.AbstractRepository;
import com.masterhelper.global.db.DbHelpers;

import java.util.ArrayList;
import java.util.List;

public class JourneyRepository extends AbstractRepository<JourneyModel> {

  public JourneyRepository(DbHelpers helper) {
    super(new JourneyContract(helper), "journey");
  }

  @Override
  public JourneyModel getDraftRecord() {
    return new JourneyModel(this, null, "");
  }

  @Override
  public JourneyModel[] list(int offset, int limit) {
    List<JourneyModel> cacheList = getCacheList(offset, limit);
    if(cacheList != null && cacheList.size() > 0){
      return cacheList.toArray(new JourneyModel[0]);
    }

    JourneyContract contract = (JourneyContract) getContract();
    ArrayList<JourneyModel> dbRecords = new ArrayList<>();
    Cursor dbList = getContract().list(offset, limit);
    while (dbList.moveToNext()){
      int idIndex = dbList.getColumnIndex(JourneyContract.id.getColumnTitle());
      int nameIndex = dbList.getColumnIndex(contract.title.getColumnTitle());

      dbRecords.add(new JourneyModel(this, dbList.getString(idIndex), dbList.getString(nameIndex)));
    }
    dbList.close();
    setItemsToCache(dbRecords, offset);
    return dbRecords.toArray(new JourneyModel[0]);
  }

  @Override
  public JourneyModel getRecord(String recordId) {
    return null;
  }

  public int getNameLength(){
    return JourneyContract.NAME_COLUMN_LENGTH;
  }
}
