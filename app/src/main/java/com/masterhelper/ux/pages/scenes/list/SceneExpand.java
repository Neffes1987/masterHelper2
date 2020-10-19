package com.masterhelper.ux.pages.scenes.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.resources.ResourceIcons;

public class SceneExpand extends CommonListItemElement<SetBtnEvent> {
  ComponentUIImageButton btn;
  public SceneExpand(View container, FragmentManager manager, SetBtnEvent event){
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
    btn.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.filter));
  }
}
