package com.masterhelper.ux;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

public class ContextPopupMenu {
  PopupMenu popupMenu;

  public ContextPopupMenu(Context context, View view) {
    popupMenu = new PopupMenu(context, view);
  }

  public void setPopupMenuClickHandler(PopupMenu.OnMenuItemClickListener listener) {
    popupMenu.setOnMenuItemClickListener(listener);
  }

  public void setPopupMenuItem(int popupMenuName) {
    popupMenu.getMenu().add(popupMenuName);
  }

  public void show() {
    popupMenu.show();
  }
}
