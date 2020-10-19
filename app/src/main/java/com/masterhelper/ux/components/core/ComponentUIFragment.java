package com.masterhelper.ux.components.core;

import androidx.fragment.app.Fragment;


/**
 * Fragment for labels components
 */
public abstract class ComponentUIFragment extends Fragment {
  protected OnAttachListener pListener;

  public abstract void setOnAttachListener(OnAttachListener listener);

  public interface OnAttachListener{
    void onFragmentAttached();
  }

  public abstract void setLayoutWeight(int weight);
}
