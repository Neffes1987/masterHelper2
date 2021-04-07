package com.masterhelper.global.autocomplitefield.editField;

import android.app.Activity;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import static android.text.InputType.*;

public class EditTextField {
  EditText textField;

  public EditTextField(int textFieldId, Activity context) {
    if (textFieldId != 0) {
      textField = context.findViewById(textFieldId);
    } else {
      textField = new EditText(context);
    }
  }

  public void setMultiLIneText() {
    textField.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_FLAG_MULTI_LINE | TYPE_TEXT_FLAG_CAP_SENTENCES);
  }

  public void setMaxLength(int maxLength) {
    textField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
  }

  public void setText(String text) {
    textField.setText(text);
  }

  public String getText() {
    return textField.getText().toString();
  }

  public EditText getTextField() {
    return textField;
  }

  public void hide() {
    textField.setVisibility(View.GONE);
  }

  public void show() {
    textField.setVisibility(View.VISIBLE);
  }
}
