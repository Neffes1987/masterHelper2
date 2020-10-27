package com.masterhelper.db.contracts.journeys;

import com.masterhelper.baseclasses.BaseRepository;

public class JourneyRepository implements BaseRepository<JourneyModel> {

  @Override
  public void createItem() {

  }

  @Override
  public void updateItem(JourneyModel record) {

  }

  @Override
  public void deleteItem(JourneyModel record) {

  }

  @Override
  public JourneyModel getItem() {
    return null;
  }

  public interface IJourneyRepository{
    JourneyRepository repo = null;
    void setRepo(JourneyRepository repo);
  }
}
