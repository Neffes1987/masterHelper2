package com.masterhelper.ux.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.masterhelper.R;
import com.masterhelper.ux.global.GlobalApplication;

/** Class for working with colors from resources */
public class ResourceColors {
  private static int getColor(ResourceColorType type){
    Context context = GlobalApplication.getAppContext();
    switch (type){
      case secondary: return context.getResources().getColor(R.color.colorPrimary);
      case primary: return context.getResources().getColor(R.color.colorSecondary);
      default:
        throw new Error("ResourceColors - Wrong type of color");
    }
  }

  public static void setBackgroundTint(View element, ResourceColorType type){
    ViewCompat.setBackgroundTintList(element, ColorStateList.valueOf(getColor(type)));
  }

  public enum ResourceColorType{
    primary,
    secondary
  }
}
