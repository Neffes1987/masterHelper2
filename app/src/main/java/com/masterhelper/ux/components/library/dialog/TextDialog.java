package com.masterhelper.ux.components.library.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class TextDialog {
  AlertDialog.Builder builder;
  EditText name;

  public TextDialog(Activity pageActivity, String dialogTitle, int textLength, String defaultValue, DialogClickListener listener) {
    builder = new AlertDialog.Builder(pageActivity);
    builder.setTitle("Title");

    name = new EditText(pageActivity);

    name.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
    name.setText(defaultValue);

    if (textLength > 0) {
      name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textLength)});
    }

    builder.setView(name);
    builder.setTitle(dialogTitle);
    builder.setPositiveButton("OK", (dialog, which) -> listener.onResolve(name.getText().toString()));
    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
  }

  public void show() {
    builder.show();
  }

  public void alert() {
    name.setVisibility(View.GONE);
    builder.show();
  }
}
