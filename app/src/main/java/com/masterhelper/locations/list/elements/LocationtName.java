package com.masterhelper.locations.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.elements.LabelControl;

public class LocationtName extends LabelControl {
  public LocationtName(View container, FragmentManager manager){
    super(container, manager);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getLabel().setLayoutWeight(1);
  }
}
