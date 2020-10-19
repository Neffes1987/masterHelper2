package com.masterhelper.ux.pages.scenes.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ListItemEvents;

/**  */
public class ListItemScene extends CommonItem<UISceneItemData> implements SetBtnEvent {

  private SceneName name;
  private SceneName description;

  private SceneEditBtn editButton;
  private SceneDeleteControl deleteButton;

  public ListItemScene(FragmentManager manager, ListItemEvents listItemScenesEvents) {
    super(manager, listItemScenesEvents);
  }

  public ListItemScene(View view, FragmentManager manager, ListItemEvents listItemScenesEvents) {
    super(view, manager, listItemScenesEvents);
    View header = getHeader();
    View body = getBody();

    name = new SceneName(header, manager);
    description = new SceneName(body, manager);
    deleteButton = new SceneDeleteControl(header, manager, this);
    editButton = new SceneEditBtn(header, manager, this);
  }

  @Override
  protected void update(UISceneItemData itemData, int listItemId) {
    setListItemId(listItemId);
    name.setElementData(itemData.getText());
    description.setElementData(itemData.getDescription());
  }

  @Override
  public CommonItem<UISceneItemData> clone(View view) {
    return new ListItemScene(view, getManager(), getListItemSceneEvents());
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