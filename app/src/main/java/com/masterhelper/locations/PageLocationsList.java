package com.masterhelper.locations;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.locations.repository.LocationModel;
import com.masterhelper.locations.repository.LocationRepository;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.buttons.floating.FloatingButtonsPreset;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.CommonHolderPayloadData;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.components.library.list.ListItemControlsListener;

import java.util.ArrayList;

import static com.masterhelper.goals.PageGoal.INTENT_GOAL_ID;
import static com.masterhelper.locations.LocationLocale.getLocalizationByKey;


public class PageLocationsList extends AppMenuActivity implements SetBtnLocation, ListItemControlsListener {
  public static final String INTENT_LOCATION_ID = "locationId";
  public static final String INTENT_LOCATION_SELECTION_MODE = "isLocationsSelectionMode";
  FragmentManager mn;
  LocationRepository locationRepository;

  LocationDialog locationDialog;
  ComponentUIList list;
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

  ComponentUIList initList(LocationModel[] items, boolean isSelectionMode) {
    ComponentUIList list = ComponentUIList.cast(mn.findFragmentById(R.id.EVENTS_LIST_ID));
    ArrayList<CommonItem.Flags> flags = new ArrayList<>();
    flags.add(CommonItem.Flags.showPreview);
    if (!isSelectionMode) {
      flags.add(CommonItem.Flags.showDelete);
    }

    ArrayList<CommonHolderPayloadData> itemsList = new ArrayList<>();
    for (LocationModel model : items) {
      String preview = model.previewUrl.get();
      itemsList.add(new CommonHolderPayloadData(model.id, model.name.get(), preview != null ? preview : ""));
    }

    list.controls.setAdapter(itemsList, this, flags);
    return list;
  }

  private void onCreateItem(String text) {
    LocationModel newEvent = locationRepository.getDraftRecord();
    newEvent.name.set(text);
    newEvent.description.set("");
    newEvent.save();
    CommonHolderPayloadData newItem = new CommonHolderPayloadData(newEvent.id, text, "");
    list.controls.add(newItem, false);
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

  @Override
  protected void onStart() {
    super.onStart();
    list = initList(locationRepository.list(0, 0), isSelectionMode);
  }

  /**
   * click callback for short click event
   *
   * @param btnId - element unique id that fired event
   * @param tag   -
   */
  @Override
  public void onClick(int btnId, String tag) {
    if (btnId == newItemButton.controls.getId()) {
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
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    LocationModel selectedLocation = locationRepository.getRecord(item.getId());
    locationDialog.initUpdateState(
      item.getTitle()
    );

    locationDialog.dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        selectedLocation.name.set(locationDialog.getName());
        item.setTitle(locationDialog.getName());
        selectedLocation.save();
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
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    list.controls.delete(listItemId);
    locationRepository.removeRecord(item.getId());
  }

  @Override
  public void onSelect(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    Intent eventIntent;
    eventIntent = new Intent(this, PageControlsListener.class);
    eventIntent.putExtra(INTENT_LOCATION_ID, item.getId().get().toString());

    if (isSelectionMode) {
      setResult(RESULT_OK, eventIntent);
      finish();
      return;
    }

    eventIntent.putExtra(INTENT_GOAL_ID, getIntent().getStringExtra(INTENT_GOAL_ID));
    startActivity(eventIntent);
  }

  @Override
  public void onPlay(int listItemId) {

  }
}