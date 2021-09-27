package com.masterhelper.screens.journey;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.global.db.repository.AbstractRepository;
import com.masterhelper.global.db.repository.ContractColumn;

import java.util.ArrayList;
import java.util.UUID;

public class JourneyRepository extends AbstractRepository<JourneyModel> {
  static String TABLE_NAME = "journey";

  public static ContractColumn id = new ContractColumn(TABLE_NAME);
  static ContractColumn title = new ContractColumn(TABLE_NAME, "title", ContractColumn.ColumnType.CharType, AbstractModel.TITLE_MAX_LENGTH);
  static ContractColumn description = new ContractColumn(TABLE_NAME, "description", ContractColumn.ColumnType.CharType, AbstractModel.DESCRIPTION_MAX_LENGTH);
  static ContractColumn restrictions = new ContractColumn(TABLE_NAME, "restrictions", ContractColumn.ColumnType.CharType, JourneyModel.RESTRICTION_MAX_LENGTH);
  static ContractColumn sessionNumber = new ContractColumn(TABLE_NAME, "reference", ContractColumn.ColumnType.Integer);
  static ContractColumn actNumber = new ContractColumn(TABLE_NAME, "act", ContractColumn.ColumnType.Integer);

  public JourneyRepository(SQLiteDatabase db) {
    super(db, TABLE_NAME, new ContractColumn[]{id, title, description, restrictions, sessionNumber, actNumber});
  }

  @Override
  public String[] extractFields(JourneyModel model) {
    return new String[]{
      model.getId(),
      model.getTitle(),
      model.getDescription(),
      model.getRestrictions(),
      model.getSessionNumber().toString(),
      model.getSessionAct().toString()
    };
  }

  @Override
  public JourneyModel get(String id) {
    JourneyModel model;
    try {
      model = new JourneyModel(getRecord(id));
    } catch (Exception e){
      return  null;
    }
    return model;
  }

  public ArrayList<JourneyModel> list(String where) {
    ArrayList<JourneyModel> journeys = new ArrayList<>();

    Cursor cursor = getRecords(0, 0, title.getColumnTitle() + " DESC", where);

    while (cursor.moveToNext()) {
      journeys.add(new JourneyModel(cursor));
    }

    return journeys;
  }

  @Override
  public ArrayList<JourneyModel> list(int offset, int limit, String ordering, String where) {
    ArrayList<JourneyModel> journeys = new ArrayList<>();

    Cursor cursor = getRecords(offset, limit, title.getColumnTitle() + " DESC", where);

    while (cursor.moveToNext()) {
      journeys.add(new JourneyModel(cursor));
    }

    return journeys;
  }

  @Override
  public JourneyModel draft() {
    return new JourneyModel(UUID.randomUUID().toString());
  }
}
