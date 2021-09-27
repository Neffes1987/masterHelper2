package com.masterhelper.ux;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.masterhelper.R;
import org.jetbrains.annotations.Nullable;

import static android.text.InputType.*;

public class TextDialogBuilder {
  AlertDialog.Builder builder;
  EditText name;
  TextView title;
  Button positive;
  Button negative;
  AlertDialog dialog;

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
    dialog = builder.create();
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    return dialog;
  }

  public TextDialogBuilder setControlsButton(View.OnClickListener positiveListener, @Nullable View.OnClickListener negativeListener) {
    this.positive.setOnClickListener(v -> {
      positiveListener.onClick(v);

      dialog.dismiss();
    });

    this.negative.setOnClickListener(v -> {
      if (negativeListener != null) {
        negativeListener.onClick(v);
      }

      dialog.dismiss();
    });
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

  public String getValue() {
    return name.getText().toString();
  }

  public TextDialogBuilder setValue(String value) {
    name.setText(value);
    return this;
  }

  public TextDialogBuilder setInputValue(String value) {
    name.setText(value);
    return this;
  }

  public void setMultiLIneText() {
    name.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_FLAG_MULTI_LINE | TYPE_TEXT_FLAG_CAP_SENTENCES);
  }

  public void setNumeric() {
    name.setInputType(TYPE_CLASS_PHONE);
  }

  public void setMaxLength(int maxLength) {
    name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
  }
}
