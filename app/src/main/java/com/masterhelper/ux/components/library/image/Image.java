package com.masterhelper.ux.components.library.image;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.media.Formats;
import com.masterhelper.ux.components.core.UXButton;
import com.masterhelper.media.FileViewerWidget;
import com.masterhelper.ux.resources.ResourceIcons;

import java.io.File;

public class Image extends UXButton<ImageView> {
  public static final int IMAGE_WIDGET_INTENT_RESULT = 2000;

  public Image(@NonNull View androidSystemComponent){
    this.setUxElement((ImageView) androidSystemComponent);
  }

  public void setResource(int resource){
    this.getUxElement().setImageResource(resource);
  }

  public void setFile(File imageFile){
    if(imageFile.exists()){
      this.getUxElement().setImageURI(Uri.parse(imageFile.getPath()));
    }
  }

  public void clearPreview(){
    this.getUxElement().setImageResource(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.addCircle));
  }

  public void openImageSelector(AppCompatActivity currentActivity){
    currentActivity.startActivityForResult(FileViewerWidget.getWidgetIntent(
      currentActivity,
      Formats.imagePng,
      FileViewerWidget.Layout.global,
      null
    ), IMAGE_WIDGET_INTENT_RESULT);
  }

}
