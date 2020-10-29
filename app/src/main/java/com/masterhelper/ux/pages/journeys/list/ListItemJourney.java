package com.masterhelper.ux.pages.journeys.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.db.repositories.journeys.JourneyModel;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.ux.pages.journeys.list.elements.JourneyDeleteControl;
import com.masterhelper.ux.pages.journeys.list.elements.JourneyEditControl;
import com.masterhelper.ux.pages.journeys.list.elements.JourneyName;

/**  */
public class ListItemJourney extends CommonItem<JourneyModel> implements SetBtnEvent {
  private JourneyName name;
  private JourneyEditControl editButton;
  private JourneyDeleteControl deleteButton;


  public ListItemJourney(FragmentManager manager, ListItemEvents listItemJourneyEvents) {
    super(manager, listItemJourneyEvents);
  }

  public ListItemJourney(View view, FragmentManager manager, ListItemEvents listItemJourneyEvents) {
    super(view, manager, listItemJourneyEvents);
    View pLayout = getHeader();
    name = new JourneyName(pLayout, manager);
    deleteButton = new JourneyDeleteControl(pLayout, manager, this);
    editButton = new JourneyEditControl(pLayout, manager, this);
  }

  @Override
  protected void update(JourneyModel itemData, int listItemId) {
    setListItemId(listItemId);
    name.setElementData(itemData.name.get());
  }

  @Override
  public CommonItem<JourneyModel> clone(View view) {
    return new ListItemJourney(view, getManager(), getListItemSceneEvents());
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
    }
  }

  /**
   * click callback for long click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {}

}