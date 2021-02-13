package com.masterhelper.media.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.list.elements.SelectionControl;

public class FileSelection extends SelectionControl {
  SetBtnLocation listener;
  public FileSelection(View container, FragmentManager manager, String checkboxName, SetBtnLocation listener){
    super(container, manager, checkboxName);
    this.listener = listener;
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getCheckbox().setLayoutWeight(3);
    getCheckbox().controls.setExternalListener(v -> {
      listener.onClick(getCheckbox().getId(), getTag());
    });
  }
}
