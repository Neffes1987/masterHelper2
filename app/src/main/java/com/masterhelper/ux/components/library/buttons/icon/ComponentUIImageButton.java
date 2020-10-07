package com.masterhelper.ux.components.library.buttons.icon;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;


/**
 * fragment for working with image button
 */
public class ComponentUIImageButton extends Fragment {
  private static final int BUTTON_ID = R.id.COMPONENT_IMAGE_BUTTON_ID;

  public IconButton controls;

  public ComponentUIImageButton() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fr = inflater.inflate(R.layout.fragment_component_ui_image_button, container, false);
    controls = new IconButton(fr.findViewById(BUTTON_ID));
    return fr;
  }

  /**
   * check that component is instance of current ui fragment
   * @param uiComponent - fragment ui for testing
   */
  public static ComponentUIImageButton cast(Fragment uiComponent) {
    if(uiComponent instanceof ComponentUIImageButton){
      return (ComponentUIImageButton) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUIImageButton");
  }
}
