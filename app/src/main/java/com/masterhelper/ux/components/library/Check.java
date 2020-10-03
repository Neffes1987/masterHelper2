package com.masterhelper.ux.components.library;

import android.view.View;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.UXTickControl;

/** class for working with Checkbox widget */
public class Check extends UXTickControl<CheckBox> {

  public Check(@NonNull View androidSystemComponent){
    this.setUxElement((CheckBox) androidSystemComponent);
  }


  /**
   * set control current value
   *
   * @param newElementState - value for checkbox
   */
  @Override
  public void setState(Boolean newElementState) {
    this.getUxElement().setChecked(newElementState);
  }

  /**
   * set control current label value
   *
   * @param tickLabel - value of label for radion or checkbox
   */
  @Override
  public void setLabelText(String tickLabel) {
    this.getUxElement().setText(tickLabel);
  }

  /**
   * get current value from element
   */
  @Override
  public Boolean getState() {
    return getUxElement().isChecked();
  }
}
