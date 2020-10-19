package com.masterhelper.ux.pages.scenes.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;

public class SceneName extends CommonListItemElement<String> {

  public SceneName(View container, FragmentManager manager){
    init(container, manager);
  }

  @Override
  protected void init(View container, FragmentManager manager) {
    setFragment(new ComponentUILabel());
    attach(container, manager);
  }

  @Override
  protected void onRender(String data) {
    ComponentUILabel name = (ComponentUILabel) getFragment();
    name.controls.setText(data);
    name.setLayoutWeight(1);
  }

  @Override
  protected void onAttached() {

  }
}
