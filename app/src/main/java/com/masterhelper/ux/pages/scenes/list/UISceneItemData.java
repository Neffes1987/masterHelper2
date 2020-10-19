package com.masterhelper.ux.pages.scenes.list;

public class UISceneItemData {
  String text;
  String description;
  int finishedEvents = 0;
  int totalEvents = 0;

  public UISceneItemData(String text, String description, int finishedEvents, int totalEvents){
    setText(text);
    setDescription(description);
    setFinishedEvents(finishedEvents);
    setTotalEvents(totalEvents);
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setFinishedEvents(int finishedEvents) {
    this.finishedEvents = finishedEvents;
  }

  public int getFinishedEvents() {
    return finishedEvents;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
  
  public int getSceneProgress(){
    int totalEvents = getTotalEvents();
    int finishedEvents = getFinishedEvents();
    if(finishedEvents == 0 || totalEvents == 0){
      return 0;
    }
    return (100*finishedEvents) / totalEvents;
  }

  public void setTotalEvents(int totalEvents) {
    this.totalEvents = totalEvents;
  }

  public int getTotalEvents() {
    return totalEvents;
  }
}
