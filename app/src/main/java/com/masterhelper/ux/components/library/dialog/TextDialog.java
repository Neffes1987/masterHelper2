package com.masterhelper.ux.components.library.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import static android.text.InputType.*;

public class TextDialog {
  AlertDialog.Builder builder;
  EditText name;

  public TextDialog(Activity pageActivity, String dialogTitle, int textLength, String defaultValue, DialogClickListener listener) {
    builder = new AlertDialog.Builder(pageActivity);
    builder.setTitle("Title");

    name = new EditText(pageActivity);

    name.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_POSTAL_ADDRESS | TYPE_TEXT_FLAG_MULTI_LINE);
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
