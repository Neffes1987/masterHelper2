package com.masterhelper.ux.components.library.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.components.library.tickButton.check.ComponentUICheckBox;
import java.util.Collections;

public class SelectionControl extends CommonListItemElement<Boolean> {
  ComponentUICheckBox checkbox;
  String checkboxName;

  public ComponentUICheckBox getCheckbox() {
    return checkbox;
  }

  public SelectionControl(View container, FragmentManager manager, String checkboxName){
    init(container, manager);
    this.checkboxName = checkboxName;
  }

  @Override
  protected void init(View container, FragmentManager manager) {
    setFragment(new ComponentUICheckBox());
    attach(container, manager);
  }

  @Override
  protected void onRender(Boolean isSelected) {
    checkbox.controls.setSelectedItems(isSelected ? Collections.singletonList(0) : Collections.emptyList());
  }

  @Override
  protected void onAttached() {
    checkbox = (ComponentUICheckBox) getFragment();
    checkbox.controls.setList(Collections.singletonList(checkboxName));
  }
}
