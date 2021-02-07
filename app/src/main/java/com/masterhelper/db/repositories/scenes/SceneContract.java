package com.masterhelper.db.repositories.scenes;

import android.database.Cursor;
import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.db.DbHelpers;
import com.masterhelper.db.repositories.common.contracts.AbstractContract;
import com.masterhelper.db.repositories.common.contracts.GeneralColumn;
import com.masterhelper.journeys.repository.JourneyContract;

public class SceneContract extends AbstractContract<SceneModel> {
  private final static String TABLE_NAME = "scenes";
  public final static int NAME_COLUMN_LENGTH = 200;
  public final static int DESCRIPTION_COLUMN_LENGTH = 200;
  public final static GeneralColumn id = new GeneralColumn(TABLE_NAME, "id", GeneralColumn.ColumnTypes.Primary, ID_COLUMN_LENGTH, false);
  public final static GeneralColumn externalId = new GeneralColumn(TABLE_NAME, "journeyId", GeneralColumn.ColumnTypes.CharType, ID_COLUMN_LENGTH, false);
  public final GeneralColumn title = new GeneralColumn(TABLE_NAME,"title", GeneralColumn.ColumnTypes.CharType, NAME_COLUMN_LENGTH, false);
  public final GeneralColumn description = new GeneralColumn(TABLE_NAME,"description", GeneralColumn.ColumnTypes.CharType, DESCRIPTION_COLUMN_LENGTH, false);
  public final GeneralColumn musicList = new GeneralColumn(TABLE_NAME,"musicList", GeneralColumn.ColumnTypes.TextTypes, 0, false);

  String journeyId;

  public SceneContract(DbHelpers dbHelpers) {
    super(dbHelpers);
    initContract(TABLE_NAME, new GeneralColumn[]{id, title, description, musicList});
    getContract().addDeleteForeignKeyColumn(JourneyContract.id, externalId);
  }

  public void setJourneyId(String journeyId) {
    this.journeyId = journeyId;
  }

  @Override
  public void insertRecord(SceneModel record) {
    String[] values = new String[]{record.id.get().toString(), record.name.get(), record.description.get(), record.musicList.get(), this.journeyId};
    String insertQuery = getContract().insertRecord(values);
    getDbHelpers().write(insertQuery);
  }

  @Override
  public void updateRecord(SceneModel record) {
    String[] values = new String[]{record.id.get().toString(), record.name.get(), record.description.get(), record.musicList.get(), this.journeyId };
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
    String where = externalId.getColumnTitle() + "='" + this.journeyId + "'";
    return getDbHelpers().read(getContract().selectRecords(offset, limit, getContract().getColumnsTitles(), id.getColumnTitle() + " ASC ", where));
  }

  @Override
  public Cursor getRecord(String recordId) {
    String where = id.getColumnTitle() + "='" + recordId + "'";
    return getDbHelpers().read(getContract().selectRecords(0, 0, getContract().getColumnsTitles(), id.getColumnTitle() + " DESC ", where));
  }
}
