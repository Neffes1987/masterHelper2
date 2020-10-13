package com.masterhelper.ux.components.library.buttons.text;

import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.UXButton;
import com.masterhelper.ux.resources.ResourceColors;

/** facade for simple android button */
public class TextButton extends UXButton<Button> {

  public TextButton(@NonNull View androidSystemComponent){
    this.setUxElement((Button) androidSystemComponent);
  }

  /** set text on button */
  public void setText(String text){
    this.getUxElement().setText(text);
  }

  /** set text color on button */
  public void setTextColor(ResourceColors.ResourceColorType colorType){
    this.getUxElement().setTextColor(ResourceColors.getColorStateList(colorType));
  }
}
