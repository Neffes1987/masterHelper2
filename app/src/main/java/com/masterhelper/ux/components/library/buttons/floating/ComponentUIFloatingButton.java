package com.masterhelper.ux.components.library.buttons.floating;


import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.ComponentUIFragment;


/**
 * fragment for working with Floating button
 */
public class ComponentUIFloatingButton extends ComponentUIFragment {
  private static final int BUTTON_ID = R.id.COMPONENT_FLOAT_BUTTON_ID;

  public FloatButton controls;

  public ComponentUIFloatingButton() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentView = inflater.inflate(R.layout.fragment_component_ui_floating_button, container, false);
    controls = new FloatButton(fragmentView.findViewById(BUTTON_ID));
    initControls(controls);
    return fragmentView;
  }

  /**
   * check that component is instance of current ui fragment
   * @param uiComponent - fragment ui for testing
   */
  public static ComponentUIFloatingButton cast(Fragment uiComponent) {
    if(uiComponent instanceof ComponentUIFloatingButton){
      return (ComponentUIFloatingButton) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUIFloatingButton");
  }

}
