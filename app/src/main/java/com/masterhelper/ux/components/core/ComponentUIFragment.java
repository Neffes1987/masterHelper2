package com.masterhelper.ux.components.core;

import androidx.fragment.app.Fragment;
import com.masterhelper.ux.components.library.text.label.Label;


/**
 * Fragment for labels components
 */
public abstract class ComponentUIFragment extends Fragment {
  protected OnAttachListener pListener;

  public Label controls;

  public abstract void setOnAttachListener(OnAttachListener listener);

  public interface OnAttachListener{
    void onFragmentAttached(String tag);
  }

  public abstract void setLayoutWeight(int weight);
}
