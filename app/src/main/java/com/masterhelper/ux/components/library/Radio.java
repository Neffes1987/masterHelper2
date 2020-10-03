package com.masterhelper.ux.components.library;

import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.UXElement;

import java.util.List;

/** class for working with RadioGroup widget */
public class Radio extends UXElement<RadioGroup> {

  public Radio(@NonNull View androidSystemComponent){
    this.setUxElement((RadioGroup) androidSystemComponent);
  }

  /**
   * get current checked radio button in group
   */
  public int getSelectedItemIndex() {
    RadioGroup group = this.getUxElement();
    RadioButton button = group.findViewById(group.getCheckedRadioButtonId());
    return (int) button.getTag();
  }

  /**
   * create list of radio buttons by array of buttons name
   */
  public void setRadioList(List<String> list){
    RadioGroup group = this.getUxElement();
    for (String name : list) {
      RadioButton button = new RadioButton(group.getContext());
      button.setText(name);
      button.setId(View.generateViewId());
      button.setTag(list.indexOf(name));
      group.addView(button);
    }
  }
}
