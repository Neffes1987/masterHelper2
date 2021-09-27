package com.masterhelper.screens.plotTwist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.global.db.repository.AbstractRepository;
import com.masterhelper.global.db.repository.ContractColumn;

import java.util.ArrayList;
import java.util.UUID;

public class PlotTwistRepository extends AbstractRepository<PlotTwistModel> {
  public static final String TABLE_NAME = "plot";

  public static ContractColumn id = new ContractColumn(TABLE_NAME);
  public static ContractColumn title = new ContractColumn(TABLE_NAME, "title", ContractColumn.ColumnType.CharType, AbstractModel.TITLE_MAX_LENGTH);
  public static ContractColumn description = new ContractColumn(TABLE_NAME, "description", ContractColumn.ColumnType.CharType, AbstractModel.DESCRIPTION_MAX_LENGTH);
  public static ContractColumn deadLine = new ContractColumn(TABLE_NAME, "deadline", ContractColumn.ColumnType.Integer);

  public PlotTwistRepository(SQLiteDatabase db) {
    super(db, TABLE_NAME, new ContractColumn[]{
      id, title, description, deadLine
    });
  }

  @Override
  public String[] extractFields(PlotTwistModel model) {
    return new String[]{
      model.getId(),
      model.getTitle(),
      model.getDescription(),
      model.getDeadLine() + ""
    };
  }

  @Override
  public PlotTwistModel get(String id) {
    return new PlotTwistModel(getRecord(id));
  }

  @Override
  public ArrayList<PlotTwistModel> list(int offset, int limit, String ordering, String where) {
    ArrayList<PlotTwistModel> models = new ArrayList<>();

    Cursor cursor = getRecords(0, 0, title.getColumnTitle() + " DESC", where);

    while (cursor.moveToNext()) {
      models.add(new PlotTwistModel(cursor));
    }

    return models;
  }

  @Override
  public void create(PlotTwistModel newModel) {
    super.create(newModel);
  }


  @Override
  public PlotTwistModel draft() {
    return new PlotTwistModel(UUID.randomUUID().toString());
  }
}
