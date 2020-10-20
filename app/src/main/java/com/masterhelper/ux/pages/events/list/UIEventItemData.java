package com.masterhelper.ux.pages.events.list;

import androidx.annotation.NonNull;

public class UIEventItemData {
  private String name;
  private String description;
  private EventType eventType;

  public UIEventItemData(String name, String description, @NonNull EventType eventType){
    setName(name);
    setDescription(description);
    setEventType(eventType);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public EventType getEventType() {
    return eventType;
  }

  public enum EventType {
    battle,
    meeting,
    accident
  }
}
