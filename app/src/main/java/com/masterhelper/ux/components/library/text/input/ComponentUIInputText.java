package com.masterhelper.ux.components.library.text.input;


import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.ComponentUIFragment;


/**
 * Fragment for working with edit text fields
 */
public class ComponentUIInputText extends ComponentUIFragment {
  public static final int ID = R.id.COMPONENT_INPUT_TEXT_ID;

  public InputTextField controls;
  private View fragmentView;

  public ComponentUIInputText() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    fragmentView = inflater.inflate(R.layout.fragment_component_ui_input_text, container, false);
    controls = new InputTextField(fragmentView.findViewById(ID));
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
  public static ComponentUIInputText cast(Fragment uiComponent) {
    if(uiComponent instanceof ComponentUIInputText){
      return (ComponentUIInputText) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUIInputText");
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
