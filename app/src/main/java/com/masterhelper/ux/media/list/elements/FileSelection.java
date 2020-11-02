package com.masterhelper.ux.media.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.elements.LabelControl;
import com.masterhelper.ux.components.library.list.elements.SelectionControl;
import com.masterhelper.ux.components.library.tickButton.check.CheckBoxesGroup;

public class FileSelection extends SelectionControl {
  public FileSelection(View container, FragmentManager manager, String checkboxName){
    super(container, manager, checkboxName);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getCheckbox().setLayoutWeight(3);
  }
}
