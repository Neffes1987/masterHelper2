package com.masterhelper.ux.components.library;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.UXTextElement;

/** class for working with TextView as readonly label */
public class Label extends UXTextElement<TextView> {
  /**
   * set text value in text component
   *
   * @param text - new value fom text element
   */
  @Override
  public void setText(@NonNull String text) {
    this.getUxElement().setText(text);
  }

  /**
   * read text from text component
   */
  @Override
  public String getText() {
    return this.getUxElement().getText().toString();
  }

  public Label(@NonNull View androidSystemComponent){
    this.setUxElement((TextView) androidSystemComponent);
  }

}
