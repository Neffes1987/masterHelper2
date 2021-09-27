package com.masterhelper.screens.plotTwist;

import android.database.Cursor;
import com.masterhelper.global.db.repository.AbstractModel;

public class PlotTwistModel extends AbstractModel {
  private int deadLine = 2;

  public PlotTwistModel(String id) {
    super(id);
  }

  public PlotTwistModel(Cursor cursor){
    super(cursor.getString(cursor.getColumnIndex(PlotTwistRepository.id.getColumnTitle())));

    int nameIndex = cursor.getColumnIndex(PlotTwistRepository.title.getColumnTitle());
    int descriptionIndex = cursor.getColumnIndex(PlotTwistRepository.description.getColumnTitle());
    int deadLineIndex = cursor.getColumnIndex(PlotTwistRepository.deadLine.getColumnTitle());

    setTitle(cursor.getString(nameIndex));
    setDescription(cursor.getString(descriptionIndex));
    setDeadLine(cursor.getInt(deadLineIndex));
  }

  public int getDeadLine() {
    return deadLine;
  }

  public void setDeadLine(int deadLine) {
    this.deadLine = deadLine;
  }
}
