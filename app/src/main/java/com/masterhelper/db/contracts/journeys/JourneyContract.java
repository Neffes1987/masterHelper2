package com.masterhelper.db.contracts.journeys;

import android.database.Cursor;
import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.db.DbHelpers;
import com.masterhelper.db.contracts.common.GeneralColumn;
import com.masterhelper.db.contracts.common.AbstractContract;

public class JourneyContract extends AbstractContract<JourneyModel> {
  private final String TABLE_NAME = "journeys";
  public final GeneralColumn JourneyId = new GeneralColumn(TABLE_NAME, "journeyId", GeneralColumn.ColumnTypes.CharType, 200, false);
  public final GeneralColumn JourneyTitle = new GeneralColumn(TABLE_NAME,"journeyTitle", GeneralColumn.ColumnTypes.CharType, 200, false);

  public JourneyContract(DbHelpers dbHelpers) {
    super(dbHelpers);
    initContract(TABLE_NAME, new GeneralColumn[]{JourneyId, JourneyTitle});
  }

  @Override
  public void insertRecord(JourneyModel record) {
    String insertQuery = getContract().insertRecord(new String[]{record.id.get().toString(), record.name.get()});
    getDbHelpers().write(insertQuery);
  }

  @Override
  public void updateRecord(JourneyModel record) {
    String updateQuery = getContract().updateRecord(record.id, new String[]{record.id.get().toString(), record.name.get()});
    getDbHelpers().write(updateQuery);
  }

  @Override
  public void deleteRecord(DataID recordId) {
    String deleteQuery = getContract().deleteRecord(recordId);
    getDbHelpers().write(deleteQuery);
  }

  @Override
  public Cursor list(int offset, int limit) {
    return getDbHelpers().read(getContract().selectRecords(offset, limit, getContract().getColumnsTitles(), null));
  }
}
