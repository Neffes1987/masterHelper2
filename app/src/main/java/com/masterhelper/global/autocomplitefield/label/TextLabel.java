package com.masterhelper.global.autocomplitefield.label;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class TextLabel {
  TextView label;

  public TextLabel(int textFieldId, Activity context) {
    label = context.findViewById(textFieldId);
  }

  public void hide() {
    label.setVisibility(View.GONE);
  }

  public void show() {
    label.setVisibility(View.VISIBLE);
  }


  void parseText(String labelText) {

  }
}
