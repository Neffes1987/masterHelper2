package com.masterhelper.plotLine.repository;

import com.masterhelper.global.db.repositories.common.model.GeneralModel;
import com.masterhelper.global.fields.DataID;

import java.util.ArrayList;

public class PlotLineModel extends GeneralModel<PlotLineRepository> {
  DataID actIPlotLinePointId;

  DataID actIIPlotLinePointId;

  DataID actIIIPlotLinePointId;

  DataID actIVPlotLinePointId;

  DataID actVPlotLinePointId;

  DataID plotLineProgressId;

  String currentPlotPointName;

  public PlotLineModel(
    PlotLineRepository repository,
    String defaultId,
    String defaultFileName
  ) {
    super(repository, defaultId, defaultFileName);
    this.plotLineProgressId = new DataID();
    this.currentPlotPointName = "";
    this.actIPlotLinePointId = new DataID();
    this.actIIPlotLinePointId = new DataID();
    this.actIIIPlotLinePointId = new DataID();
    this.actIVPlotLinePointId = new DataID();
    this.actVPlotLinePointId = new DataID();
  }

  public void setActIPlotPoint(String plotPoint) {
    this.actIPlotLinePointId.fromString(plotPoint);
  }

  public String getActIPlotLinePointId() {
    return actIPlotLinePointId != null ? actIPlotLinePointId.toString() : "";
  }

  public void setActIIPlotPoint(String plotPoint) {
    this.actIIPlotLinePointId.fromString(plotPoint);
  }

  public String getActIIPlotLinePointId() {
    return actIIPlotLinePointId != null ? actIIPlotLinePointId.toString() : "";
  }

  public void setActIIIPlotPoint(String plotPoint) {
    this.actIIIPlotLinePointId.fromString(plotPoint);
  }

  public String getActIIIPlotLinePointId() {
    return actIIIPlotLinePointId != null ? actIIIPlotLinePointId.toString() : "";
  }

  public void setActIVPlotPoint(String plotPoint) {
    this.actIVPlotLinePointId.fromString(plotPoint);
  }


  public String getActIVPlotLinePointId() {
    return actIVPlotLinePointId != null ? actIVPlotLinePointId.toString() : "";
  }

  public void setActVPlotPoint(String plotPoint) {
    this.actVPlotLinePointId.fromString(plotPoint);
  }


  public String getActVPlotLinePointId() {
    return actVPlotLinePointId != null ? actVPlotLinePointId.toString() : "";
  }

  public void setPlotLineProgress(String plotLineProgress) {
    this.plotLineProgressId.fromString(plotLineProgress);
  }

  public String getPlotLineProgress() {
    return plotLineProgressId.toString();
  }

  public String getPlotPoints() {
    StringBuilder plotPoints = new StringBuilder();
    if (actIPlotLinePointId != null && actIPlotLinePointId.get() != null) {
      plotPoints.append("'").append(actIPlotLinePointId.toString()).append("'");
    }

    if (actIIPlotLinePointId != null && actIIPlotLinePointId.get() != null) {
      plotPoints.append(", ");
      plotPoints.append("'").append(actIIPlotLinePointId.toString()).append("'");
    }

    if (actIIIPlotLinePointId != null && actIIIPlotLinePointId.get() != null) {
      plotPoints.append(", ");
      plotPoints.append("'").append(actIIIPlotLinePointId.toString()).append("'");
    }

    if (actIVPlotLinePointId != null && actIVPlotLinePointId.get() != null) {
      plotPoints.append(", ");
      plotPoints.append("'").append(actIVPlotLinePointId.toString()).append("'");
    }

    if (actVPlotLinePointId != null && actVPlotLinePointId.get() != null) {
      plotPoints.append(", ");
      plotPoints.append("'").append(actVPlotLinePointId.toString()).append("'");
    }

    return plotPoints.toString();
  }

  public void setCurrentPlotPointName(String currentPlotPointName) {
    this.currentPlotPointName = currentPlotPointName;
  }

  public String getCurrentPlotPointName() {
    return currentPlotPointName;
  }
}
