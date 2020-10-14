package com.masterhelper.ux.components.library.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.ComponentUIFragment;

/** list view control class where db data mapped into ui components */
public abstract class CommonItem<DataModel>{
  public static final int TEMPLATE_ID = R.layout.fragment_component_ui_list_item;
  public static final int TEMPLATE_HEADER_ID = R.id.COMPONENT_LIST_ITEM_HEADER_ID;
  public static final int TEMPLATE_BODY_ID = R.id.COMPONENT_LIST_ITEM_BODY_ID;

  protected int pListItemId = -1;

  protected void setListItemId(int pListItemId) {
    this.pListItemId = pListItemId;
  }

  public CommonItem() {}

  protected abstract void update(DataModel itemData, int listItemId);

  public abstract CommonItem<DataModel> clone(View view);

}
