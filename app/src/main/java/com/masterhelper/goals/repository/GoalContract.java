package com.masterhelper.goals.repository;

import android.database.Cursor;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.contracts.AbstractContract;
import com.masterhelper.global.db.repositories.common.contracts.GeneralColumn;
import com.masterhelper.plotLine.repository.PlotLineContract;

import java.util.UUID;

public class GoalContract extends AbstractContract<GoalModel> {
  public final static String TABLE_NAME = "goals";
  public final static int NAME_COLUMN_LENGTH = 200;
  public final static int DESCRIPTION_COLUMN_LENGTH = 200;
  public final static int PROGRESS_COLUMN_LENGTH = 100;
  public final static GeneralColumn id = new GeneralColumn(TABLE_NAME, "id", GeneralColumn.ColumnTypes.Primary, ID_COLUMN_LENGTH, false);
  public final static GeneralColumn externalId = new GeneralColumn(TABLE_NAME, "plotId", GeneralColumn.ColumnTypes.CharType, ID_COLUMN_LENGTH, false);
  public final static GeneralColumn title = new GeneralColumn(TABLE_NAME, "title", GeneralColumn.ColumnTypes.CharType, NAME_COLUMN_LENGTH, false);
  public final GeneralColumn description = new GeneralColumn(TABLE_NAME, "description", GeneralColumn.ColumnTypes.CharType, DESCRIPTION_COLUMN_LENGTH, false);
  public final GeneralColumn progress = new GeneralColumn(TABLE_NAME,"progress", GeneralColumn.ColumnTypes.CharType, PROGRESS_COLUMN_LENGTH, false);
  public final GeneralColumn actNumber = new GeneralColumn(TABLE_NAME,"act", GeneralColumn.ColumnTypes.Integer, 0, false);
  public final GeneralColumn assignedLocation = new GeneralColumn(TABLE_NAME, "assigned_location", GeneralColumn.ColumnTypes.CharType, 30, false);

  String plotId;

  public GoalContract(DbHelpers dbHelpers) {
    super(dbHelpers);
    initContract(TABLE_NAME, new GeneralColumn[]{id, title, description, actNumber, assignedLocation, progress});
    getContract().addDeleteForeignKeyColumn(PlotLineContract.id, externalId);
  }

  public void setPlotId(String plotId) {
    this.plotId = plotId;
  }

  @Override
  public void insertRecord(GoalModel record) {
    UUID currentAssignedLocation = record.assignedLocation.get();
    String[] values = new String[]{
      record.id.get().toString(),
      record.name.get(),
      record.description.get(),
      record.actNumber.get().toString(),
      currentAssignedLocation != null ? currentAssignedLocation.toString() : "",
      record.progress.get().name(),
      this.plotId
    };
    String insertQuery = getContract().insertRecord(values);
    getDbHelpers().write(insertQuery);
  }

  @Override
  public void updateRecord(GoalModel record) {
    UUID currentAssignedLocation = record.assignedLocation.get();
    String[] values = new String[]{
      record.id.get().toString(),
      record.name.get(),
      record.description.get(),
      record.actNumber.get().toString(),
      currentAssignedLocation != null ? currentAssignedLocation.toString() : "",
      record.progress.get().name(),
      this.plotId
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
    String where = externalId.getColumnTitle() + "='" + this.plotId + "'";
    return getDbHelpers().read(getContract().selectRecords(offset, limit, getContract().getColumnsTitles(), id.getColumnTitle() + " ASC ", where));
  }

  public Cursor listByIds(String listOfGoalIds) {
    String where = id.getColumnTitle() + " IN (" + listOfGoalIds + ")";
    return getDbHelpers().read(getContract().selectRecords(0, 0, getContract().getColumnsTitles(), id.getColumnTitle() + " ASC ", where));
  }

  @Override
  public Cursor getRecord(String recordId) {
    String where = id.getColumnTitle() + "='" + recordId + "'";
    return getDbHelpers().read(getContract().selectRecords(0, 0, getContract().getColumnsTitles(), id.getColumnTitle() + " DESC ", where));
  }
}
