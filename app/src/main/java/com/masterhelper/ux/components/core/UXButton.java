package com.masterhelper.ux.components.core;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;


/** base class for all buttons in ux system */
public abstract class UXButton<ButtonType extends View> extends UXElement<ButtonType> {

  /** ux button click setter for click events
   *  @param eventHandler - callback class for button click events
   * */
  public void setOnClick(final @NonNull SetBtnEvent eventHandler){
    this.getUxElement().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        eventHandler.onClick(v.getId());
      }
    });

    this.getUxElement().setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        eventHandler.onLongClick(v.getId());
        return true;
      }
    });
  }

  public void setColor(String color){
    ViewCompat.setBackgroundTintList(this.getUxElement(), ColorStateList.valueOf(Color.parseColor(color)));
  }
}
