package com.masterhelper.ux.components.library.tickButton.check;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.UXElement;

import java.util.Collections;
import java.util.List;

/** class for working with Checkbox widget */
public class CheckBoxesGroup extends UXElement<LinearLayout> {

  public CheckBoxesGroup(@NonNull View androidSystemComponent){
    this.setUxElement((LinearLayout) androidSystemComponent);
  }

  /**
   * get current checked radio button in group
   */
  public List<Integer> getSelectedItemsIndex() {
    List<Integer> selectedCheckboxes = Collections.emptyList();
    LinearLayout group = this.getUxElement();
    int childrenCount = group.getChildCount();
    for (int i=0; i<childrenCount; i+=1){
      CheckBox check = (CheckBox) group.getChildAt(i);
      if(check.isChecked()){
        selectedCheckboxes.add(i);
      }
    }
    return selectedCheckboxes;
  }

  /**
   * create list of radio buttons by array of buttons name
   */
  public void setList(List<String> list){
    LinearLayout group = this.getUxElement();
    for (String name : list) {
      CheckBox button = new CheckBox(group.getContext());
      button.setText(name);
      button.setId(View.generateViewId());
      button.setTag(list.indexOf(name));
      group.addView(button);
    }
  }
}
