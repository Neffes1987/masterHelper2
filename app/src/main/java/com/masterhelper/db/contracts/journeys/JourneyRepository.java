package com.masterhelper.db.contracts.journeys;

import android.database.Cursor;
import com.masterhelper.baseclasses.repository.AbstractRepository;
import com.masterhelper.global.GlobalApplication;

import java.util.ArrayList;

public class JourneyRepository extends AbstractRepository<JourneyModel> {

  public JourneyRepository() {
    super(GlobalApplication.getAppDB().journeyContract, "journey");
  }

  @Override
  public JourneyModel getDraftRecord() {
    JourneyModel newJourney = new JourneyModel(this, null, "");
    newJourney.type.set(this.repositoryName.get());
    return newJourney;
  }

  @Override
  public JourneyModel[] list(int offset, int limit) {
    JourneyModel[] result = getCacheList(offset, limit).toArray(new JourneyModel[0]);
    JourneyContract contract = (JourneyContract) getContract();
    if(result.length == 0){
      ArrayList<JourneyModel> dbRecords = new ArrayList<>();
      Cursor dbList = getContract().list(offset, limit);
      while (dbList.moveToNext()){
        int idIndex = dbList.getColumnIndex(contract.JourneyId.getColumnTitle());
        int nameIndex = dbList.getColumnIndex(contract.JourneyTitle.getColumnTitle());
        dbRecords.add(new JourneyModel(this, dbList.getString(idIndex), dbList.getString(nameIndex)));
      }
      result = dbRecords.toArray(new JourneyModel[0]);
      setItemsToCache(dbRecords, offset);
    }
    return result;
  }
}
