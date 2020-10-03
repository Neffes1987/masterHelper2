package com.masterhelper.ux.components.core;

import android.view.View;

/** base class for all checkboxes and radio buttons inside the system */
public abstract class UXTickControl<ControlType extends View> extends UXElement<ControlType> {
  /** set control current value
   *  @param newElementState - value for radion or checkbox
   * */
  public abstract void setState(Boolean newElementState);

  /** set control current label value
   *  @param tickLabel - value of label for radion or checkbox
   * */
  public abstract void setLabelText(String tickLabel);

  /** get current value from element */
  public abstract Boolean getState();
}
