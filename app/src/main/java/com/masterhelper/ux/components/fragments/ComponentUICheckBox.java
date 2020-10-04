package com.masterhelper.ux.components.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.library.Check;


/**
 * fragment for working with Checkbox buttons
 */
public class ComponentUICheckBox extends Fragment {
  private static final int CHECKBOX_ID = R.id.COMPONENT_CHEKBOX_ID;

  public Check controls;

  public ComponentUICheckBox() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fr = inflater.inflate(R.layout.fragment_component_ui_check, container, false);
    controls = new Check(fr.findViewById(CHECKBOX_ID));
    return fr;
  }

  /**
   * check that component is instance of current ui fragment
   * @param uiComponent - fragment ui for testing
   */
  public static ComponentUICheckBox cast(Fragment uiComponent) {
    if(uiComponent instanceof ComponentUICheckBox){
      return (ComponentUICheckBox) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUICheckBox");
  }

}
