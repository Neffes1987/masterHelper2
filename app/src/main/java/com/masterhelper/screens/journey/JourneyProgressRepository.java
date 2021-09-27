package com.masterhelper.screens.journey;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.global.db.repository.AbstractRepository;
import com.masterhelper.global.db.repository.ContractColumn;
import com.masterhelper.screens.plotTwist.PlotTwistModel;
import com.masterhelper.screens.plotTwist.PlotTwistRepository;

import java.util.ArrayList;
import java.util.UUID;

public class JourneyProgressRepository extends AbstractRepository<JourneyProgressRepository.JourneyProgressModal> {
  static final String TABLE_NAME = "journeyProgress";

  static ContractColumn id = new ContractColumn(TABLE_NAME);
  static ContractColumn journeyIdColumn = new ContractColumn(TABLE_NAME, "journeyId", ContractColumn.ColumnType.ID);
  static ContractColumn externalEntityId = new ContractColumn(TABLE_NAME, "externalEntityId", ContractColumn.ColumnType.ID);
  static ContractColumn externalEntityType = new ContractColumn(TABLE_NAME, "externalEntityType", ContractColumn.ColumnType.CharType, 200);
  static ContractColumn journeyActNumber = new ContractColumn(TABLE_NAME, "journeyActNumber", ContractColumn.ColumnType.Integer);

  public JourneyProgressRepository(SQLiteDatabase db) {
    super(db, TABLE_NAME, new ContractColumn[]{
      id, journeyIdColumn, externalEntityId, externalEntityType, journeyActNumber
    });
  }

  @Override
  public String[] extractFields(JourneyProgressModal model) {

    return new String[]{
      model.getId(),
      model.getJourneyId(),
      model.getEntityId(),
      model.getEntityType(),
      model.getJourneyActNumber() + ""
    };
  }

  public ArrayList<PlotTwistModel> getDetachedPlots(String journeyId) {
    ArrayList<PlotTwistModel> list = new ArrayList<>();


    String query = formatQuery("SELECT {4}.*, {0}.{1} as jid FROM {4} LEFT JOIN {0} ON {0}.{2} = {4}.{5} WHERE {0}.{1} != \"{3}\" OR {0}.{1} IS NULL", new String[]{
      TABLE_NAME, //0
      journeyIdColumn.getColumnTitle(),//1
      externalEntityId.getColumnTitle(),//2
      journeyId,//3

      PlotTwistRepository.TABLE_NAME, //4
      PlotTwistRepository.id.getColumnTitle()//5
    });

    Log.i("TAG", "getDetachedPlots: " + query);

    Cursor relatedPlotsCursor = getRawData(query);

    while (relatedPlotsCursor.moveToNext()) {

      list.add(new PlotTwistModel(relatedPlotsCursor));
    }

    return list;
  }

  public ArrayList<PlotTwistModel> getJourneyPlots(JourneyModel journeyModel) {
    ArrayList<PlotTwistModel> list = new ArrayList<>();

    String query = formatQuery("SELECT {4}.* FROM {4} JOIN {0} ON {4}.{5} = {0}.{2} WHERE {0}.{1} = \"{6}\" AND {0}.{3} = \"{7}\"", new String[]{
      TABLE_NAME, //0
      journeyIdColumn.getColumnTitle(),//1
      externalEntityId.getColumnTitle(),//2
      journeyActNumber.getColumnTitle(),//3

      PlotTwistRepository.TABLE_NAME, //4
      PlotTwistRepository.id.getColumnTitle(),//5

      journeyModel.getId(),//6
      journeyModel.getSessionAct() + ""//7
    });

    Cursor relatedPlotsCursor = getRawData(query);

    while (relatedPlotsCursor.moveToNext()) {
      list.add(new PlotTwistModel(relatedPlotsCursor));
    }

    return list;
  }

  @Override
  public JourneyProgressModal get(String id) {
    return new JourneyProgressModal(getRecord(id));
  }

  @Override
  public ArrayList<JourneyProgressModal> list(int offset, int limit, String ordering, String where) {
    return null;
  }

  @Override
  public JourneyProgressModal draft() {
    return null;
  }

  public void detachPlot(String plotId) {
    String query = formatQuery("DELETE FROM {0} WHERE {1}=\"{2}\"", new String[]{TABLE_NAME, externalEntityId.getColumnTitle(), plotId});
    execQuery(query);
  }

  public void setPlotToJourney(String journeyId, int actNumber, String plotId) {
    JourneyProgressModal progressModel = new JourneyProgressModal(UUID.randomUUID().toString(), EntityType.Plot);
    progressModel.setJourneyId(journeyId);
    progressModel.setEntityId(plotId);
    progressModel.setJourneyActNumber(actNumber);
    create(progressModel);
  }

  public static class JourneyProgressModal extends AbstractModel {
    String journeyId;
    int journeyActNumber;
    String entityId;
    EntityType entityType;

    public JourneyProgressModal(Cursor cursor) {
      super(cursor.getString(cursor.getColumnIndex(JourneyProgressRepository.id.getColumnTitle())));

      int entityIdIndex = cursor.getColumnIndex(JourneyProgressRepository.externalEntityId.getColumnTitle());
      int entityTypeIndex = cursor.getColumnIndex(JourneyProgressRepository.externalEntityType.getColumnTitle());
      int journeyIdIndex = cursor.getColumnIndex(JourneyProgressRepository.journeyIdColumn.getColumnTitle());
      int journeyActNumberIndex = cursor.getColumnIndex(JourneyProgressRepository.journeyActNumber.getColumnTitle());

      setJourneyId(cursor.getString(journeyIdIndex));
      setEntityId(cursor.getString(entityIdIndex));
      setEntityType(cursor.getString(entityTypeIndex));
      setJourneyActNumber(cursor.getInt(journeyActNumberIndex));
    }

    public JourneyProgressModal(String id, EntityType type) {
      super(id);
      entityType = type;
    }

    public void setJourneyId(String journeyId) {
      this.journeyId = journeyId;
    }

    public String getJourneyId() {
      return journeyId;
    }

    public void setEntityId(String plotTwistId) {
      this.entityId = plotTwistId;
    }

    public String getEntityId() {
      return entityId;
    }

    public String getEntityType() {
      return entityType.name();
    }

    public void setEntityType(String entityType) {
      this.entityType = EntityType.valueOf(entityType);
    }

    public void setJourneyActNumber(int journeyActNumber) {
      this.journeyActNumber = journeyActNumber;
    }

    public int getJourneyActNumber() {
      return journeyActNumber;
    }
  }

  public enum EntityType{
    Plot
  }

}
