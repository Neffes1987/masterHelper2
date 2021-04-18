package com.masterhelper.global.autocomplitefield.editField;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import com.masterhelper.global.autocomplitefield.repository.AutoFillRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.InputType.*;

public class MultiAutofillEditTextField implements TextWatcher {
  MultiAutoCompleteTextView textField;
  int THRESHOLD = 2;
  int currentTypedTokenIndex = -1;
  static String TOKEN = "@";

  public static ArrayList<String> findTokensText(String text) {
    ArrayList<String> tokensList = new ArrayList<>();
    String regularExpression = "@.+?\\[\\S+?\\]{1}";
    Pattern pattern = Pattern.compile(regularExpression);
    Matcher match = pattern.matcher(text);
    while (match.find()) {
      String token = text.substring(match.start(), match.end());
      tokensList.add(token);
    }
    return tokensList;
  }

  public static ArrayList<String> findTokensNames(String text) {
    ArrayList<String> tokensNamesList = new ArrayList<>();
    String regularExpression = "@.+?(?=\\[\\S+?\\]{1})";
    Pattern pattern = Pattern.compile(regularExpression);
    Matcher match = pattern.matcher(text);
    while (match.find()) {
      String token = text.substring(match.start(), match.end());
      tokensNamesList.add(token.replace(TOKEN, ""));
    }
    return tokensNamesList;
  }

  public static String getTokenId(String inputToken) {
    String regularExpression = "@.+?\\[+?";
    Pattern pattern = Pattern.compile(regularExpression);
    Matcher match = pattern.matcher(inputToken);
    if (match.find()) {
      String matching = inputToken.substring(match.start(), match.end());
      return inputToken.replace(matching, "").replace("]", "").trim();
    }
    return "";
  }

  public MultiAutofillEditTextField(int textFieldId, Activity context, int maxTextLength) {
    if (textFieldId != 0) {
      textField = context.findViewById(textFieldId);
    } else {
      textField = new MultiAutoCompleteTextView(context);
    }
    textField.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_FLAG_MULTI_LINE | TYPE_TEXT_FLAG_CAP_SENTENCES);
    textField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxTextLength)});
    withAutofill();
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
    textField.setTokenizer(new EditTextAutoFillToken(TOKEN));
    textField.addTextChangedListener(this);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    if (s.length() == 0) {
      textField.setAdapter(null);
    }
    final String tokenQuery = accumulateToken(s, start, before > 0);
    generateAutofillList(tokenQuery);
  }

  @Override
  public void afterTextChanged(Editable s) {

  }

  private String accumulateToken(CharSequence typedChar, int start, boolean isErase) {
    if (typedChar.length() <= start) {
      return "";
    }
    String typedString = typedChar.toString();
    String lastTypedChar = typedString.charAt(start) + "";
    boolean isTokenEnded = (lastTypedChar.contains("\r") || lastTypedChar.contains("\n") || lastTypedChar.contains(" ")) && !isErase;

    if (lastTypedChar.contains(TOKEN)) {
      currentTypedTokenIndex = start;
    } else if (isTokenEnded) {
      currentTypedTokenIndex = -1;
    }

    if (currentTypedTokenIndex == -1 || start - currentTypedTokenIndex <= 0) {
      return "";
    }

    return typedString.substring(currentTypedTokenIndex, start).replace(TOKEN, "");
  }

  private void generateAutofillList(String tokenQuery) {
    if (tokenQuery.length() == 0 || tokenQuery.length() < THRESHOLD) {
      textField.setAdapter(null);
      return;
    }
    HashMap<String, String> cache = AutoFillRepository.getAutofillList(tokenQuery, THRESHOLD);
    ArrayList<String> list = new ArrayList<>();
    for (String id : cache.keySet()) {
      list.add(TOKEN + Objects.requireNonNull(cache.get(id)).replace("\\s", "") + "[" + id + "]");
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<>(textField.getContext(), android.R.layout.simple_dropdown_item_1line, list);
    textField.setAdapter(adapter);
  }
}
