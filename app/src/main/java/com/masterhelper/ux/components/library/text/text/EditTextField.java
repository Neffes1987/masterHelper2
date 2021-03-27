package com.masterhelper.ux.components.library.text.text;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.text.InputType.*;

public class EditTextField {
  MultiAutoCompleteTextView textField;
  int THRESHOLD = 1;
  String TOKEN = "@";

  public EditTextField(int textFieldId, Activity context) {
    if (textFieldId != 0) {
      textField = context.findViewById(textFieldId);
    } else {
      textField = new MultiAutoCompleteTextView(context);
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

  public void setAutoFillList(HashMap<String, String> autoCompleteList, Activity context) {
    ArrayList<String> list = new ArrayList<>();
    for (String id : autoCompleteList.keySet()) {
      list.add(TOKEN + autoCompleteList.get(id) + "[" + id + "]");
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, list);
    textField.setThreshold(THRESHOLD);
    textField.setAdapter(adapter);
    textField.setTokenizer(new EditTextAutoFillToken(" "));
  }
}
