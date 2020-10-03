package com.masterhelper.ux.components.library;

import android.view.View;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.masterhelper.ux.components.core.UXButton;

/** facade for floating android button */
public class FloatButton extends UXButton<FloatingActionButton> {

  public FloatButton(@NonNull View androidSystemComponent){
    this.setUxElement((FloatingActionButton) androidSystemComponent);
  }

  /** set button icon */
  public void setIcon(int icon){
    this.getUxElement().setImageResource(icon);
  }


}
