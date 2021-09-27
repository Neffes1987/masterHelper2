package com.masterhelper.ux;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

public class ContextPopupMenuBuilder {
  PopupMenu popupMenu;
  int[] menuItems;
  OnContextPopupClick listener;
  String elementTag;

  public ContextPopupMenuBuilder(int[] menuItems) {
    this.menuItems = menuItems;
  }

  public void setPopupMenuClickHandler(OnContextPopupClick onContextPopupClickListener) {
    listener = onContextPopupClickListener;
  }

  public void setElementTag(String tag) {
    elementTag = tag;
  }

  public PopupMenu create(Context context, View uiElement) {
    popupMenu = new PopupMenu(context, uiElement);

    if (menuItems.length > 0) {
      for (int title : this.menuItems) {
        popupMenu.getMenu().add(title);
      }
    }

    if (listener != null) {
      popupMenu.setOnMenuItemClickListener(menuItem -> {
        int ind = 0;
        for (int title : this.menuItems) {
          if (context.getResources().getString(title) == menuItem.getTitle()) {
            break;
          }

          ind += 1;
        }

        return listener.onClick(elementTag, ind);
      });
    }

    return popupMenu;
  }

  public ContextPopupMenuBuilder cloneBuilder(String tag) {
    ContextPopupMenuBuilder clone = new ContextPopupMenuBuilder(this.menuItems);
    clone.setPopupMenuClickHandler(this.listener);
    clone.setElementTag(tag);

    return clone;
  }

  public interface OnContextPopupClick {
    Boolean onClick(String tag, int menuItemIndex);
  }

}
