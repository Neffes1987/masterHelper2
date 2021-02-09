package com.masterhelper.locations.list;

import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.db.repositories.events.EventModel;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.locations.LocationLocale;

import java.util.ArrayList;
import java.util.HashMap;

import static com.masterhelper.locations.LocationLocale.getLocalizationByKey;

public class EventDialog {
  public ComponentUIDialog dialog;

  HashMap<EventModel.EventType, String> eventsTypesList = new HashMap<>();

  public EventDialog(AppCompatActivity context, int nameMaxLength, int descriptionMaxLength){
    eventsTypesList.put(EventModel.EventType.battle, getLocalizationByKey(LocationLocale.Keys.eventBattle));
    eventsTypesList.put(EventModel.EventType.accident, getLocalizationByKey(LocationLocale.Keys.eventAccident));
    eventsTypesList.put(EventModel.EventType.meeting, getLocalizationByKey(LocationLocale.Keys.eventMeeting));
    dialog = initDialog(context, nameMaxLength, descriptionMaxLength);
  }

  public ComponentUIDialog initDialog(AppCompatActivity context, int nameMaxLength, int descriptionMaxLength){
    ComponentUIDialog dialog = new ComponentUIDialog(context);
    dialog.setTitle(getLocalizationByKey(LocationLocale.Keys.createEvent));
    dialog.pNameLabel.show();
    dialog.pNameLabel.setText(getLocalizationByKey(LocationLocale.Keys.eventName));

    dialog.pNameField.setText("");
    dialog.pNameField.setMaxLength(nameMaxLength);
    dialog.pNameField.show();

    dialog.pDescriptionLabel.show();
    dialog.pDescriptionLabel.setText(getLocalizationByKey(LocationLocale.Keys.shortDescription));

    dialog.pDescriptionField.setText("");
    dialog.pDescriptionField.setMaxLength(descriptionMaxLength);
    dialog.pDescriptionField.show();

    dialog.pRadioGroup.setList(new ArrayList<>(eventsTypesList.values()));
    dialog.pRadioGroup.show();
    return dialog;
  }

  public EventModel.EventType getSelectedType(){
    int selectedIndex = dialog.pRadioGroup.getSelectedItemIndex();
    ArrayList<EventModel.EventType> eventTypesKeys = new ArrayList<>(eventsTypesList.keySet());
    return eventTypesKeys.get(selectedIndex);
  }

  public int getIndexBySelectedType(EventModel.EventType type){
    ArrayList<EventModel.EventType> eventTypesKeys = new ArrayList<>(eventsTypesList.keySet());
    return eventTypesKeys.indexOf(type);
  }

  public void initUpdateState(String name, String description, EventModel.EventType type){
    dialog.setTitle(LocationLocale.getLocalizationByKey(LocationLocale.Keys.updateEvent));
    dialog.pDescriptionField.setText(description);
    dialog.pNameField.setText(name);
    dialog.pRadioGroup.setSelectedItem(getIndexBySelectedType(type));
  }

  public void initCreateState(){
    dialog.setTitle(getLocalizationByKey(LocationLocale.Keys.createEvent));
    dialog.pRadioGroup.setSelectedItem(0);
  }

  public String getName(){
    return dialog.pNameField.getText();
  }

  public String getDescription(){
    return dialog.pDescriptionField.getText();
  }

  public void show(){
    dialog.show();
  }

}
