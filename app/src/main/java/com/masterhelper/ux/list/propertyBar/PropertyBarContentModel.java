package com.masterhelper.ux.list.propertyBar;

import com.masterhelper.ux.ContextPopupMenuBuilder;

public class PropertyBarContentModel {
  private String title;
  private int label;
  private String id;
  private String description;
  private PropertyBar.CardStatus status;

  public PropertyBarContentModel(String id, String title) {
    initDefaults(id, title, 0, "", null);
  }

  public PropertyBarContentModel(String id, String title, String description) {
    initDefaults(id, title, 0, description, null);
  }

  public PropertyBarContentModel(String id, String title, String description, ContextPopupMenuBuilder menu, int label) {
    initDefaults(id, title, label, description, null);
  }

  public PropertyBarContentModel(String id, String title, String description, ContextPopupMenuBuilder menu, int label, PropertyBar.CardStatus status) {
    initDefaults(id, title, label, description, status);
  }

  private void initDefaults(String id, String title, int label, String description, PropertyBar.CardStatus status) {
    this.id = id;
    this.title = title;
    this.label = label;
    this.description = description;
    this.status = status == null ? PropertyBar.CardStatus.Active : status;
  }

  public PropertyBar.CardStatus getStatus() {
    return status;
  }

  public int getLabel() {
    return label;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getId() {
    return id;
  }
}