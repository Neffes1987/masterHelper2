package com.masterhelper.ux.list.propertyBar;

import androidx.appcompat.widget.PopupMenu;

public class PropertyBarContentModel {
  private PopupMenu contextMenu;
  private String title;
  private int label;
  private String id;
  private String description;
  private PropertyBar.CardStatus status;

  public PropertyBarContentModel(String id, String title) {
    initDefaults(id, title, 0, "", null, null);
  }

  public PropertyBarContentModel(String id, String title, String description) {
    initDefaults(id, title, 0, description, null, null);
  }

  public PropertyBarContentModel(String id, String title, String description, PopupMenu menu) {
    initDefaults(id, title, 0, description, null, menu);
  }

  public PropertyBarContentModel(String id, String title, String description, PopupMenu menu, int label) {
    initDefaults(id, title, label, description, null, menu);
  }

  public PropertyBarContentModel(String id, String title, String description, PopupMenu menu, int label, PropertyBar.CardStatus status) {
    initDefaults(id, title, label, description, status, menu);
  }

  private void initDefaults(String id, String title, int label, String description, PropertyBar.CardStatus status, PopupMenu menu) {
    this.id = id;
    this.title = title;
    this.label = label;
    this.description = description;
    this.status = status == null ? PropertyBar.CardStatus.Active : status;
    this.contextMenu = menu;
  }

  ;

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

  public PopupMenu getContextMenu() {
    return contextMenu;
  }

  public String getId() {
    return id;
  }
}