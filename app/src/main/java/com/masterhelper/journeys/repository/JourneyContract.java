package com.masterhelper.journeys.repository;

import android.database.Cursor;
import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.db.DbHelpers;
import com.masterhelper.db.repositories.common.contracts.GeneralColumn;
import com.masterhelper.db.repositories.common.contracts.AbstractContract;

public class JourneyContract extends AbstractContract<JourneyModel> {
  private final static String TABLE_NAME = "journeys";
  public final static int NAME_COLUMN_LENGTH = 200;
  public final static GeneralColumn id = new GeneralColumn(TABLE_NAME, "id", GeneralColumn.ColumnTypes.Primary, ID_COLUMN_LENGTH, false);
  public final GeneralColumn title = new GeneralColumn(TABLE_NAME,"title", GeneralColumn.ColumnTypes.CharType, NAME_COLUMN_LENGTH, false);

  public JourneyContract(DbHelpers dbHelpers) {
    super(dbHelpers);
    initContract(TABLE_NAME, new GeneralColumn[]{id, title});
  }

  @Override
  public void insertRecord(JourneyModel record) {
    String insertQuery = getContract().insertRecord(new String[]{record.id.get().toString(), record.name.get()});
    getDbHelpers().write(insertQuery);
  }

  @Override
  public void updateRecord(JourneyModel record) {
    String updateQuery = getContract().updateRecord(record.id, new String[]{record.id.get().toString(), record.name.get()}, id.getColumnTitle());
    getDbHelpers().write(updateQuery);
  }

  @Override
  public void deleteRecord(DataID recordId) {
    String deleteQuery = getContract().deleteRecord(recordId, id.getColumnTitle());
    getDbHelpers().write(deleteQuery);
  }

  @Override
  public Cursor list(int offset, int limit) {
    return getDbHelpers().read(getContract().selectRecords(offset, limit, getContract().getColumnsTitles(), id.getColumnTitle() + " DESC ", null));
  }

  @Override
  public Cursor getRecord(String recordId) {
    String where = id.getColumnTitle() + "='" + recordId + "'";
    return getDbHelpers().read(getContract().selectRecords(0, 0, getContract().getColumnsTitles(), id.getColumnTitle() + " DESC ", where));
  }
}
