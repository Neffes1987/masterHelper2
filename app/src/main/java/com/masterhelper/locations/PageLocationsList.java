package com.masterhelper.locations;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.locations.repository.LocationModel;
import com.masterhelper.locations.repository.LocationRepository;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.buttons.floating.FloatingButtonsPreset;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.locations.list.LocationDialog;
import com.masterhelper.locations.list.ListItemLocation;

import static com.masterhelper.goals.PageGoal.INTENT_GOAL_ID;
import static com.masterhelper.locations.LocationLocale.getLocalizationByKey;


public class PageLocationsList extends AppCompatActivity implements SetBtnLocation, com.masterhelper.ux.components.library.list.ListItemLocation {
  public static final String INTENT_LOCATION_ID = "locationId";
  public static final String INTENT_LOCATION_SELECTION_MODE = "isLocationsSelectionMode";
  public static final int INTENT_LOCATION_RESULT_ID = 10000;
  FragmentManager mn;
  LocationRepository locationRepository;

  LocationDialog locationDialog;
  ComponentUIList<LocationModel> list;
  ComponentUIFloatingButton newItemButton;
  boolean isSelectionMode;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_locations_list);
    mn = getSupportFragmentManager();
    locationRepository = GlobalApplication.getAppDB().locationRepository;

    isSelectionMode = getIntent().getIntExtra(INTENT_LOCATION_SELECTION_MODE, 0) == 1;

    UIToolbar.setTitle(this, getLocalizationByKey(LocationLocale.Keys.listCaption), null);
    locationDialog = new LocationDialog(this, locationRepository.getNameLength());

    initNewItemButton(isSelectionMode);

    list = initList(locationRepository.list(0, 0), isSelectionMode);
  }

  ComponentUIList<LocationModel> initList(LocationModel[] items, boolean isSelectionMode) {
    ComponentUIList<LocationModel> list = ComponentUIList.cast(mn.findFragmentById(R.id.EVENTS_LIST_ID));
    list.controls.setAdapter(items, new ListItemLocation(getSupportFragmentManager(), this, isSelectionMode));
    return list;
  }

  private void onCreateItem(String text) {
    LocationModel newEvent = locationRepository.getDraftRecord();
    newEvent.name.set(text);
    newEvent.save();
    list.controls.add(newEvent, false);
  }

  void initNewItemButton(boolean isHide) {
    newItemButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.EVENTS_ADD_NEW_ITEM_ID));
    if (isHide) {
      newItemButton.controls.hide();
      return;
    }
    FloatingButtonsPreset.setPreset(FloatingButtonsPreset.Presets.addNewItem, newItemButton);
    newItemButton.controls.setOnClick(this);
  }

  void openAddNewItemDialog(){
    locationDialog.initCreateState();
    locationDialog.dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        onCreateItem(
          locationDialog.getName()
        );
      }
      @Override
      public void onReject() {

      }
    });
    locationDialog.show();
  }

  /**
   * click callback for short click event
   *
   * @param btnId - element unique id that fired event
   * @param tag -
   */
  @Override
  public void onClick(int btnId, String tag) {
    if(btnId == newItemButton.controls.getId()){
      openAddNewItemDialog();
    }
  }

  /**
   * click callback for long click event
   *
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {

  }

  @Override
  public void onUpdate(int listItemId) {
    LocationModel item = list.controls.getItemByListId(listItemId);
    locationDialog.initUpdateState(
      item.name.get(),
      item.description.get()
    );

    locationDialog.dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        item.name.set(locationDialog.getName());
        item.description.set(locationDialog.getDescription());
        item.save();
        list.controls.update(item, listItemId);
      }
      @Override
      public void onReject() {

      }
    });
    locationDialog.show();
  }

  @Override
  public void onDelete(int listItemId) {
    LocationModel item = list.controls.getItemByListId(listItemId);
    list.controls.delete(listItemId);
    locationRepository.removeRecord(item.id);
  }

  @Override
  public void onSelect(int listItemId) {
    LocationModel item = list.controls.getItemByListId(listItemId);
    Intent eventIntent;
    eventIntent = new Intent(this, PageLocation.class);
    eventIntent.putExtra(INTENT_LOCATION_ID, item.id.get().toString());

    if (isSelectionMode) {
      setResult(RESULT_OK, eventIntent);
      finish();
      return;
    }

    eventIntent.putExtra(INTENT_GOAL_ID, getIntent().getStringExtra(INTENT_GOAL_ID));
    startActivity(eventIntent);
  }
}