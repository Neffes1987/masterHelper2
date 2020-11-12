package com.masterhelper.ux.media.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.elements.LabelControl;
import com.masterhelper.ux.components.library.list.elements.SelectionControl;
import com.masterhelper.ux.components.library.tickButton.check.CheckBoxesGroup;

public class FileSelection extends SelectionControl {
  SetBtnEvent listener;
  public FileSelection(View container, FragmentManager manager, String checkboxName, SetBtnEvent listener){
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
