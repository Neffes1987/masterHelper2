package com.masterhelper.global.autocomplitefield.editField;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView;

public class EditTextAutoFillToken implements MultiAutoCompleteTextView.Tokenizer {
  String separator;

  public EditTextAutoFillToken(String separator) {
    this.separator = separator;
  }

  public int findTokenStart(CharSequence text, int cursor) {
    return getCharIndex(text, cursor);
  }

  public int findTokenEnd(CharSequence text, int cursor) {
    return getCharIndex(text, cursor);
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

  int getCharIndex(CharSequence text, int cursor) {
    String lastSeparator = text.toString().substring(0, cursor);
    return lastSeparator.lastIndexOf(separator);
  }
}
