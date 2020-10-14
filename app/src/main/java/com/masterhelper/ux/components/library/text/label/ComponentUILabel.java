package com.masterhelper.ux.components.library.text.label;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.ComponentUIFragment;


/**
 * Fragment for labels components
 */
public class ComponentUILabel extends ComponentUIFragment {

  public static final int ID = R.id.COMPONENT_LABEL_ID;

  public Label controls;
  public View fragmentView;

  public ComponentUILabel() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    fragmentView = inflater.inflate(R.layout.fragment_component_ui_label, container, false);
    this.controls = new Label(fragmentView.findViewById(ID));
    controls.setTag(this.getTag());
    if(pListener != null){
      pListener.onFragmentAttached();
    }
    return fragmentView;
  }



  /**
   * check that component is instance of current ui fragment
   * @param uiComponent - fragment ui for testing
   */
  public static ComponentUILabel cast(Fragment uiComponent) {
    if(uiComponent instanceof ComponentUILabel){
      return (ComponentUILabel) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUILabel");
  }

  @Override
  public void setOnAttachListener(OnAttachListener listener) {
    pListener = listener;
  }

  @Override
  public void setLayoutWeight(int weight) {
    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) fragmentView.getLayoutParams();
    params.weight = 1;
    params.width = LinearLayout.LayoutParams.MATCH_PARENT;
    params.height = LinearLayout.LayoutParams.MATCH_PARENT;
    fragmentView.setLayoutParams(params);
  }
}
