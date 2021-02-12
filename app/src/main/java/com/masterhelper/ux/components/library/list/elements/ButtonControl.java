package com.masterhelper.ux.components.library.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.list.CommonListItemElement;

public class ButtonControl extends CommonListItemElement<SetBtnLocation> {

  private ComponentUIImageButton btn;

  public ComponentUIImageButton getBtn() {
    return btn;
  }

  public ButtonControl(View container, FragmentManager manager, SetBtnLocation event){
    init(container, manager);
    setElementData(event);
  }

  @Override
  protected void init(View container, FragmentManager manager) {
    setFragment(new ComponentUIImageButton());
    attach(container, manager);
  }

  @Override
  protected void onRender(SetBtnLocation event) {
    btn.controls.setOnClick(event);
  }

  @Override
  protected void onAttached() {
    btn = (ComponentUIImageButton) getFragment();
    btn.controls.setId(View.generateViewId());
  }

}
