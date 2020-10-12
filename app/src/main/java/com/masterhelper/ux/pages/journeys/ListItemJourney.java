package com.masterhelper.ux.pages.journeys;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.resources.ResourceIcons;

/**  */
public class ListItemJourney extends CommonItem<TestUIListDataItem> implements SetBtnEvent {
  private boolean isInitiated = false;

  private View pLayout;
  private FragmentManager pManager;

  private ComponentUILabel name;
  private TestUIListDataItem defaultData;

  private ComponentUIImageButton editButton;
  private ComponentUIImageButton deleteButton;

  private ListItemJourneyEvents listItemJourneyEvents;



  public void setListItemJourneyEvents(ListItemJourneyEvents listItemJourneyEvents) {
    this.listItemJourneyEvents = listItemJourneyEvents;
  }

  public ListItemJourney(FragmentManager manager, ListItemJourneyEvents listItemJourneyEvents) {
    setListItemJourneyEvents(listItemJourneyEvents);
    setManager(manager);
  }

  public ListItemJourney(View view, FragmentManager manager, ListItemJourneyEvents listItemJourneyEvents) {
    pLayout = view.findViewById(CommonItem.TEMPLATE_HEADER_ID);
    pLayout.setId(this.generateViewId());
    setManager(manager);
    setListItemJourneyEvents(listItemJourneyEvents);
  }

  private void setManager(FragmentManager pManager) {
    this.pManager = pManager;
  }

  @Override
  protected void update(TestUIListDataItem itemData, int listItemId) {
    init(listItemId);
    if(name.controls == null){
      defaultData = itemData;
    } else {
      defaultData = null;
      name.controls.setText(itemData.getText());
    }
  }

  private void initName(){
    name = new ComponentUILabel();
    attachFragment(this, name, pLayout, pManager);
  }

  private void initEditBtn(){
    editButton = new ComponentUIImageButton();
    attachFragment(this, editButton, pLayout, pManager);
  }

  private void initDeleteBtn(){
    deleteButton = new ComponentUIImageButton();
    attachFragment(this, deleteButton, pLayout, pManager);
  }

  protected void init(int listItemId){
    setListItemId(listItemId);
    if(isInitiated){
      return ;
    }
    isInitiated = true;
    initName();
    initEditBtn();
    initDeleteBtn();
  }

  @Override
  public CommonItem<TestUIListDataItem> clone(View view) {
    return new ListItemJourney(view, pManager, listItemJourneyEvents);
  }

  @Override
  public void onFragmentAttached(String tag) {
    if (this.checkEqual(name, tag)) {
      if (defaultData != null) {
        name.controls.setText(defaultData.getText());
        name.setLayoutWeight(1);
      }
      return;
    }
    if(this.checkEqual(deleteButton, tag)){
      deleteButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.clear));
      deleteButton.controls.setOnClick(this);
    }
    if(this.checkEqual(editButton, tag)){
      editButton.controls.setId(View.generateViewId());
      editButton.controls.setOnClick(this);
    }
  }

  /**
   * click callback for short click event
   *
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onClick(int btnId, String tag) {
    if(deleteButton.controls.getTag().equals(tag)){
      if(listItemJourneyEvents != null){
        listItemJourneyEvents.onDelete(pListItemId);
      }
      return;
    }
    if(editButton.controls.getTag().equals(tag)){
      if(listItemJourneyEvents != null){
        listItemJourneyEvents.onUpdate(pListItemId);
      }
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

  public interface ListItemJourneyEvents{
    void onUpdate(int listItemId);
    void onDelete(int listItemId);
  }
}