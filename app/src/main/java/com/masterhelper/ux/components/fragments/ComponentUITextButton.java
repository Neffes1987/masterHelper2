package com.masterhelper.ux.components.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.library.TextButton;


/**
 * fragment for working with plain button
 */
public class ComponentUITextButton extends Fragment {
  private static final int BUTTON_ID = R.id.COMPONENT_TEXT_BUTTON_ID;

  public TextButton controls;

  public ComponentUITextButton() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fr = inflater.inflate(R.layout.fragment_component_ui_text_button, container, false);
    controls = new TextButton(fr.findViewById(BUTTON_ID));
    return fr;
  }

  /**
   * check that component is instance of current ui fragment
   * @param uiComponent - fragment ui for testing
   */
  public static ComponentUITextButton cast(Fragment uiComponent) {
    if(uiComponent instanceof ComponentUITextButton){
      return (ComponentUITextButton) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUITextButton");
  }

}
