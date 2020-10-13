package com.masterhelper.ux.components.library.buttons.icon;

import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.UXButton;
import com.masterhelper.ux.resources.ResourceColors;

/** facade for floating android button */
public class IconButton extends UXButton<ImageButton> {

  public IconButton(@NonNull View androidSystemComponent){
    this.setUxElement((ImageButton) androidSystemComponent);
  }

  /** set button icon */
  public void setIcon(int icon){
    this.getUxElement().setImageResource(icon);
  }

  /** set icon color on button */
  public void setIconColor(ResourceColors.ResourceColorType colorType){
    this.getUxElement().setImageTintList(ResourceColors.getColorStateList(colorType));
  }

}
