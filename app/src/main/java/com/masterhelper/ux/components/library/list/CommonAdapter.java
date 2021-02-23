package com.masterhelper.ux.components.library.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * class for working with TextView as readonly label
 */
public class CommonAdapter extends RecyclerView.Adapter<CommonHolder> {
  private final ArrayList<CommonHolderPayloadData> mDataset;
  ListItemControlsListener listener;
  ArrayList<CommonItem.Flags> flags;

  int idsCounter = 0;

  /** counter that assign local unique id for list items */
  private int getIdsCounter() {
    idsCounter += 1;
    return idsCounter;
  }

  /**
   * find position for item in list by the list item id
   * @param listId - special id of CommonHolderPayloadData wrapper
   * */
  private int getListPositionByListId(int listId){
    int result = -1;
    for(int listIndexCounter = 0; listIndexCounter < mDataset.size(); listIndexCounter+=1){
      if (mDataset.get(listIndexCounter).getListId() == listId) {
        result = listIndexCounter;
        break;
      }
    }
    return result;
  }

  /**
   * @param data - data for list items views
   */
  public CommonAdapter(ArrayList<CommonHolderPayloadData> data, ListItemControlsListener listener, ArrayList<CommonItem.Flags> flags) {
    for (CommonHolderPayloadData item : data) {
      item.setListId(getIdsCounter());
    }
    mDataset = data;
    this.listener = listener;
    this.flags = flags;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public CommonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(CommonItem.TEMPLATE_ID, parent, false);
    return new CommonHolder(v, listener, flags);
  }

  @Override
  public void onBindViewHolder(@NonNull CommonHolder holder, int position) {
    CommonHolderPayloadData data = mDataset.get(position);
    holder.update(data);
  }

  /**
   * Returns the total number of items in the data set held by the adapter.
   *
   * @return The total number of items in this adapter.
   */
  @Override
  public int getItemCount() {
    return mDataset.size();
  }

  public ArrayList<CommonHolderPayloadData> getCurrentList() {
    return mDataset;
  }

  /**
   * delete particular item from the list
   *
   * @param listItemId - unique id from CommonHolderPayloadData wrapper
   */
  public void deleteItem(int listItemId) {
    int position = getListPositionByListId(listItemId);
    mDataset.remove(position);
    notifyItemRemoved(position);
  }

  /**
   * update particular item into list
   *
   * @param updatedData - payload of updatetd data
   * @param listItemId  - unique id from CommonHolderPayloadData wrapper
   */
  public void updateItem(CommonHolderPayloadData updatedData, int listItemId) {
    int position = getListPositionByListId(listItemId);
    updatedData.setListId(listItemId);
    mDataset.set(position, updatedData);
    notifyItemChanged(position, updatedData);
  }

  /**
   * add new item into list
   *
   * @param newData - data for new item in list
   * @param toFirst - add new record as first
   */
  public void addItem(CommonHolderPayloadData newData, boolean toFirst) {
    int listItemId = this.getIdsCounter();
    newData.setListId(listItemId);
    if (toFirst) {
      mDataset.add(0, newData);
    } else {
      mDataset.add(newData);
    }
    int position = getListPositionByListId(listItemId);
    notifyItemInserted(position);
  }

  @Override
  public long getItemId(int position) {
    return mDataset.get(position).getListId();
  }

  public CommonHolderPayloadData getItem(int listItemId) {
    int position = getListPositionByListId(listItemId);
    return mDataset.get(position);
  }
}
