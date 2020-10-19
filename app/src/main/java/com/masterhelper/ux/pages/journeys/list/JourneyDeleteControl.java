package com.masterhelper.ux.pages.journeys.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.elements.ButtonControl;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

public class JourneyDeleteControl extends ButtonControl {


  public JourneyDeleteControl(View container, FragmentManager manager, SetBtnEvent event) {
    super(container, manager, event);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getBtn().controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.clear));
    getBtn().controls.setIconColor(ResourceColors.ResourceColorType.alert);
  }
}
