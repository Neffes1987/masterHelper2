package com.masterhelper.locations.list;

import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.locations.repository.LocationModel;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.locations.LocationLocale;

import java.util.ArrayList;
import java.util.HashMap;

import static com.masterhelper.locations.LocationLocale.getLocalizationByKey;

public class LocationDialog {
  public ComponentUIDialog dialog;

  HashMap<LocationModel.EventType, String> eventsTypesList = new HashMap<>();

  public LocationDialog(AppCompatActivity context, int nameMaxLength, int descriptionMaxLength){
    eventsTypesList.put(LocationModel.EventType.battle, getLocalizationByKey(LocationLocale.Keys.eventBattle));
    eventsTypesList.put(LocationModel.EventType.accident, getLocalizationByKey(LocationLocale.Keys.eventAccident));
    eventsTypesList.put(LocationModel.EventType.meeting, getLocalizationByKey(LocationLocale.Keys.eventMeeting));
    dialog = initDialog(context, nameMaxLength, descriptionMaxLength);
  }

  public ComponentUIDialog initDialog(AppCompatActivity context, int nameMaxLength, int descriptionMaxLength){
    ComponentUIDialog dialog = new ComponentUIDialog(context);
    dialog.setTitle(getLocalizationByKey(LocationLocale.Keys.createLocation));
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

  public LocationModel.EventType getSelectedType(){
    int selectedIndex = dialog.pRadioGroup.getSelectedItemIndex();
    ArrayList<LocationModel.EventType> eventTypesKeys = new ArrayList<>(eventsTypesList.keySet());
    return eventTypesKeys.get(selectedIndex);
  }

  public int getIndexBySelectedType(LocationModel.EventType type){
    ArrayList<LocationModel.EventType> eventTypesKeys = new ArrayList<>(eventsTypesList.keySet());
    return eventTypesKeys.indexOf(type);
  }

  public void initUpdateState(String name, String description, LocationModel.EventType type){
    dialog.setTitle(LocationLocale.getLocalizationByKey(LocationLocale.Keys.updateLocation));
    dialog.pDescriptionField.setText(description);
    dialog.pNameField.setText(name);
    dialog.pRadioGroup.setSelectedItem(getIndexBySelectedType(type));
  }

  public void initCreateState(){
    dialog.setTitle(getLocalizationByKey(LocationLocale.Keys.createLocation));
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
