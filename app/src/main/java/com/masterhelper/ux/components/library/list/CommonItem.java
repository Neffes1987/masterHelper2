package com.masterhelper.ux.components.library.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;

/** list view control class where db data mapped into ui components */
public class CommonItem<DataModel>{
  private FragmentManager pManager;
  private ListItemEvents listItemSceneEvents;
  private View header;
  private View body;
  private View buttons;
  private View staticPart;

  public static final int TEMPLATE_ID = R.layout.fragment_component_ui_list_item;
  public static final int TEMPLATE_HEADER_ID = R.id.COMPONENT_LIST_ITEM_HEADER_ID;
  public static final int TEMPLATE_BODY_ID = R.id.COMPONENT_LIST_ITEM_BODY_ID;
  public static final int TEMPLATE_CONTROLS_ID = R.id.COMPONENT_LIST_ITEM_CONTROLS_ID;
  public static final int TEMPLATE_STATIC_PART_ID = R.id.COMPONENT_LIST_ITEM_STATIC_PART;

  protected int pListItemId = -1;

  protected void setListItemId(int pListItemId) {
    this.pListItemId = pListItemId;
  }

  public CommonItem(FragmentManager manager, ListItemEvents listItemEvents) {
    setListItemSceneEvents(listItemEvents);
    setManager(manager);
  }

  public View getBody() {
    return body;
  }

  public View getHeader() {
    return header;
  }

  public View getStaticPart() {
    return staticPart;
  }

  public void setBodyVisibility(boolean isVisible){
    getBody().setVisibility(isVisible ? View.VISIBLE : View.GONE);
    getButtons().setVisibility(isVisible ? View.VISIBLE : View.GONE);
  }

  public View getButtons() {
    return buttons;
  }

  public CommonItem(View view, FragmentManager manager, ListItemEvents listItemEvents) {
    header = view.findViewById(CommonItem.TEMPLATE_HEADER_ID);
    header.setId(View.generateViewId());
    header.setOnClickListener(v -> listItemEvents.onSelect(pListItemId));

    body = view.findViewById(CommonItem.TEMPLATE_BODY_ID);
    body.setId(View.generateViewId());

    buttons = view.findViewById(CommonItem.TEMPLATE_CONTROLS_ID);
    buttons.setId(View.generateViewId());

    staticPart = view.findViewById(CommonItem.TEMPLATE_STATIC_PART_ID);
    staticPart.setId(View.generateViewId());

    setListItemSceneEvents(listItemEvents);
    setManager(manager);
  }

  protected void update(DataModel itemData, int listItemId){}

  public CommonItem<DataModel> clone(View view){
    return null;
  }

  protected void setManager(FragmentManager pManager) {
    this.pManager = pManager;
  }

  protected FragmentManager getManager() {
    return pManager;
  }

  public void setListItemSceneEvents(ListItemEvents listItemSceneEvents) {
    this.listItemSceneEvents = listItemSceneEvents;
  }

  public ListItemEvents getListItemSceneEvents() {
    return listItemSceneEvents;
  }

  
}
