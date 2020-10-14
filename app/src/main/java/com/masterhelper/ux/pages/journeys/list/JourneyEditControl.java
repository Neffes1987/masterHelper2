package com.masterhelper.ux.pages.journeys.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;

public class JourneyEditControl extends CommonListItemElement<SetBtnEvent> {

  public JourneyEditControl(View container, FragmentManager manager, SetBtnEvent event){
    init(container, manager);
    setElementData(event);
  }

  @Override
  protected void init(View container, FragmentManager manager) {
    setFragment(new ComponentUIImageButton());
    attach(container, manager);
  }

  @Override
  protected void render(SetBtnEvent event) {
    ComponentUIImageButton btn = (ComponentUIImageButton) getFragment();
    btn.controls.setOnClick(event);
    btn.controls.setId(View.generateViewId());
  }
}
