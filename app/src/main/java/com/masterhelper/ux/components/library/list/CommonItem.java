package com.masterhelper.ux.components.library.list;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.ComponentUIFragment;

/** list view control class where db data mapped into ui components */
public abstract class CommonItem<DataModel> implements ComponentUIFragment.OnAttachListener {
  public static final int TEMPLATE_ID = R.layout.fragment_list_item;
  public static final int TEMPLATE_HEADER_ID = R.id.COMPONENT_LIST_ITEM_HEADER_ID;
  public static final int TEMPLATE_BODY_ID = R.id.COMPONENT_LIST_ITEM_BODY_ID;

  public CommonItem() {}

  protected abstract void update(DataModel itemData, int listItemId);

  protected abstract void init();

  public abstract CommonItem<DataModel> clone(View view);


  public int generateViewId(){
    return  View.generateViewId();
  }

  private String generateTag(int viewId){
    return "fragment_tag_" + viewId;
  }

  protected void attachFragment( CommonItem<DataModel> context, ComponentUIFragment newFragment, View container, FragmentManager pManager ){
    newFragment.setOnAttachListener(context);
    String tag = generateTag(generateViewId());
    pManager
      .beginTransaction()
      .add(container.getId(), newFragment, tag)
      .commit();
  };

  protected boolean checkEqual(ComponentUIFragment uiComponent, String tag){
    return uiComponent.getTag() != null && uiComponent.getTag().equals(tag);
  }

}
