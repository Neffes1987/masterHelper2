package com.masterhelper.locations.repository;

import android.database.Cursor;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.contracts.AbstractContract;
import com.masterhelper.global.db.repositories.common.contracts.GeneralColumn;
import com.masterhelper.media.repository.MediaContract;

public class LocationContract extends AbstractContract<LocationModel> {
  private final static String TABLE_NAME = "locations";
  public final static int NAME_COLUMN_LENGTH = 200;
  public final static int DESCRIPTION_COLUMN_LENGTH = 200;
  public final static int PREVIEW_COLUMN_LENGTH = 2000;
  public final static GeneralColumn id = new GeneralColumn(TABLE_NAME, "id", GeneralColumn.ColumnTypes.Primary, ID_COLUMN_LENGTH, false);
  public final GeneralColumn title = new GeneralColumn(TABLE_NAME,"title", GeneralColumn.ColumnTypes.CharType, NAME_COLUMN_LENGTH, false);
  public final GeneralColumn description = new GeneralColumn(TABLE_NAME,"description", GeneralColumn.ColumnTypes.CharType, DESCRIPTION_COLUMN_LENGTH, false);
  public final GeneralColumn previewUrlId = new GeneralColumn(TABLE_NAME,"preview", GeneralColumn.ColumnTypes.CharType, PREVIEW_COLUMN_LENGTH, true);
  public final GeneralColumn musicList = new GeneralColumn(TABLE_NAME, "musicList", GeneralColumn.ColumnTypes.TextTypes, 0, false);
  public final GeneralColumn musicEffects = new GeneralColumn(TABLE_NAME, "musicEffects", GeneralColumn.ColumnTypes.TextTypes, 0, false);

  public LocationContract(DbHelpers dbHelpers) {
    super(dbHelpers);
    initContract(TABLE_NAME, new GeneralColumn[]{id, title, description, previewUrlId, musicList, musicEffects});
  }

  @Override
  public void insertRecord(LocationModel record) {
    String[] values = new String[]{
      record.id.toString(),
      record.name.get(),
      record.description.get(),
      record.previewId.get(),
      record.musicList.get(),
      record.musicEffects.get()
    };
    String insertQuery = getContract().insertRecord(values);
    getDbHelpers().write(insertQuery);
  }

  @Override
  public void updateRecord(LocationModel record) {
    String[] values = new String[]{
      record.id.toString(),
      record.name.get(),
      record.description.get(),
      record.previewId.get(),
      record.musicList.get(),
      record.musicEffects.get()
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
      + "," + TABLE_NAME + "." + title.getColumnTitle()
      + "," + TABLE_NAME + "." + previewUrlId.getColumnTitle()
      + "," + TABLE_NAME + "." + musicList.getColumnTitle()
      + "," + TABLE_NAME + "." + description.getColumnTitle();
    String previewFields = MediaContract.TABLE_NAME + "." + MediaContract.filePath.getColumnTitle() + " as previewUrl";

    String query = "SELECT " + locationFields + "," + previewFields
      + " FROM " + TABLE_NAME + " LEFT OUTER JOIN " + MediaContract.TABLE_NAME
      + " ON " + TABLE_NAME + "." + previewUrlId.getColumnTitle() + "=" + MediaContract.TABLE_NAME + "." + MediaContract.id.getColumnTitle();
    return getDbHelpers().read(query);
  }

  @Override
  public Cursor getRecord(String recordId) {
    String locationFields = TABLE_NAME + "." + id.getColumnTitle()
      + "," + TABLE_NAME + "." + title.getColumnTitle()
      + "," + TABLE_NAME + "." + previewUrlId.getColumnTitle()
      + "," + TABLE_NAME + "." + musicList.getColumnTitle()
      + "," + TABLE_NAME + "." + musicEffects.getColumnTitle()
      + "," + TABLE_NAME + "." + description.getColumnTitle();
    String previewFields = MediaContract.TABLE_NAME + "." + MediaContract.filePath.getColumnTitle() + " as previewUrl";

    String query = "SELECT " + locationFields + "," + previewFields
      + " FROM " + TABLE_NAME + " LEFT OUTER JOIN " + MediaContract.TABLE_NAME
      + " ON " + TABLE_NAME + "." + previewUrlId.getColumnTitle() + "=" + MediaContract.TABLE_NAME + "." + MediaContract.id.getColumnTitle()
      + " WHERE " + TABLE_NAME + "." + id.getColumnTitle() + "='" + recordId + "'";
    return getDbHelpers().read(query);
  }
}
