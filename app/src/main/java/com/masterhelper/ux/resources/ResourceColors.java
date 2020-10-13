package com.masterhelper.ux.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.masterhelper.R;
import com.masterhelper.ux.global.GlobalApplication;

/** Class for working with colors from resources */
public class ResourceColors {
  public static int getColor(ResourceColorType type){
    Context context = GlobalApplication.getAppContext();
    switch (type){
      case secondary: return context.getResources().getColor(R.color.colorPrimary);
      case primary: return context.getResources().getColor(R.color.colorTextReverse);
      case alert: return context.getResources().getColor(R.color.colorDelete);
      case common: return context.getResources().getColor(R.color.colorCommon);
      default:
        throw new Error("ResourceColors - Wrong type of color");
    }
  }

  public static ColorStateList getColorStateList(ResourceColorType type){
    return ColorStateList.valueOf(getColor(type));
  }

  public static void setBackgroundTint(View element, ResourceColorType type){
    ViewCompat.setBackgroundTintList(element, getColorStateList(type));
  }

  public enum ResourceColorType{
    primary,
    secondary,
    alert,
    common
  }
}
