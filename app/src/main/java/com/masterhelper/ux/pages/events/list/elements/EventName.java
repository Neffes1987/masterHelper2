package com.masterhelper.ux.pages.events.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.elements.LabelControl;

public class EventName extends LabelControl {
  public EventName(View container, FragmentManager manager){
    super(container, manager);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getLabel().setLayoutWeight(1);
  }
}
