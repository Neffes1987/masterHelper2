package com.masterhelper.ux.components.library.image;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.core.UXElement;

import java.io.File;

public class Image extends UXElement<ImageView> {
  public Image(@NonNull View androidSystemComponent){
    this.setUxElement((ImageView) androidSystemComponent);
  }

  public void setResource(int resource){
    this.getUxElement().setImageResource(resource);
  }

  public void setFile(File imageFile){
    this.getUxElement().setImageURI(Uri.parse(imageFile.getPath()));
  }

  public void setClickListener(SetBtnEvent listener){
    int elementID = this.getUxElement().getId();
    String tag = this.getUxElement().getTag().toString();
    this.getUxElement().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onClick(elementID, tag);
      }
    });

    this.getUxElement().setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        listener.onLongClick(elementID);
        return true;
      }
    });
  }
}
