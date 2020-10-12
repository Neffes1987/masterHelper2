package com.masterhelper.ux.components.core;

import android.view.View;
import androidx.annotation.NonNull;

/** Base class for all UX components */
public abstract class UXElement<Type extends View> {
  /** state for android element */
  private Type uxElement;

  /** set android control element as a target for all operations with current class
   *  @param uxElement - android base component as a target for all class operations
   * */
  protected void setUxElement(@NonNull Type uxElement) {
    this.uxElement = uxElement;
  }

  /** get android control element as a target for all operations with current class */
  protected Type getUxElement() {
    return uxElement;
  }

  /** show target element in parent view template */
  public void show(){
    getUxElement().setVisibility(View.VISIBLE);
  };

  /** hide target element in parent view template */
  public void hide(){
    getUxElement().setVisibility(View.GONE);
  };

  /** get unique component id */
  public int getId(){
    return getUxElement().getId();
  }
  public void setId(int id){
    getUxElement().setId(id);
  }


  public String getTag(){
    return getUxElement().getTag().toString();
  }
  public void setTag(String tag){
    getUxElement().setTag(tag);
  }
}
