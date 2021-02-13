package com.masterhelper.locations.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.list.elements.ButtonControl;
import com.masterhelper.ux.resources.ResourceIcons;

public class LocationEditControl extends ButtonControl {

  public LocationEditControl(View container, FragmentManager manager, SetBtnLocation event){
    super(container, manager, event);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getBtn().controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.pencil));
    getBtn().setLayoutWeight(3);
  }
}