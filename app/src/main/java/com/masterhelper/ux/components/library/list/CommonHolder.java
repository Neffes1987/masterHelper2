package com.masterhelper.ux.components.library.list;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/** class for working with  */
public class CommonHolder<DataModel> extends RecyclerView.ViewHolder {
  private final CommonItem<DataModel> commonItem;

  public CommonHolder(@NonNull View itemView, CommonItem<DataModel> workingClassInstance) {
    super(itemView);
    commonItem = workingClassInstance.clone(itemView);
  }

  public void update(CommonHolderPayloadData<DataModel> item) {
    commonItem.update(item.getPayload(), item.getId());
  }
}
