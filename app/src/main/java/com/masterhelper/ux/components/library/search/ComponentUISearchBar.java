package com.masterhelper.ux.components.library.search;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class ComponentUISearchBar extends Fragment {
  int TextFieldId = R.id.COMPONENT_SEARCH_TEXT_FIELD_ID;
  int ClearBtnId = R.id.COMPONENT_SEARCH_CLEAR_ID;

  ISearchBar searchBarParentActivity;
  InputMethodManager imm;

  public ComponentUISearchBar() {
    // Required empty public constructor
  }

  @Override
  public void onAttach(@NonNull @NotNull Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ISearchBar) {
      searchBarParentActivity = (ISearchBar) activity;
      imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    } else {
      throw new Error("Activity should implements ISearchBar");
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_component_ui_search_bar, container, false);
    EditText textField = view.findViewById(TextFieldId);
    textField.setOnKeyListener((v, keyCode, event) -> {
      boolean isKeyDown = event.getAction() != KeyEvent.ACTION_DOWN;
      boolean isEnterPressed = keyCode == KeyEvent.KEYCODE_ENTER;

      if (isKeyDown && isEnterPressed) {
        searchBarParentActivity.doSearch(textField.getText().toString().toLowerCase());
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return false;
      }
      return true;
    });

    ImageButton button = view.findViewById(ClearBtnId);
    button.setOnClickListener(v -> {
      textField.setText("");
      searchBarParentActivity.doSearch(null);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    });
    return view;
  }
}