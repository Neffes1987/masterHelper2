package com.masterhelper.ux.components.library.tickButton.radio;


import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.ComponentUIFragment;


/**
 * fragment for working with Radio group widget
 */
public class ComponentUIRadioGroup extends ComponentUIFragment {
  public static final int ID = R.id.COMPONENT_RADIOGROUP_ID;

  public RadioControlsGroup controls;

  private View fragmentView;


  public ComponentUIRadioGroup() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentView = inflater.inflate(R.layout.fragment_component_ui_radio_group, container, false);
    controls = new RadioControlsGroup(fragmentView.findViewById(ID));
    controls.setTag(this.getTag());
    if(pListener != null){
      pListener.onFragmentAttached(this.getTag());
    }
    return fragmentView;
  }

  /**
   * check that component is instance of current ui fragment
   * @param uiComponent - fragment ui for testing
   */
  public static ComponentUIRadioGroup cast(Fragment uiComponent) {
    if(uiComponent instanceof ComponentUIRadioGroup){
      return (ComponentUIRadioGroup) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUIRadioGroup");
  }

  @Override
  public void setOnAttachListener(OnAttachListener listener) {
    pListener = listener;
  }

  @Override
  public void setLayoutWeight(int weight) {
    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) fragmentView.getLayoutParams();
    params.weight = weight;
    fragmentView.setLayoutParams(params);
  }
}