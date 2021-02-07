package com.masterhelper.goals.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.goals.repository.GoalModel;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.goals.list.elements.*;

/**  */
public class ListItemGoal extends CommonItem<GoalModel> implements SetBtnEvent {

  private GoalName name;
  private GoalName description;
  private GoalEditBtn editButton;
  private GoalPlayBtn playButton;
  private GoalDeleteControl deleteButton;
  private GoalExpandBtn expandBtn;

  public ListItemGoal(FragmentManager manager, ListItemEvents listItemScenesEvents) {
    super(manager, listItemScenesEvents);
  }

  public ListItemGoal(View view, FragmentManager manager, ListItemEvents listItemScenesEvents) {
    super(view, manager, listItemScenesEvents);
    View header = getHeader();
    View body = getBody();
    View buttons = getButtons();

    header.setOnClickListener(v -> {
      expandBtn.toggleCurrentState();
      expandBtn.setOrientation(expandBtn.getExpanded());
      setBodyVisibility(expandBtn.getExpanded());
    });

    setBodyVisibility(false);

    name = new GoalName(header, manager);
    expandBtn = new GoalExpandBtn(header, manager, this);
    description = new GoalName(body, manager);

    deleteButton = new GoalDeleteControl(buttons, manager, this);
    editButton = new GoalEditBtn(buttons, manager, this);
    playButton = new GoalPlayBtn(buttons, manager, this);
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
    if(playButton.getTag().equals(tag)){
      getListItemSceneEvents().onSelect(pListItemId);
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