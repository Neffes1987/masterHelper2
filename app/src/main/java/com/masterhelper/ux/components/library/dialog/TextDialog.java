package com.masterhelper.ux.components.library.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import com.masterhelper.global.autocomplitefield.editField.EditTextField;

public class TextDialog {
  AlertDialog.Builder builder;
  EditTextField name;

  public TextDialog(Activity pageActivity, String dialogTitle, int textLength, String defaultValue, DialogClickListener listener) {
    builder = new AlertDialog.Builder(pageActivity);
    name = new EditTextField(0, pageActivity);

    name.setMultiLIneText();
    name.setText(defaultValue);

    if (textLength > 0) {
      name.setMaxLength(textLength);
    }

    builder.setView(name.getTextField());
    builder.setTitle(dialogTitle);
    builder.setPositiveButton("OK", (dialog, which) -> listener.onResolve(name.getText().toString()));
    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
  }

  public void show() {
    builder.show();
  }

  public void alert() {
    name.hide();
    builder.show();
  }
}
