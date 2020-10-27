package com.masterhelper.db.contracts.journeys;

import com.masterhelper.baseclasses.BaseModel;

public class JourneyModel implements BaseModel, JourneyRepository.IJourneyRepository {


  @Override
  public void save() {
    repo.updateItem(this);
  }


  @Override
  public void setRepo(JourneyRepository repo) {
  }
}
