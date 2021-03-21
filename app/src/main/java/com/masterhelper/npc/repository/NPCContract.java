package com.masterhelper.npc.repository;

import android.database.Cursor;
import com.masterhelper.global.db.DbHelpers;
import com.masterhelper.global.db.repositories.common.contracts.AbstractContract;
import com.masterhelper.global.db.repositories.common.contracts.GeneralColumn;
import com.masterhelper.global.fields.DataID;
import com.masterhelper.media.repository.MediaContract;

public class NPCContract extends AbstractContract<NPCModel> {
  private final static String TABLE_NAME = "npc";
  public final static int NAME_COLUMN_LENGTH = 200;
  public final static int BACKGROUND_COLUMN_LENGTH = 2000;
  public final static int GOALS_COLUMN_LENGTH = 2000;
  public final static int CHARACTER_COLUMN_LENGTH = 1000;
  public final static int RELATIONS_COLUMN_LENGTH = 1000;
  public final static int PREVIEW_COLUMN_LENGTH = 2000;

  public final static GeneralColumn id = new GeneralColumn(TABLE_NAME, "id", GeneralColumn.ColumnTypes.Primary, ID_COLUMN_LENGTH, false);
  public final GeneralColumn title = new GeneralColumn(TABLE_NAME, "title", GeneralColumn.ColumnTypes.CharType, NAME_COLUMN_LENGTH, false);
  public final GeneralColumn age = new GeneralColumn(TABLE_NAME, "age", GeneralColumn.ColumnTypes.Integer, 0, false);
  public final GeneralColumn character = new GeneralColumn(TABLE_NAME, "character", GeneralColumn.ColumnTypes.CharType, CHARACTER_COLUMN_LENGTH, false);
  public final GeneralColumn relations = new GeneralColumn(TABLE_NAME, "relations", GeneralColumn.ColumnTypes.CharType, RELATIONS_COLUMN_LENGTH, false);
  public final GeneralColumn goals = new GeneralColumn(TABLE_NAME, "goals", GeneralColumn.ColumnTypes.CharType, GOALS_COLUMN_LENGTH, false);
  public final GeneralColumn background = new GeneralColumn(TABLE_NAME, "background", GeneralColumn.ColumnTypes.CharType, BACKGROUND_COLUMN_LENGTH, false);
  public final GeneralColumn previewUrlId = new GeneralColumn(TABLE_NAME, "preview", GeneralColumn.ColumnTypes.CharType, PREVIEW_COLUMN_LENGTH, true);
  public final GeneralColumn musicEffects = new GeneralColumn(TABLE_NAME, "musicEffects", GeneralColumn.ColumnTypes.TextTypes, 0, false);

  public NPCContract(DbHelpers dbHelpers) {
    super(dbHelpers);
    initContract(TABLE_NAME, new GeneralColumn[]{id, title, age, character, relations, goals, background, previewUrlId, musicEffects});
  }

  @Override
  public void insertRecord(NPCModel record) {
    String[] values = new String[]{
      record.id.toString(),
      record.name.get(),
      record.age.get().toString(),
      record.character.get(),
      record.relations.get(),
      record.goals.get(),
      record.background.get(),
      record.previewId.get(),
      record.musicEffects.get()
    };
    String insertQuery = getContract().insertRecord(values);
    getDbHelpers().write(insertQuery);
  }

  @Override
  public void updateRecord(NPCModel record) {
    String[] values = new String[]{
      record.id.toString(),
      record.name.get(),
      record.age.get(),
      record.character.get(),
      record.relations.get(),
      record.goals.get(),
      record.background.get(),
      record.previewId.get(),
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
    return null;
  }

  public Cursor list(String searchString) {
    String locationFields = TABLE_NAME + "." + id.getColumnTitle()
      + "," + TABLE_NAME + "." + title.getColumnTitle()
      + "," + TABLE_NAME + "." + age.getColumnTitle()
      + "," + TABLE_NAME + "." + character.getColumnTitle()
      + "," + TABLE_NAME + "." + relations.getColumnTitle()
      + "," + TABLE_NAME + "." + goals.getColumnTitle()
      + "," + TABLE_NAME + "." + previewUrlId.getColumnTitle()
      + "," + TABLE_NAME + "." + musicEffects.getColumnTitle()
      + "," + TABLE_NAME + "." + background.getColumnTitle();
    String previewFields = MediaContract.TABLE_NAME + "." + MediaContract.filePath.getColumnTitle() + " as previewUrl";

    String query = "SELECT " + locationFields + "," + previewFields
      + " FROM " + TABLE_NAME + " LEFT OUTER JOIN " + MediaContract.TABLE_NAME
      + " ON " + TABLE_NAME + "." + previewUrlId.getColumnTitle() + "=" + MediaContract.TABLE_NAME + "." + MediaContract.id.getColumnTitle();

    if (searchString != null && searchString.length() > 3) {
      query += " WHERE " + TABLE_NAME + "." + title.getColumnTitle() + " LIKE '%" + searchString + "%'";
    }
    query += " ORDER BY " + TABLE_NAME + "." + title.getColumnTitle() + " ASC ";
    return getDbHelpers().read(query);
  }

  @Override
  public Cursor getRecord(String recordId) {
    String locationFields = TABLE_NAME + "." + id.getColumnTitle()
      + "," + TABLE_NAME + "." + title.getColumnTitle()
      + "," + TABLE_NAME + "." + age.getColumnTitle()
      + "," + TABLE_NAME + "." + character.getColumnTitle()
      + "," + TABLE_NAME + "." + relations.getColumnTitle()
      + "," + TABLE_NAME + "." + goals.getColumnTitle()
      + "," + TABLE_NAME + "." + previewUrlId.getColumnTitle()
      + "," + TABLE_NAME + "." + musicEffects.getColumnTitle()
      + "," + TABLE_NAME + "." + background.getColumnTitle();
    String previewFields = MediaContract.TABLE_NAME + "." + MediaContract.filePath.getColumnTitle() + " as previewUrl";

    String query = "SELECT " + locationFields + "," + previewFields
      + " FROM " + TABLE_NAME + " LEFT OUTER JOIN " + MediaContract.TABLE_NAME
      + " ON " + TABLE_NAME + "." + previewUrlId.getColumnTitle() + "=" + MediaContract.TABLE_NAME + "." + MediaContract.id.getColumnTitle()
      + " WHERE " + TABLE_NAME + "." + id.getColumnTitle() + "='" + recordId + "'";
    return getDbHelpers().read(query);
  }
}
