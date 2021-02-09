package com.masterhelper.locations.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.db.repositories.events.EventModel;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.locations.list.elements.*;
import com.masterhelper.ux.resources.ResourceColors;

/**  */
public class ListItemEvent extends CommonItem<EventModel> implements SetBtnEvent {
  private EventName name;
  private EventName description;
  private EventEditControl editButton;
  private EventDeleteControl deleteButton;
  private EventPlayBtn playButton;
  private EventExpandBtn expandBtn;


  public ListItemEvent(FragmentManager manager, ListItemEvents listItemJourneyEvents) {
    super(manager, listItemJourneyEvents);
  }

  public ListItemEvent(View view, FragmentManager manager, ListItemEvents listItemJourneyEvents) {
    super(view, manager, listItemJourneyEvents);
    View header = getHeader();
    View body = getBody();
    View controls = getButtons();
    setBodyVisibility(false);

    name = new EventName(header, manager);
    expandBtn = new EventExpandBtn(header, manager, this);
    description = new EventName(body, manager);
    deleteButton = new EventDeleteControl(controls, manager, this);
    editButton = new EventEditControl(controls, manager, this);
    playButton = new EventPlayBtn(controls, manager, this);

    header.setOnClickListener(v -> {
      expandBtn.toggleCurrentState();
      expandBtn.setOrientation(expandBtn.getExpanded());
      setBodyVisibility(expandBtn.getExpanded());
    });
  }

  @Override
  protected void update(EventModel itemData, int listItemId) {
    setListItemId(listItemId);
    name.setElementData(itemData.name.get());
    description.setElementData(itemData.description.get());
    setHeaderColorByType(itemData.type.get());
  }

  private void setHeaderColorByType(EventModel.EventType type){
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
  public CommonItem<EventModel> clone(View view) {
    return new ListItemEvent(view, getManager(), getListItemSceneEvents());
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