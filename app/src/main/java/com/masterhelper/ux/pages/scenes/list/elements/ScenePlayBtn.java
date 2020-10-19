package com.masterhelper.ux.pages.scenes.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.elements.ButtonControl;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

public class ScenePlayBtn extends ButtonControl {
  public ScenePlayBtn(View container, FragmentManager manager, SetBtnEvent event){
    super(container, manager, event);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getBtn().controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.play));
    getBtn().controls.setIconColor(ResourceColors.ResourceColorType.primary);
    getBtn().setLayoutWeight(2);
  }
}
