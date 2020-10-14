package com.masterhelper.ux.pages.journeys.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.components.library.list.CommonListItemElement;

public class JourneyName extends CommonListItemElement<String> {

  public JourneyName(View container, FragmentManager manager){
    init(container, manager);
  }

  @Override
  protected void init(View container, FragmentManager manager) {
    setFragment(new ComponentUILabel());
    attach(container, manager);
  }

  @Override
  protected void render(String data) {
    ComponentUILabel name = (ComponentUILabel) getFragment();
    name.controls.setText(data);
    name.setLayoutWeight(1);
  }
}
