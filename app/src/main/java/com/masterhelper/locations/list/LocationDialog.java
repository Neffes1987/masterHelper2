package com.masterhelper.locations.list;

import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.locations.LocationLocale;

import static com.masterhelper.locations.LocationLocale.getLocalizationByKey;

public class LocationDialog {
  public ComponentUIDialog dialog;

  public LocationDialog(AppCompatActivity context, int nameMaxLength){
    dialog = initDialog(context, nameMaxLength);
  }

  public ComponentUIDialog initDialog(AppCompatActivity context, int nameMaxLength){
    ComponentUIDialog dialog = new ComponentUIDialog(context);
    dialog.setTitle(getLocalizationByKey(LocationLocale.Keys.createLocation));
    dialog.pNameLabel.show();
    dialog.pNameLabel.setText(getLocalizationByKey(LocationLocale.Keys.eventName));

    dialog.pNameField.setText("");
    dialog.pNameField.setMaxLength(nameMaxLength);
    dialog.pNameField.show();
    return dialog;
  }


  public void initUpdateState(String name, String description){
    dialog.setTitle(LocationLocale.getLocalizationByKey(LocationLocale.Keys.updateLocation));
    dialog.pDescriptionField.setText(description);
    dialog.pNameField.setText(name);
  }

  public void initCreateState(){
    dialog.setTitle(getLocalizationByKey(LocationLocale.Keys.createLocation));
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
