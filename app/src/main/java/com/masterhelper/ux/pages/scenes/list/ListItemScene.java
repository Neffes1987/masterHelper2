package com.masterhelper.ux.pages.scenes.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.pages.journeys.TestUIListDataItem;

/**  */
public class ListItemScene extends CommonItem<TestUIListDataItem> implements SetBtnEvent {
  private FragmentManager pManager;
  private SceneName name;

  private SceneExpand editButton;
  private SceneDeleteControl deleteButton;
  private ListItemJourneyEvents listItemJourneyEvents;

  public void setListItemJourneyEvents(ListItemJourneyEvents listItemJourneyEvents) {
    this.listItemJourneyEvents = listItemJourneyEvents;
  }

  public ListItemScene(FragmentManager manager, ListItemJourneyEvents listItemJourneyEvents) {
    setListItemJourneyEvents(listItemJourneyEvents);
    setManager(manager);
  }

  public ListItemScene(View view, FragmentManager manager, ListItemJourneyEvents listItemJourneyEvents) {
    View pLayout = view.findViewById(CommonItem.TEMPLATE_HEADER_ID);
    pLayout.setId(View.generateViewId());
    setListItemJourneyEvents(listItemJourneyEvents);

    name = new SceneName(pLayout, manager);
    deleteButton = new SceneDeleteControl(pLayout, manager, this);
    editButton = new SceneExpand(pLayout, manager, this);

    pLayout.setOnClickListener(v -> listItemJourneyEvents.onSelect(pListItemId));
  }

  private void setManager(FragmentManager pManager) {
    this.pManager = pManager;
  }

  @Override
  protected void update(TestUIListDataItem itemData, int listItemId) {
    setListItemId(listItemId);
    name.setElementData(itemData.getText());
  }

  @Override
  public CommonItem<TestUIListDataItem> clone(View view) {
    return new ListItemScene(view, pManager, listItemJourneyEvents);
  }

  /**
   * click callback for short click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onClick(int btnId, String tag) {
    if(deleteButton.getTag().equals(tag)){
      if(listItemJourneyEvents != null){
        listItemJourneyEvents.onDelete(pListItemId);
      }
      return;
    }
    if(editButton.getTag().equals(tag)){
      if(listItemJourneyEvents != null){
        listItemJourneyEvents.onUpdate(pListItemId);
      }
    }
  }

  /**
   * click callback for long click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {}

  public interface ListItemJourneyEvents{
    void onUpdate(int listItemId);
    void onDelete(int listItemId);
    void onSelect(int listItemId);
  }
}