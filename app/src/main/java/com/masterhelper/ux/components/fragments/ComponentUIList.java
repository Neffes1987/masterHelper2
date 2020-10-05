package com.masterhelper.ux.components.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.library.list.ComponentList;


/**
 * Fragment for working with recycle list
 */
public class ComponentUIList<DataModel> extends Fragment {
  public static final int COMPONENT_LIST_ID = R.id.COMPONENT_LIST_ID;
  public ComponentList<DataModel> controls;

  public ComponentUIList() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fr = inflater.inflate(R.layout.fragment_component_ui_list, container, false);
    controls = new ComponentList<>(fr.findViewById(COMPONENT_LIST_ID));
    return fr;
  }

}
