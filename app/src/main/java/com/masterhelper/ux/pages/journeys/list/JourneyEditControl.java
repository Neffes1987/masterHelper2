package com.masterhelper.ux.pages.journeys.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.ComponentUIFragment;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;

public class JourneyEditControl extends CommonListItemElement<SetBtnEvent> {

  private ComponentUIImageButton btn;

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
  protected void onRender(SetBtnEvent event) {
    btn.controls.setOnClick(event);
  }

  @Override
  protected void onAttached() {
    btn = (ComponentUIImageButton) getFragment();
    btn.controls.setId(View.generateViewId());
  }
}
