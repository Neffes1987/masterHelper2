package com.masterhelper.ux.components.library.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;

public class LabelControl extends CommonListItemElement<String> {
  ComponentUILabel label;

  public ComponentUILabel getLabel() {
    return label;
  }

  public LabelControl(View container, FragmentManager manager){
    init(container, manager);
  }

  @Override
  protected void init(View container, FragmentManager manager) {
    setFragment(new ComponentUILabel());
    attach(container, manager);
  }

  @Override
  protected void onRender(String data) {
    label.controls.setText(data);
  }

  @Override
  protected void onAttached() {
    label = (ComponentUILabel) getFragment();
  }
}
