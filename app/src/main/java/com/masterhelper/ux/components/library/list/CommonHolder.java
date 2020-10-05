package com.masterhelper.ux.components.library.list;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/** class for working with TextView as readonly label */
public class CommonHolder<DataModel> extends RecyclerView.ViewHolder {
  private CommonItem<DataModel> commonItem;

  public CommonHolder(@NonNull View itemView, CommonItem<DataModel> template) {
    super(itemView);
    commonItem = template.getInstance(itemView);
  }

  public void update(CommonHolderPayloadData<DataModel> item) {
    commonItem.updateHolderByData(item.getPayload());
  }
}
