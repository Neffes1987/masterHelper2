package com.masterhelper.ux.pages.journeys.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

public class JourneyDeleteControl extends CommonListItemElement<SetBtnEvent> {

  public JourneyDeleteControl(View container, FragmentManager manager, SetBtnEvent event){
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
    ComponentUIImageButton deleteButton = (ComponentUIImageButton) getFragment();
    deleteButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.clear));
    deleteButton.controls.setOnClick(event);
    deleteButton.controls.setIconColor(ResourceColors.ResourceColorType.alert);
  }
}
