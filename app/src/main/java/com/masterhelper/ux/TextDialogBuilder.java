package com.masterhelper.ux;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.masterhelper.R;

public class TextDialogBuilder {
  AlertDialog.Builder builder;
  EditText name;
  TextView title;
  Button positive;
  Button negative;

  public TextDialogBuilder(Activity pageActivity) {
    builder = new AlertDialog.Builder(pageActivity);
    View dialogView = pageActivity.getLayoutInflater().inflate(R.layout.dialog_input_layout, null);
    name = dialogView.findViewById(R.id.DIALOG_INPUT_ID);
    title = dialogView.findViewById(R.id.DIALOG_TITLE_ID);
    positive = dialogView.findViewById(R.id.DIALOG_POSITIVE_BUTTON_ID);
    positive.setText(R.string.apply);

    negative = dialogView.findViewById(R.id.DIALOG_NEGATIVE_BUTTON_ID);
    negative.setText(R.string.cancel);

    builder.setView(dialogView);
  }

  public AlertDialog create() {
    AlertDialog dialog = builder.create();
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    return dialog;
  }

  public TextDialogBuilder setPositiveButton(View.OnClickListener listener) {
    positive.setOnClickListener(listener);
    return this;
  }

  public TextDialogBuilder setNegativeButton(View.OnClickListener listener) {
    negative.setOnClickListener(listener);
    return this;
  }

  public TextDialogBuilder setTitle(int titleTextResId) {
    title.setText(titleTextResId);
    return this;
  }

  public TextDialogBuilder setTitle(String title) {
    this.title.setText(title);
    return this;
  }

  public TextDialogBuilder setInputValue(String value) {
    name.setText(value);
    return this;
  }
}
