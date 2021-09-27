package com.masterhelper.screens.plotTwist.point;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.global.db.repository.AbstractRepository;
import com.masterhelper.global.db.repository.ContractColumn;

import java.util.ArrayList;
import java.util.UUID;

public class PointRepository extends AbstractRepository<PointModel> {
  public static String TABLE_NAME = "Points";
  public static ContractColumn id = new ContractColumn(TABLE_NAME);
  public static ContractColumn plotTwistId = new ContractColumn(TABLE_NAME, "plotTwistId", ContractColumn.ColumnType.Primary);
  public static ContractColumn title = new ContractColumn(TABLE_NAME, "title", ContractColumn.ColumnType.CharType, AbstractModel.TITLE_MAX_LENGTH);
  public static ContractColumn description = new ContractColumn(TABLE_NAME, "description", ContractColumn.ColumnType.CharType, AbstractModel.DESCRIPTION_MAX_LENGTH);
  public static ContractColumn status = new ContractColumn(TABLE_NAME, "status", ContractColumn.ColumnType.CharType, 200);

  public PointRepository(SQLiteDatabase db) {
    super(db, TABLE_NAME, new ContractColumn[]{id, plotTwistId, title, description, status});
  }

  @Override
  public PointModel get(String id) {
    return new PointModel(getRecord(id));
  }

  @Override
  public ArrayList<PointModel> list(int offset, int limit, String ordering, String where) {
    ArrayList<PointModel> pointModels = new ArrayList<>();

    Cursor cursor = getRecords(offset, limit, ordering, where);

    while (cursor.moveToNext()) {
      pointModels.add(new PointModel(cursor));
    }

    return pointModels;
  }

  public ArrayList<PointModel> list(String where) {
    return list(0, 0, title.getColumnTitle(), where);
  }

  @Override
  public PointModel draft() {
    return new PointModel(UUID.randomUUID().toString());
  }

  @Override
  public String[] extractFields(PointModel newModel) {
    return new String[]{
      newModel.getId(),
      newModel.getPlotTwistId(),
      newModel.getTitle(),
      newModel.getDescription(),
      newModel.statusToString()
    };
  }
}
