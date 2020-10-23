package com.masterhelper.ux.components.library.image;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.ComponentUIFragment;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.text.input.ComponentUIInputText;
import com.masterhelper.ux.components.library.text.input.InputTextField;

/**  */
public class ComponentUIImage extends ComponentUIFragment {
  public static final int ID = R.id.COMPONENT_IMAGE_BUTTON_ID;
  public Image controls;

  public ComponentUIImage() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    fragmentView = inflater.inflate(R.layout.fragment_component_ui_image, container, false);
    controls = new Image(fragmentView.findViewById(ID));
    initControls(controls);
    return fragmentView;
  }

  /**
   * check that component is instance of current ui fragment
   * @param uiComponent - fragment ui for testing
   */
  public static ComponentUIImage cast(Fragment uiComponent) {
    if(uiComponent instanceof ComponentUIImage){
      return (ComponentUIImage) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUIImage");
  }
}