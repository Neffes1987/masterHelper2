package com.masterhelper.ux.components.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.library.InputTextField;


/**
 * Fragment for working with edit text fields
 */
public class ComponentUIInputText extends Fragment {
  private static final int INPUT_TEXT_ID = R.id.COMPONENT_INPUT_TEXT_ID;

  public InputTextField controls;

  public ComponentUIInputText() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View fragment = inflater.inflate(R.layout.fragment_component_ui_input_text, container, false);
    controls = new InputTextField(fragment.findViewById(INPUT_TEXT_ID));
    return fragment;
  }

  /**
   * check that component is instance of current ui fragment
   * @param uiComponent - fragment ui for testing
   */
  public static ComponentUIInputText cast(Fragment uiComponent) {
    if(uiComponent instanceof ComponentUIInputText){
      return (ComponentUIInputText) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUIInputText");
  }
}
