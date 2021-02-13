package com.masterhelper.media.repository;

import android.database.Cursor;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.contracts.AbstractContract;
import com.masterhelper.global.db.repositories.common.contracts.GeneralColumn;

public class MediaContract extends AbstractContract<MediaModel> {
  public final static String TABLE_NAME = "media";
  public final static int NAME_COLUMN_LENGTH = 200;
  public final static int TYPE_COLUMN_LENGTH = 100;
  public final static GeneralColumn id = new GeneralColumn(TABLE_NAME, "id", GeneralColumn.ColumnTypes.Primary, ID_COLUMN_LENGTH, false);
  public final static GeneralColumn filePath = new GeneralColumn(TABLE_NAME,"filePath", GeneralColumn.ColumnTypes.CharType, NAME_COLUMN_LENGTH, false);
  public final GeneralColumn fileName = new GeneralColumn(TABLE_NAME,"fileName", GeneralColumn.ColumnTypes.TextTypes, 0, false);
  public final GeneralColumn fileType = new GeneralColumn(TABLE_NAME,"fileType", GeneralColumn.ColumnTypes.CharType, TYPE_COLUMN_LENGTH, false);

  public MediaContract(DbHelpers dbHelpers) {
    super(dbHelpers);
    initContract(TABLE_NAME, new GeneralColumn[]{id, filePath, fileName, fileType});
  }

  @Override
  public void insertRecord(MediaModel record) {
    String[] values = new String[]{
      record.id.toString(),
      record.filePath.get(),
      record.fileName.get(),
      record.fileType.get().name(),
    };
    String insertQuery = getContract().insertRecord(values);
    getDbHelpers().write(insertQuery);
  }

  @Override
  public void updateRecord(MediaModel record) {
    String[] values = new String[]{
      record.id.toString(),
      record.filePath.get(),
      record.fileName.get(),
      record.fileType.get().name(),
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
    return getDbHelpers().read(getContract().selectRecords(offset, limit, getContract().getColumnsTitles(), id.getColumnTitle() + " ASC ", null));
  }

  public Cursor listByType(String type) {
    String where = fileType.getColumnTitle() + "='" + type + "'";
    return getDbHelpers().read(getContract().selectRecords(0, 0, getContract().getColumnsTitles(), id.getColumnTitle() + " ASC ", where));
  }

  @Override
  public Cursor getRecord(String recordId) {
    String where = id.getColumnTitle() + "='" + recordId + "'";
    return getDbHelpers().read(getContract().selectRecords(0, 0, getContract().getColumnsTitles(), id.getColumnTitle() + " DESC ", where));
  }
}
