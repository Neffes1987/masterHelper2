package com.masterhelper.ux.components.library;

import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.UXButton;

/** facade for simple android button */
public class TextButton extends UXButton<Button> {

  public TextButton(@NonNull View androidSystemComponent){
    this.setUxElement((Button) androidSystemComponent);
  }

  /** set text on button */
  public void setText(String text){
    this.getUxElement().setText(text);
  }
}
