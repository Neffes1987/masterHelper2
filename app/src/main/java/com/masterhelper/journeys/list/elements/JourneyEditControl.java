package com.masterhelper.journeys.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.elements.ButtonControl;
import com.masterhelper.ux.resources.ResourceIcons;

public class JourneyEditControl extends ButtonControl {

  public JourneyEditControl(View container, FragmentManager manager, SetBtnEvent event){
    super(container, manager, event);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getBtn().controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.pencil));
    getBtn().setLayoutWeight(3);
  }
}