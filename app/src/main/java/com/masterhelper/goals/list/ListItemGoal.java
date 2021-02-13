package com.masterhelper.goals.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.goals.repository.GoalModel;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ListItemLocation;
import com.masterhelper.goals.list.elements.*;

/**  */
public class ListItemGoal extends CommonItem<GoalModel> implements SetBtnLocation {

  private GoalName name;
  private GoalName description;
  private GoalEditBtn editButton;
  private GoalDeleteControl deleteButton;

  public ListItemGoal(FragmentManager manager, ListItemLocation listItemScenesEvents) {
    super(manager, listItemScenesEvents);
  }

  public ListItemGoal(View view, FragmentManager manager, ListItemLocation listItemScenesEvents) {
    super(view, manager, listItemScenesEvents);
    View header = getHeader();
    View body = getBody();

    setBodyVisibility(true);

    name = new GoalName(header, manager);
    description = new GoalName(body, manager);

    deleteButton = new GoalDeleteControl(header, manager, this);
    editButton = new GoalEditBtn(header, manager, this);
  }

  @Override
  protected void update(GoalModel itemData, int listItemId) {
    setListItemId(listItemId);
    name.setElementData(itemData.name.get());
    description.setElementData(itemData.description.get());
  }

  @Override
  public CommonItem<GoalModel> clone(View view) {
    return new ListItemGoal(view, getManager(), getListItemSceneEvents());
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