package com.masterhelper.media.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.list.elements.ButtonControl;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

public class FileDeleteControl extends ButtonControl {


  public FileDeleteControl(View container, FragmentManager manager, SetBtnLocation event) {
    super(container, manager, event);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getBtn().controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.clear));
    getBtn().controls.setIconColor(ResourceColors.ResourceColorType.alert);
    getBtn().setLayoutWeight(3);
  }
}
