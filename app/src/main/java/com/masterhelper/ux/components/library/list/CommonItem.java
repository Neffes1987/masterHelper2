package com.masterhelper.ux.components.library.list;

import android.view.View;

/** list view control class where db data mapped into ui components */
public abstract class CommonItem<DataModel> implements ICommonItem<DataModel> {
  protected View itemView;
  protected int TEMPLATE_ID = 0;

  public CommonItem(View v){
    itemView = v;
  }

  public void updateHolderByData(DataModel itemData) {}

  public abstract CommonItem<DataModel> getInstance(View v);

}
