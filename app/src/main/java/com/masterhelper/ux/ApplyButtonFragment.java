package com.masterhelper.ux;

import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;

public class ApplyButtonFragment extends Fragment {
  Button applyButtonView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View fragment = inflater.inflate(R.layout.fragment_apply_button, container, false);
    applyButtonView = fragment.findViewById(R.id.APPLY_BUTTON_ID);

    return fragment;
  }

  public void setTitle(int title) {
    applyButtonView.setTag(title);
  }

  public void setListener(View.OnClickListener listener) {
    applyButtonView.setOnClickListener(listener);
  }
}