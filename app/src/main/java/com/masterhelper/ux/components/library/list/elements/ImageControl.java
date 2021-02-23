package com.masterhelper.ux.components.library.list.elements;

import android.util.Log;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.image.ComponentUIImage;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;

import java.io.File;

public class ImageControl extends CommonListItemElement<String> {
  ComponentUIImage image;

  public ComponentUIImage getImage() {
    return image;
  }

  public ImageControl(View container, FragmentManager manager) {
    init(container, manager);
  }

  @Override
  protected void init(View container, FragmentManager manager) {
    setFragment(new ComponentUIImage());
    attach(container, manager);
  }

  @Override
  protected void onRender(String data) {
    image.controls.setFile(new File(data));
  }

  @Override
  protected void onAttached() {
    image = (ComponentUIImage) getFragment();
    image.setLayoutWidth(60);
    image.setLayoutHeight(60);
  }
}
