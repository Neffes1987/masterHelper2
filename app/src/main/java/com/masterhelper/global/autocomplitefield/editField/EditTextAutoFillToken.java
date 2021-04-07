package com.masterhelper.global.autocomplitefield.editField;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.MultiAutoCompleteTextView;

public class EditTextAutoFillToken implements MultiAutoCompleteTextView.Tokenizer {
  String separator;

  public EditTextAutoFillToken(String separator) {
    this.separator = separator;
  }

  public int findTokenStart(CharSequence text, int cursor) {
    return text.toString().lastIndexOf(separator);
  }

  public int findTokenEnd(CharSequence text, int cursor) {
    int len = text.length();
    int tokenIndex = text.toString().lastIndexOf(separator);
    return len - (tokenIndex + 1);
  }

  public CharSequence terminateToken(CharSequence text) {
    int i = text.length();

    while (i > 0 && text.charAt(i - 1) == ' ') {
      i--;
    }

    if (i > 0 && text.charAt(i - 1) == separator.charAt(0)) {
      return text;
    } else {
      if (text instanceof Spanned) {
        SpannableString sp = new SpannableString(text + " ");
        TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
          Object.class, sp, 0);
        return sp;
      } else {
        return text + " ";
      }
    }
  }
}
