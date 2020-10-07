package com.masterhelper.ux.components.library.text.label;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;


/**
 * Fragment for labels components
 */
public class ComponentUILabel extends Fragment {

  public static final int ID = R.id.COMPONENT_LABEL_ID;

  public Label controls;

  public ComponentUILabel() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_component_ui_label, container, false);
    this.controls = new Label(fragmentView.findViewById(ID));
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
}
