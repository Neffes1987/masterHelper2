package com.masterhelper.ux.pages.scenes.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.components.library.list.elements.ButtonControl;
import com.masterhelper.ux.resources.ResourceIcons;

public class SceneEditBtn extends ButtonControl {
  public SceneEditBtn(View container, FragmentManager manager, SetBtnEvent event){
    super(container, manager, event);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getBtn().controls.setId(View.generateViewId());
    getBtn().controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.filter));
  }
}
