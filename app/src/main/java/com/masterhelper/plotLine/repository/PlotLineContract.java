package com.masterhelper.plotLine.repository;

import android.database.Cursor;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.contracts.AbstractContract;
import com.masterhelper.global.db.repositories.common.contracts.GeneralColumn;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.goals.repository.GoalContract;
import com.masterhelper.journeys.repository.JourneyContract;

public class PlotLineContract extends AbstractContract<PlotLineModel> {
  public final static String TABLE_NAME = "plots";
  public final static int NAME_COLUMN_LENGTH = 200;
  public final static int DESCRIPTION_COLUMN_LENGTH = 1000;
  public final static int POINT_COLUMN_LENGTH = 100;
  String journeyId;

  public final static GeneralColumn id = new GeneralColumn(TABLE_NAME, "id", GeneralColumn.ColumnTypes.Primary, ID_COLUMN_LENGTH, false);
  public final static GeneralColumn externalId = new GeneralColumn(TABLE_NAME, "journeyId", GeneralColumn.ColumnTypes.CharType, ID_COLUMN_LENGTH, false);
  public final static GeneralColumn firstPlotPontId = new GeneralColumn(TABLE_NAME, "firstPlotPontId", GeneralColumn.ColumnTypes.CharType, POINT_COLUMN_LENGTH, false);
  public final static GeneralColumn secondPlotPontId = new GeneralColumn(TABLE_NAME, "secondPlotPontId", GeneralColumn.ColumnTypes.Integer, POINT_COLUMN_LENGTH, false);
  public final static GeneralColumn thirdPlotPontId = new GeneralColumn(TABLE_NAME, "thirdPlotPontId", GeneralColumn.ColumnTypes.Integer, POINT_COLUMN_LENGTH, false);
  public final static GeneralColumn fourthPlotPontId = new GeneralColumn(TABLE_NAME, "fourthPlotPontId", GeneralColumn.ColumnTypes.Integer, POINT_COLUMN_LENGTH, false);
  public final static GeneralColumn fifthPlotPontId = new GeneralColumn(TABLE_NAME, "fifthPlotPontId", GeneralColumn.ColumnTypes.Integer, POINT_COLUMN_LENGTH, false);
  public final static GeneralColumn plotProgress = new GeneralColumn(TABLE_NAME, "plotProgress", GeneralColumn.ColumnTypes.CharType, POINT_COLUMN_LENGTH, false);
  public final static GeneralColumn description = new GeneralColumn(TABLE_NAME, "description", GeneralColumn.ColumnTypes.CharType, DESCRIPTION_COLUMN_LENGTH, false);
  public final static GeneralColumn name = new GeneralColumn(TABLE_NAME, "name", GeneralColumn.ColumnTypes.CharType, NAME_COLUMN_LENGTH, false);


  public void setJourneyId(String journeyId) {
    this.journeyId = journeyId;
  }

  public PlotLineContract(DbHelpers dbHelpers) {
    super(dbHelpers);
    initContract(TABLE_NAME, new GeneralColumn[]{
      id,
      firstPlotPontId,
      secondPlotPontId,
      thirdPlotPontId,
      fourthPlotPontId,
      fifthPlotPontId,
      plotProgress,
      description,
      name
    });
    getContract().addDeleteForeignKeyColumn(JourneyContract.id, externalId);
  }

  @Override
  public void insertRecord(PlotLineModel record) {
    String[] values = new String[]{
      record.id.toString(),
      record.getActIPlotLinePointId(),
      record.getActIIPlotLinePointId(),
      record.getActIIIPlotLinePointId(),
      record.getActIVPlotLinePointId(),
      record.getActVPlotLinePointId(),
      record.getPlotLineProgress() + "",
      "",
      record.name.get(),
      this.journeyId
    };
    String insertQuery = getContract().insertRecord(values);
    getDbHelpers().write(insertQuery);
  }

  @Override
  public void updateRecord(PlotLineModel record) {
    String[] values = new String[]{
      record.id.toString(),
      record.getActIPlotLinePointId(),
      record.getActIIPlotLinePointId(),
      record.getActIIIPlotLinePointId(),
      record.getActIVPlotLinePointId(),
      record.getActVPlotLinePointId(),
      record.getPlotLineProgress(),
      "",
      record.name.get(),
      this.journeyId
    };
    String updateQuery = getContract().updateRecord(record.id, values, id.getColumnTitle());
    getDbHelpers().write(updateQuery);
  }

  @Override
  public void deleteRecord(DataID recordId) {
    String deleteQuery = getContract().deleteRecord(recordId, id.getColumnTitle());
    getDbHelpers().write(deleteQuery);
  }

  @Override
  public Cursor list(int offset, int limit) {
    String locationFields = TABLE_NAME + "." + id.getColumnTitle()
      + "," + TABLE_NAME + "." + name.getColumnTitle()
      + "," + TABLE_NAME + "." + firstPlotPontId.getColumnTitle()
      + "," + TABLE_NAME + "." + secondPlotPontId.getColumnTitle()
      + "," + TABLE_NAME + "." + thirdPlotPontId.getColumnTitle()
      + "," + TABLE_NAME + "." + fourthPlotPontId.getColumnTitle()
      + "," + TABLE_NAME + "." + fifthPlotPontId.getColumnTitle()
      + "," + TABLE_NAME + "." + description.getColumnTitle()
      + "," + TABLE_NAME + "." + plotProgress.getColumnTitle();

    String currentGoalName = GoalContract.TABLE_NAME + "." + GoalContract.title.getColumnTitle() + " as description";

    String query = "SELECT " + locationFields + "," + currentGoalName
      + " FROM " + TABLE_NAME + " LEFT OUTER JOIN " + GoalContract.TABLE_NAME
      + " ON " + TABLE_NAME + "." + plotProgress.getColumnTitle() + "=" + GoalContract.TABLE_NAME + "." + GoalContract.id.getColumnTitle()
      + " ORDER BY " + TABLE_NAME + "." + name.getColumnTitle() + " ASC ";
    return getDbHelpers().read(query);

  }

  @Override
  public Cursor getRecord(String recordId) {
    String where = id.getColumnTitle() + "='" + recordId + "'";
    return getDbHelpers().read(getContract().selectRecords(0, 0, getContract().getColumnsTitles(), name.getColumnTitle() + " ASC ", where));
  }
}
