package com.masterhelper.ux.components.library.list;

public interface ListItemLocation {
  void onUpdate(int listItemId);

  void onDelete(int listItemId);

  void onSelect(int listItemId);
}