package com.masterhelper.ux.pages.events.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.elements.ButtonControl;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

public class EventExpandBtn extends ButtonControl {
  private boolean isExpanded = false;

  public void toggleCurrentState() {
    this.isExpanded = !this.isExpanded;
  }

  public boolean getExpanded(){
    return isExpanded;
  }

  public EventExpandBtn(View container, FragmentManager manager, SetBtnEvent event){
    super(container, manager, event);
  }

  public void setOrientation(boolean isOpen){
    if(isOpen){
      getBtn().controls.setIconColor(ResourceColors.ResourceColorType.alert);
      getBtn().controls.setRotation(180);
      return;
    }
    getBtn().controls.setRotation(0);
    getBtn().controls.setIconColor(ResourceColors.ResourceColorType.primary);
  }

  @Override
  protected void onRender(SetBtnEvent event) {
    getBtn().controls.setOnClick(new SetBtnEvent() {
      @Override
      public void onClick(int btnId, String tag) {
        toggleCurrentState();
        setOrientation(getExpanded());
        event.onClick(btnId, tag);
      }

      @Override
      public void onLongClick(int btnId) {

      }
    });
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getBtn().controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.filter));
    getBtn().setLayoutWeight(5);
  }
}
