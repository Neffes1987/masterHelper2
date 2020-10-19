package com.masterhelper.ux.pages.scenes.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.components.library.list.elements.LabelControl;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;

public class SceneName extends LabelControl {

  public SceneName(View container, FragmentManager manager){
    super(container, manager);
  }

  @Override
  protected void onRender(String data) {
    getLabel().controls.setText(data);

  }

  @Override
  protected void onAttached() {
    super.onAttached();
    getLabel().setLayoutWeight(1);
  }
}
