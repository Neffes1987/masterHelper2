package com.masterhelper.ux.pages.scenes.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.elements.LabelControl;
import com.masterhelper.ux.pages.scenes.SceneLocale;

public class SceneScriptsDescription extends LabelControl {

  public SceneScriptsDescription(View container, FragmentManager manager){
    super(container, manager);
  }

  @Override
  protected void onRender(String data) {
    getLabel().controls.setText(SceneLocale.getLocalizationByKey(SceneLocale.Keys.scriptsProgressTitle));
    getLabel().valueControls.setText(data);
  }

  @Override
  protected void onAttached() {
    super.onAttached();
  }
}
