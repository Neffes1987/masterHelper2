package com.masterhelper.ux.components.library.list.elements;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.library.list.CommonListItemElement;
import com.masterhelper.ux.components.library.progressBar.ComponentUIProgressBar;

public class ProgressControl extends CommonListItemElement<Integer> {

  private ComponentUIProgressBar bar;
  private int maxValue = 0;

  public void setMaxValue(int maxValue) {
    this.maxValue = maxValue;
  }

  public int getMaxValue() {
    return maxValue;
  }

  public ComponentUIProgressBar getBar() {
    return bar;
  }

  public ProgressControl(View container, FragmentManager manager){
    init(container, manager);
  }

  @Override
  protected void init(View container, FragmentManager manager) {
    setFragment(new ComponentUIProgressBar());
    attach(container, manager);
  }

  @Override
  protected void onRender(Integer currentValue) {
    bar.controls.setProgress(currentValue);
  }

  @Override
  protected void onAttached() {
    bar = (ComponentUIProgressBar) getFragment();
    bar.controls.setId(View.generateViewId());
    bar.controls.setMax(getMaxValue());
  }
}
