package com.masterhelper.goals.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.elements.LabelControl;
import com.masterhelper.goals.GoalLocale;

public class GoalScriptsDescription extends LabelControl {

  public GoalScriptsDescription(View container, FragmentManager manager){
    super(container, manager);
  }

  @Override
  protected void onRender(String data) {
    getLabel().controls.setText(GoalLocale.getLocalizationByKey(GoalLocale.Keys.scriptsProgressTitle));
    getLabel().valueControls.setText(data);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
  }
}
