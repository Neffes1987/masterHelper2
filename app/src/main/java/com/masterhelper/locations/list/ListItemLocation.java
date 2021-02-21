package com.masterhelper.locations.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.locations.repository.LocationModel;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.locations.list.elements.*;

/**  */
public class ListItemLocation extends CommonItem<LocationModel> implements SetBtnLocation {
  private LocationtName name;
  private LocationtName description;
  private LocationEditControl editButton;
  private LocationDeleteControl deleteButton;
  private boolean isSelectionMode;


  public ListItemLocation(FragmentManager manager, com.masterhelper.ux.components.library.list.ListItemLocation listItemJourneyEvents, Boolean isSelectionMode) {
    super(manager, listItemJourneyEvents);
    this.isSelectionMode = isSelectionMode;
  }

  public ListItemLocation(View view, FragmentManager manager, com.masterhelper.ux.components.library.list.ListItemLocation listItemJourneyEvents, boolean isSelectionMode) {
    super(view, manager, listItemJourneyEvents);
    View header = getHeader();
    View body = getBody();
    setBodyVisibility(true);

    name = new LocationtName(header, manager);
    description = new LocationtName(body, manager);
    if (!isSelectionMode) {
      deleteButton = new LocationDeleteControl(header, manager, this);
      editButton = new LocationEditControl(header, manager, this);
    }

  }

  @Override
  protected void update(LocationModel itemData, int listItemId) {
    setListItemId(listItemId);
    name.setElementData(itemData.name.get());
    description.setElementData(itemData.description.get());
  }

  @Override
  public CommonItem<LocationModel> clone(View view) {
    return new ListItemLocation(view, getManager(), getListItemSceneEvents(), isSelectionMode);
  }

  /**
   * click callback for short click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onClick(int btnId, String tag) {
    if(getListItemSceneEvents() == null){
      return;
    }
    if(deleteButton.getTag().equals(tag)){
      getListItemSceneEvents().onDelete(pListItemId);
      return;
    }
    if(editButton.getTag().equals(tag)){
      getListItemSceneEvents().onUpdate(pListItemId);
      return;
    }
  }

  /**
   * click callback for long click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {}

}