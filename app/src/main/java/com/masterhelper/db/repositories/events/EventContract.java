package com.masterhelper.db.repositories.events;

import android.database.Cursor;
import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.db.DbHelpers;
import com.masterhelper.db.repositories.common.contracts.AbstractContract;
import com.masterhelper.db.repositories.common.contracts.GeneralColumn;
import com.masterhelper.db.repositories.scenes.SceneContract;

public class EventContract extends AbstractContract<EventModel> {
  private final static String TABLE_NAME = "events";
  public final static int NAME_COLUMN_LENGTH = 200;
  public final static int DESCRIPTION_COLUMN_LENGTH = 200;
  public final static int TYPE_COLUMN_LENGTH = 40;
  public final static int PREVIEW_COLUMN_LENGTH = 2000;
  public final static GeneralColumn id = new GeneralColumn(TABLE_NAME, "id", GeneralColumn.ColumnTypes.Primary, ID_COLUMN_LENGTH, false);
  public final static GeneralColumn externalId = new GeneralColumn(TABLE_NAME, "sceneId", GeneralColumn.ColumnTypes.CharType, ID_COLUMN_LENGTH, false);
  public final GeneralColumn title = new GeneralColumn(TABLE_NAME,"title", GeneralColumn.ColumnTypes.CharType, NAME_COLUMN_LENGTH, false);
  public final GeneralColumn description = new GeneralColumn(TABLE_NAME,"description", GeneralColumn.ColumnTypes.CharType, DESCRIPTION_COLUMN_LENGTH, false);
  public final GeneralColumn type = new GeneralColumn(TABLE_NAME,"type", GeneralColumn.ColumnTypes.CharType, TYPE_COLUMN_LENGTH, false);
  public final GeneralColumn previewUrlId = new GeneralColumn(TABLE_NAME,"preview", GeneralColumn.ColumnTypes.CharType, PREVIEW_COLUMN_LENGTH, true);
  public final GeneralColumn musicList = new GeneralColumn(TABLE_NAME,"musicList", GeneralColumn.ColumnTypes.TextTypes, 0, false);
  String sceneId;

  public EventContract(DbHelpers dbHelpers) {
    super(dbHelpers);
    initContract(TABLE_NAME, new GeneralColumn[]{id, title, description, type, previewUrlId, musicList});
    getContract().addDeleteForeignKeyColumn(SceneContract.id, externalId);
  }

  public void setSceneId(String sceneId) {
    this.sceneId = sceneId;
  }

  @Override
  public void insertRecord(EventModel record) {
    String[] values = new String[]{
      record.id.toString(),
      record.name.get(),
      record.description.get(),
      record.type.get().name(),
      record.previewId.get(),
      record.musicList.get(),
      this.sceneId
    };
    String insertQuery = getContract().insertRecord(values);
    getDbHelpers().write(insertQuery);
  }

  @Override
  public void updateRecord(EventModel record) {
    String[] values = new String[]{
      record.id.toString(),
      record.name.get(),
      record.description.get(),
      record.type.get().name(),
      record.previewId.get(),
      record.musicList.get(),
      this.sceneId
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
    String where = externalId.getColumnTitle() + "='" + this.sceneId + "'";
    return getDbHelpers().read(getContract().selectRecords(offset, limit, getContract().getColumnsTitles(), id.getColumnTitle() + " ASC ", where));
  }

  @Override
  public Cursor getRecord(String recordId) {
    String where = id.getColumnTitle() + "='" + recordId + "'";
    return getDbHelpers().read(getContract().selectRecords(0, 0, getContract().getColumnsTitles(), id.getColumnTitle() + " DESC ", where));
  }
}
