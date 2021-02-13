package com.masterhelper.media.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.elements.LabelControl;

public class FileName extends LabelControl {
  public FileName(View container, FragmentManager manager){
    super(container, manager);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getLabel().setLayoutWeight(1);
  }
}
