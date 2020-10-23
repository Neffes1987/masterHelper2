package com.masterhelper.ux.components.library.appBar;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.R;

public class UIToolbar {
  private static final int toolbarId = R.id.COMPONENT_TOOLBAR;
  public static void setTitle(AppCompatActivity context, String title, String subString){
    Toolbar toolbar = context.findViewById(UIToolbar.toolbarId);
    toolbar.setTitle(title);
    if(subString != null){
      toolbar.setSubtitle(subString);
    }
    context.setSupportActionBar(toolbar);
  }
}
