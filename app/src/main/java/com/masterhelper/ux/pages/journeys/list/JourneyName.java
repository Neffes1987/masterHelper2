package com.masterhelper.ux.pages.journeys.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.components.library.list.CommonListItemElement;

public class JourneyName extends CommonListItemElement<String> {
  ComponentUILabel name;

  public JourneyName(View container, FragmentManager manager){
    init(container, manager);
  }

  @Override
  protected void init(View container, FragmentManager manager) {
    setFragment(new ComponentUILabel());
    attach(container, manager);
  }

  @Override
  protected void onRender(String data) {
    name.controls.setText(data);
  }

  @Override
  protected void onAttached() {
    name = (ComponentUILabel) getFragment();
    name.setLayoutWeight(1);
  }
}
