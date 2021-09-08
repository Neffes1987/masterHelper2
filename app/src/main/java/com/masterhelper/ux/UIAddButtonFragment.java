package com.masterhelper.ux;

import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;

public class UIAddButtonFragment extends Fragment {
  Button addButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fragment = inflater.inflate(R.layout.fragment_ui_add_button, container, false);

    addButton = fragment.findViewById(R.id.ADD_BUTTON_ID);

    return fragment;
  }

  public void setListener(View.OnClickListener listener) {
    addButton.setOnClickListener(listener);
  }
}