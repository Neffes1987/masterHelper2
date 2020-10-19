package com.masterhelper.ux.components.library.progressBar;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.UXElement;
import com.masterhelper.ux.components.core.UXTextElement;

/** class for working with TextView as readonly label */
public class ProgressBarControl extends UXElement<ProgressBar> {

  public ProgressBarControl(@NonNull View androidSystemComponent){
    this.setUxElement((ProgressBar) androidSystemComponent);
  }

  public void setProgress(int currentProgress){
    this.getUxElement().setProgress(currentProgress, true);
  }

  public void setMax(int maxValue){
    this.getUxElement().setMax(maxValue);
  }

}
