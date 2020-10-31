package com.masterhelper.ux.components.library.text.input;

import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.UXTextElement;

/** class for working with TextView as readonly label */
public class InputTextField extends UXTextElement<EditText> {

  public InputTextField(@NonNull View androidSystemComponent){
    this.setUxElement((EditText) androidSystemComponent);
  }

  /**
   * set text value in text component
   *
   * @param text - new value fom text element
   */
  @Override
  public void setText(@NonNull String text) {
    this.getUxElement().setText(text.trim());
  }

  /**
   * read text from text component
   */
  @Override
  public String getText() {
    return this.getUxElement().getText().toString();
  }

  @Override
  public void setMaxLength(int length) {
    if(length > 0){
      this.getUxElement().setFilters(new InputFilter[] {new InputFilter.LengthFilter(length)});
    }
  }

}
