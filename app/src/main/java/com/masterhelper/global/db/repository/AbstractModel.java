package com.masterhelper.global.db.repository;

public abstract class AbstractModel {
  public String id;
  public String name;
  public String description;

  public AbstractModel(String id, String name) {
    init(id, name, "");
  }

  public AbstractModel(String id, String name, String description) {
    init(id, name, description);
  }

  private void init(String id, String name, String description) {
    this.name = name;
    this.description = description;

    if (id != null) {
      if (id.length() > 0) {
        this.id = id;
      }
    }
  }
}
