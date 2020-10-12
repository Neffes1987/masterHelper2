package com.masterhelper.ux.components.library.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/** class for working with TextView as readonly label */
public class CommonAdapter<DataType> extends RecyclerView.Adapter<CommonHolder<DataType>> {
  private final ArrayList<CommonHolderPayloadData<DataType>> mDataset;
  private final CommonItem<DataType> pCommonItem;
  int idsCounter = 0;

  /** counter that assign local unique id for list items */
  private int getIdsCounter() {
    idsCounter +=1;
    return idsCounter;
  }

  /**
   * find position for item in list by the list item id
   * @param listId - special id of CommonHolderPayloadData wrapper
   * */
  private int getListPositionByListId(int listId){
    int result = -1;
    for(int listIndexCounter = 0; listIndexCounter < mDataset.size(); listIndexCounter+=1){
      if(mDataset.get(listIndexCounter).getId() == listId){
        result=listIndexCounter;
        break;
      }
    }
    return result;
  }

  /**
   * @param  data - data for list items views
   * @param template - special object that described how the list item record looks like
   * */
  public CommonAdapter(ArrayList<DataType> data, CommonItem<DataType> template) {
    mDataset = new ArrayList<>();
    for (DataType item: data ) {
      mDataset.add(new CommonHolderPayloadData<>(this.getIdsCounter(), item));
    }
    pCommonItem = template;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public CommonHolder<DataType> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(CommonItem.TEMPLATE_ID, parent, false);
    return new CommonHolder<>(v, this.pCommonItem);
  }

  @Override
  public void onBindViewHolder(@NonNull CommonHolder<DataType> holder, int position) {
    CommonHolderPayloadData<DataType> data = mDataset.get(position);
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


  /**
   * delete particular item from the list
   * @param listItemId - unique id from CommonHolderPayloadData wrapper
   * */
  public void deleteItem(int listItemId){
    int position = getListPositionByListId(listItemId);
    mDataset.remove(position);
    notifyItemRemoved(position);
  }

  /** update particular item into list
   * @param updatedData - payload of updatetd data
   * @param listItemId - unique id from CommonHolderPayloadData wrapper
   * */
  public void updateItem(DataType updatedData, int listItemId){
    int position = getListPositionByListId(listItemId);
    mDataset.get(position).setPayload(updatedData);
    notifyItemChanged(position, updatedData);
  }

  /** add new item into list
   * @param newData - data for new item in list
   * @param toFirst - add new record as first
   * */
  public void addItem(DataType newData, boolean toFirst){
    int listItemId = this.getIdsCounter();
    if(toFirst){
      mDataset.add(0, new CommonHolderPayloadData<>(listItemId, newData));
    } else {
      mDataset.add(new CommonHolderPayloadData<>(listItemId, newData));
    }
    int position = getListPositionByListId(listItemId);
    notifyItemInserted(position);
  }

  @Override
  public long getItemId(int position) {
    return mDataset.get(position).getId();
  }
}