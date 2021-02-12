package com.masterhelper.locations.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.locations.repository.LocationModel;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.locations.list.elements.*;
import com.masterhelper.ux.resources.ResourceColors;

/**  */
public class ListItemLocation extends CommonItem<LocationModel> implements SetBtnLocation {
  private LocationtName name;
  private LocationtName description;
  private LocationEditControl editButton;
  private LocationDeleteControl deleteButton;
  private LocationPlayBtn playButton;
  private LocationExpandBtn expandBtn;


  public ListItemLocation(FragmentManager manager, com.masterhelper.ux.components.library.list.ListItemLocation listItemJourneyEvents) {
    super(manager, listItemJourneyEvents);
  }

  public ListItemLocation(View view, FragmentManager manager, com.masterhelper.ux.components.library.list.ListItemLocation listItemJourneyEvents) {
    super(view, manager, listItemJourneyEvents);
    View header = getHeader();
    View body = getBody();
    View controls = getButtons();
    setBodyVisibility(false);

    name = new LocationtName(header, manager);
    expandBtn = new LocationExpandBtn(header, manager, this);
    description = new LocationtName(body, manager);
    deleteButton = new LocationDeleteControl(controls, manager, this);
    editButton = new LocationEditControl(controls, manager, this);
    playButton = new LocationPlayBtn(controls, manager, this);

    header.setOnClickListener(v -> {
      expandBtn.toggleCurrentState();
      expandBtn.setOrientation(expandBtn.getExpanded());
      setBodyVisibility(expandBtn.getExpanded());
    });
  }

  @Override
  protected void update(LocationModel itemData, int listItemId) {
    setListItemId(listItemId);
    name.setElementData(itemData.name.get());
    description.setElementData(itemData.description.get());
    setHeaderColorByType(itemData.type.get());
  }

  private void setHeaderColorByType(LocationModel.EventType type){
    int color;
    switch (type){
      case battle: color = ResourceColors.getColor(ResourceColors.ResourceColorType.battleEvent); break;
      case meeting: color = ResourceColors.getColor(ResourceColors.ResourceColorType.meetingEvent); break;
      case accident: color = ResourceColors.getColor(ResourceColors.ResourceColorType.accidentEvent); break;
      default: throw new Error("Unexpectedly event type");
    }
    View header = getHeader();
    header.setBackgroundColor(color);
  }

  @Override
  public CommonItem<LocationModel> clone(View view) {
    return new ListItemLocation(view, getManager(), getListItemSceneEvents());
  }

  /**
   * click callback for short click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onClick(int btnId, String tag) {
    if(expandBtn.getTag().equals(tag)){
      setBodyVisibility(expandBtn.getExpanded());
      return;
    }
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
    if(playButton.getTag().equals(tag)){
      getListItemSceneEvents().onSelect(pListItemId);
    }
  }

  /**
   * click callback for long click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {}

}