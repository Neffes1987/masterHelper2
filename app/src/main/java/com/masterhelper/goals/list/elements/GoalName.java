package com.masterhelper.goals.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.elements.LabelControl;

public class GoalName extends LabelControl {

  public GoalName(View container, FragmentManager manager){
    super(container, manager);
  }

  @Override
  protected void onRender(String data) {
    getLabel().controls.setText(data);

  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getLabel().setLayoutWeight(1);
  }
}
