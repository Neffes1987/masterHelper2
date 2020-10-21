package com.masterhelper.ux.components.core;

import android.view.View;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;


/**
 * Fragment for labels components
 */
public abstract class ComponentUIFragment extends Fragment {
  protected OnAttachListener pListener;
  protected View fragmentView;

  public void setOnAttachListener(OnAttachListener listener){
    this.pListener = listener;
  }

  public interface OnAttachListener{
    void onFragmentAttached();
  }

  protected <Type extends View> void initControls(UXElement<Type> controls){
    controls.setTag(this.getTag());
    if(pListener != null){
      pListener.onFragmentAttached();
    }
  }

  public void setLayoutWeight(int weight){
    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) fragmentView.getLayoutParams();
    params.weight = weight;
    fragmentView.setLayoutParams(params);
  }
}
