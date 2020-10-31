package com.masterhelper.ux.components.core;

import android.view.View;
import androidx.annotation.NonNull;

/** base class for text elements in ui */
public abstract class UXTextElement<TextType extends View> extends UXElement<TextType> {
  /** set text value in text component
   *  @param text - new value fom text element
   * */
  public abstract void setText(final @NonNull String text);

  /** read text from text component */
  public abstract String  getText();

  /** clear text from text component */
  public void clear(){
    this.setText("");
  };


  public abstract void setMaxLength(int length);

}
