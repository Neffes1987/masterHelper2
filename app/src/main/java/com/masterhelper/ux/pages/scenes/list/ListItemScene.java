package com.masterhelper.ux.pages.scenes.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.ux.components.library.list.elements.ProgressControl;
import com.masterhelper.ux.pages.scenes.list.elements.*;

/**  */
public class ListItemScene extends CommonItem<UISceneItemData> implements SetBtnEvent {

  private SceneName name;
  private SceneName description;
  private SceneScriptsDescription eventsProgress;
  private ProgressControl eventsProgressBar;

  private SceneEditBtn editButton;
  private ScenePlayBtn playButton;
  private SceneDeleteControl deleteButton;
  private SceneExpandBtn expandBtn;

  public ListItemScene(FragmentManager manager, ListItemEvents listItemScenesEvents) {
    super(manager, listItemScenesEvents);
  }

  public ListItemScene(View view, FragmentManager manager, ListItemEvents listItemScenesEvents) {
    super(view, manager, listItemScenesEvents);
    View header = getHeader();
    View body = getBody();
    View buttons = getButtons();
    View staticPart = getStaticPart();

    header.setOnClickListener(v -> {
      expandBtn.toggleCurrentState();
      expandBtn.setOrientation(expandBtn.getExpanded());
      setBodyVisibility(expandBtn.getExpanded());
    });

    setBodyVisibility(false);

    name = new SceneName(header, manager);
    expandBtn = new SceneExpandBtn(header, manager, this);

    eventsProgressBar= new ProgressControl(staticPart, manager);
    eventsProgress = new SceneScriptsDescription(body, manager);
    description = new SceneName(body, manager);

    deleteButton = new SceneDeleteControl(buttons, manager, this);
    editButton = new SceneEditBtn(buttons, manager, this);
    playButton = new ScenePlayBtn(buttons, manager, this);
  }

  @Override
  protected void update(UISceneItemData itemData, int listItemId) {
    setListItemId(listItemId);
    name.setElementData(itemData.getText());
    description.setElementData(itemData.getDescription());
    eventsProgress.setElementData(itemData.getFinishedEvents() + "/" + itemData.getTotalEvents());

    eventsProgressBar.setMaxValue(itemData.getTotalEvents());
    eventsProgressBar.setElementData(itemData.getFinishedEvents());
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