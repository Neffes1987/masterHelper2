package com.masterhelper.ux.components.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.library.Radio;


/**
 * fragment for working with Radio group widget
 */
public class ComponentUIRadioGroup extends Fragment {
  private static final int RADIOGROUP_ID = R.id.COMPONENT_RADIOGROUP_ID;

  public Radio controls;


  public ComponentUIRadioGroup() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fr = inflater.inflate(R.layout.fragment_component_ui_radio_group, container, false);
    controls = new Radio(fr.findViewById(RADIOGROUP_ID));
    return fr;
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

}
