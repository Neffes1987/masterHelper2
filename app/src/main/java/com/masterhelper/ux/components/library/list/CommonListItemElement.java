package com.masterhelper.ux.components.library.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.ComponentUIFragment;
import com.masterhelper.ux.components.core.SetBtnLocation;

public abstract class CommonListItemElement<DataModel> {
  private SetBtnLocation pListener;
  private boolean attached = false;
  private DataModel pElementData;
  private ComponentUIFragment newFragment;

  protected void setFragment(ComponentUIFragment newFragment) {
    this.newFragment = newFragment;
  }

  public ComponentUIFragment getFragment(){
    return newFragment;
  }

  public String getTag(){
    return getFragment().getTag();
  }

  protected abstract void init(View container, FragmentManager pManager);
  protected abstract void onRender(DataModel data);
  protected abstract void onAttached();

  private DataModel getElementData() {
    return pElementData;
  }

  public void setElementData(DataModel data){
    if(attached){
      onRender(data);
      return;
    }
    pElementData = data;
  }

  private void onFragmentAttached(){
    onAttached();
    if(getElementData() != null){
      onRender(getElementData());
    }
    attached = true;

  }

  public SetBtnLocation getListener() {
    return pListener;
  }

  private int generateViewId(){
    return  View.generateViewId();
  }

  private String generateTag(int viewId){
    return "fragment_tag_" + viewId;
  }

  protected void attach(View container, FragmentManager pManager ){
    newFragment.setOnAttachListener(this::onFragmentAttached);
    String tag = generateTag(generateViewId()) + "_item_";
    pManager
      .beginTransaction()
      .add(container.getId(), newFragment, tag)
      .commit();
  }
}
