package com.masterhelper.ux.pages.scenes.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

public class SceneDeleteControl extends CommonListItemElement<SetBtnEvent> {
  ComponentUIImageButton deleteButton;

  public SceneDeleteControl(View container, FragmentManager manager, SetBtnEvent event){
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
    deleteButton.controls.setOnClick(event);
  }

  @Override
  protected void onAttached() {
    deleteButton = (ComponentUIImageButton) getFragment();
    deleteButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.clear));
    deleteButton.controls.setIconColor(ResourceColors.ResourceColorType.alert);
  }
}
