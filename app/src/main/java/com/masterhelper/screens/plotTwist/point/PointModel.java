package com.masterhelper.screens.plotTwist.point;

import android.database.Cursor;
import com.masterhelper.global.db.repository.AbstractModel;

public class PointModel extends AbstractModel {
  private PointStatus status = PointStatus.Hold;
  private String plotTwistId;

  public PointModel(String id) {
    super(id);
    this.plotTwistId = null;
  }

  public PointModel(Cursor cursor) {
    super(cursor.getString(cursor.getColumnIndex(PointRepository.id.getColumnTitle())));

    int nameIndex = cursor.getColumnIndex(PointRepository.title.getColumnTitle());
    int descriptionIndex = cursor.getColumnIndex(PointRepository.description.getColumnTitle());
    int plotTwistIdIndex = cursor.getColumnIndex(PointRepository.plotTwistId.getColumnTitle());
    int statusIndex = cursor.getColumnIndex(PointRepository.status.getColumnTitle());

    setTitle(cursor.getString(nameIndex));
    setDescription(cursor.getString(descriptionIndex));
    setStatus(cursor.getString(statusIndex));
    setPlotTwistId(cursor.getString(plotTwistIdIndex));
  }

  public PointStatus getStatus() {
    return status;
  }

  public String statusToString() {
    return status.name();
  }

  public void setStatus(PointStatus status) {
    this.status = status;
  }

  public void setStatus(String status) {
    this.status = PointStatus.valueOf(status);
  }

  public String getPlotTwistId() {
    return plotTwistId;
  }

  public void setPlotTwistId(String plotTwistId) {
    this.plotTwistId = plotTwistId;
  }

  public enum PointStatus {
    Hold,
    Started,
    Succeed,
    Failure
  }
}
