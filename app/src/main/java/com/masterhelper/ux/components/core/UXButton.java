package com.masterhelper.ux.components.core;

import android.view.View;
import androidx.annotation.NonNull;
import com.masterhelper.ux.resources.ResourceColors;


/** base class for all buttons in ux system */
public abstract class UXButton<ButtonType extends View> extends UXElement<ButtonType> {

  /** ux button click setter for click events
   *  @param eventHandler - callback class for button click events
   * */
  public void setOnClick(final @NonNull SetBtnEvent eventHandler){
    this.getUxElement().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(v.getTag() == null){
          eventHandler.onClick(v.getId(), null);
        } else {
          eventHandler.onClick(v.getId(), v.getTag().toString());
        }

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

  public void setColor(ResourceColors.ResourceColorType color){
    ResourceColors.setBackgroundTint(this.getUxElement(), color);
  }

}
