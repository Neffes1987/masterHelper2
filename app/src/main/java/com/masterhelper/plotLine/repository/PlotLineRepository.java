package com.masterhelper.plotLine.repository;

import android.database.Cursor;
import android.util.Log;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.repositories.AbstractRepository;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.goals.repository.GoalContract;
import com.masterhelper.media.Formats;

import java.util.ArrayList;

public class PlotLineRepository extends AbstractRepository<PlotLineModel> {

  public PlotLineRepository(DbHelpers helper) {
    super(new PlotLineContract(helper), "plot");
  }

  public void setJourneyId(String id) {
    ((PlotLineContract) getContract()).setJourneyId(id);
  }

  @Override
  public PlotLineModel getDraftRecord() {
    return new PlotLineModel(this, null, "");
  }

  @Override
  public PlotLineModel[] list(int offset, int limit) {
    return new PlotLineModel[0];
  }

  PlotLineModel getModelFromCursor(Cursor list) {
    int idIndex = list.getColumnIndex(PlotLineContract.id.getColumnTitle());
    int firstPlotPontIdIndex = list.getColumnIndex(PlotLineContract.firstPlotPontId.getColumnTitle());
    int secondPlotPontIdIndex = list.getColumnIndex(PlotLineContract.secondPlotPontId.getColumnTitle());
    int thirdPlotPontIdIndex = list.getColumnIndex(PlotLineContract.thirdPlotPontId.getColumnTitle());
    int fourthPlotPontIdIndex = list.getColumnIndex(PlotLineContract.fourthPlotPontId.getColumnTitle());
    int fifthPlotPontIdIndex = list.getColumnIndex(PlotLineContract.fifthPlotPontId.getColumnTitle());
    int goalTitleIndex = list.getColumnIndex("description");
    int nameIndex = list.getColumnIndex(PlotLineContract.name.getColumnTitle());
    int progressIndex = list.getColumnIndex(PlotLineContract.plotProgress.getColumnTitle());


    PlotLineModel model = new PlotLineModel(
      this,
      list.getString(idIndex),
      list.getString(nameIndex)
    );

    if (firstPlotPontIdIndex != -1) {
      model.setActIPlotPoint(list.getString(firstPlotPontIdIndex));
    }

    if (secondPlotPontIdIndex != -1) {
      model.setActIIPlotPoint(list.getString(secondPlotPontIdIndex));
    }

    if (thirdPlotPontIdIndex != -1) {
      model.setActIIIPlotPoint(list.getString(thirdPlotPontIdIndex));
    }

    if (fourthPlotPontIdIndex != -1) {
      model.setActIVPlotPoint(list.getString(fourthPlotPontIdIndex));
    }

    if (fifthPlotPontIdIndex != -1) {
      model.setActVPlotPoint(list.getString(fifthPlotPontIdIndex));
    }

    if (goalTitleIndex != -1) {
      model.setCurrentPlotPointName(list.getString(goalTitleIndex));
    }

    model.setPlotLineProgress(list.getString(progressIndex));

    return model;
  }

  public PlotLineModel[] list() {
    PlotLineContract contract = (PlotLineContract) getContract();
    ArrayList<PlotLineModel> dbRecords = new ArrayList<>();
    Cursor dbList = contract.list(0, 0);
    while (dbList.moveToNext()) {
      dbRecords.add(
        getModelFromCursor(dbList)
      );
    }
    dbList.close();
    setItemsToCache(dbRecords, getCacheSize());
    return dbRecords.toArray(new PlotLineModel[0]);
  }

  public PlotLineModel getRecord(String id) {
    DataID dataID = new DataID();
    dataID.fromString(id);
    PlotLineModel event = findRecordById(dataID);
    if (event != null) {
      return event;
    }
    PlotLineModel foundedRecord = null;

    Cursor dbList = getContract().getRecord(id);
    while (dbList.moveToNext()) {
      foundedRecord = getModelFromCursor(dbList);
    }
    dbList.close();
    if (foundedRecord != null) {
      setItemToCache(foundedRecord, 0);
    }
    return foundedRecord;
  }

  public void delete(DataID id) {
    PlotLineContract contract = (PlotLineContract) getContract();
    contract.deleteRecord(id);
  }

}
