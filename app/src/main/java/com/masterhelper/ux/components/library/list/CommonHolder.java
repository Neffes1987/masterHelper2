package com.masterhelper.ux.components.library.list;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * class for working with
 */
public class CommonHolder extends RecyclerView.ViewHolder {
  private final View itemView;
  private final ListItemControlsListener listener;
  private final ArrayList<CommonItem.Flags> flags;

  public CommonHolder(@NonNull View itemView, ListItemControlsListener listener, ArrayList<CommonItem.Flags> flags) {
    super(itemView);
    this.itemView = itemView;
    this.listener = listener;
    this.flags = flags;
  }

  public void update(CommonHolderPayloadData item) {
    CommonItem commonItem = new CommonItem(itemView, listener, flags);
    commonItem.update(item);
  }
}
