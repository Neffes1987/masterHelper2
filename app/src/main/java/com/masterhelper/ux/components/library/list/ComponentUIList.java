package com.masterhelper.ux.components.library.list;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.masterhelper.R;


/**
 * Fragment for working with recycle list
 */
public class ComponentUIList extends Fragment {
  public static final int COMPONENT_LIST_ID = R.id.COMPONENT_LIST_ID;
  public ComponentList controls;

  public ComponentUIList() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View fr = inflater.inflate(R.layout.fragment_component_ui_list, container, false);
    RecyclerView list = fr.findViewById(COMPONENT_LIST_ID);
    controls = new ComponentList(list);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

    list.setLayoutManager(layoutManager);
    list.setHasFixedSize(true);

    return fr;
  }

  /**
   * check that component is instance of current ui fragment
   *
   * @param uiComponent - fragment ui for testing
   */
  public static ComponentUIList cast(Fragment uiComponent) {
    if (uiComponent instanceof ComponentUIList) {
      return (ComponentUIList) uiComponent;
    }
    throw new Error(uiComponent + " does not a ComponentUIList");
  }

}
