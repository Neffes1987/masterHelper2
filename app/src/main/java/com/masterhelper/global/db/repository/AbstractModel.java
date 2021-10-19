package com.masterhelper.global.db.repository;

import java.text.MessageFormat;

public abstract class AbstractModel {
  public static int TITLE_MAX_LENGTH = 2000;
  public static int DESCRIPTION_MAX_LENGTH = 4048;

  protected String id;
  protected String title;
  protected String description;

  public AbstractModel(String id) {
    this.title = "";
    this.description = "";

    if (id != null) {
      if (id.length() > 0) {
        this.id = id;
      }
    }
  }


  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String printToHtml(String template, String[] params) {
    return MessageFormat.format(template, (Object[]) params);
  }
}
