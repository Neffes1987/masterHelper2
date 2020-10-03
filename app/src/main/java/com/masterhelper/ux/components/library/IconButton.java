package com.masterhelper.ux.components.library;

import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.UXButton;

/** facade for floating android button */
public class IconButton extends UXButton<ImageButton> {

  public IconButton(@NonNull View androidSystemComponent){
    this.setUxElement((ImageButton) androidSystemComponent);
  }

  /** set button icon */
  public void setIcon(int icon){
    this.getUxElement().setImageResource(icon);
  }

}
