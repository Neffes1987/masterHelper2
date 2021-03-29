package com.masterhelper.global.autocomplitefield.editField;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import com.masterhelper.global.autocomplitefield.repository.AutoFillRepository;

import java.util.ArrayList;
import java.util.HashMap;

import static android.text.InputType.*;

public class EditTextField implements TextWatcher {

  MultiAutoCompleteTextView textField;
  int THRESHOLD = 2;
  int TOKEN_OFFSET = 1;
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

  public void withAutofill() {
    textField.setThreshold(THRESHOLD);
    textField.setTokenizer(new EditTextAutoFillToken(" "));
    textField.addTextChangedListener(this);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    String typedString = s.toString();

    final int lastTokenIndex = typedString.lastIndexOf(TOKEN);
    final int lastSeparatorIndex = typedString.lastIndexOf(" ");
    final int typedTextLen = typedString.length();
    if (lastTokenIndex < 0 || lastSeparatorIndex > lastTokenIndex || typedTextLen - TOKEN_OFFSET < THRESHOLD) {
      return;
    }

    String lastTokenQueryRange = typedString.substring(lastTokenIndex + TOKEN_OFFSET);

    boolean isAutofillInRange = typedTextLen - lastTokenIndex >= THRESHOLD;

    if (isAutofillInRange) {
      HashMap<String, String> cache = AutoFillRepository.getAutofillList(lastTokenQueryRange, THRESHOLD);

      ArrayList<String> list = new ArrayList<>();
      for (String id : cache.keySet()) {
        list.add(TOKEN + cache.get(id) + "[" + id + "]");
      }

      ArrayAdapter<String> adapter = new ArrayAdapter<>(textField.getContext(), android.R.layout.simple_dropdown_item_1line, list);
      textField.setAdapter(adapter);
    }
  }

  @Override
  public void afterTextChanged(Editable s) {

  }
}
