package com.masterhelper.screens.journey;

import android.database.Cursor;
import com.masterhelper.global.db.repository.AbstractModel;

public class JourneyModel extends AbstractModel {
  static final Integer RESTRICTION_MAX_LENGTH = 2048;

  private String restrictions;
  private Integer sessionNumber = 0;
  private Integer sessionAct = 0;

  public JourneyModel(Cursor cursor) {
    super(cursor.getString(cursor.getColumnIndex(JourneyRepository.id.getColumnTitle())));

    int nameIndex = cursor.getColumnIndex(JourneyRepository.title.getColumnTitle());
    int descriptionIndex = cursor.getColumnIndex(JourneyRepository.description.getColumnTitle());
    int restrictionsIndex = cursor.getColumnIndex(JourneyRepository.restrictions.getColumnTitle());
    int sessionNumberIndex = cursor.getColumnIndex(JourneyRepository.sessionNumber.getColumnTitle());
    int sessionActIndex = cursor.getColumnIndex(JourneyRepository.actNumber.getColumnTitle());

    setTitle(cursor.getString(nameIndex));
    setDescription(cursor.getString(descriptionIndex));
    setRestrictions(cursor.getString(restrictionsIndex));
    setSessionNumber(cursor.getInt(sessionNumberIndex));
    setSessionAct(cursor.getInt(sessionActIndex));
  }

  public JourneyModel(String id) {
    super(id);

    this.restrictions = "";
  }

  public void setSessionNumber(Integer currentSessionNumber) {
    this.sessionNumber = currentSessionNumber;
  }

  public Integer getSessionNumber() {
    return sessionNumber;
  }

  public String getRestrictions() {
    return restrictions;
  }

  public void setRestrictions(String restrictions) {
    this.restrictions = restrictions;
  }

  public Integer getSessionAct() {
    return sessionAct;
  }

  public void setSessionAct(int newActValue) {
    sessionAct = newActValue;
  }
}
