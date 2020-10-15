package com.masterhelper.ux.components.library.list;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.masterhelper.ux.components.core.UXElement;

import java.util.ArrayList;

/** class for working with RecyclerView android component
 * */
public class ComponentList<DataType> extends UXElement<RecyclerView> {

  private CommonAdapter<DataType> adapter;

  /** will create new adapter for the list */
  private void generateAdapter(CommonAdapter<DataType> adapter) {
    this.adapter = adapter;
  }

  /** will throw the error if client code try to work with empty adapter */
  private void checkAdapter(){
    if(adapter == null){
      throw new Error("ComponentList: adapter for list is not created");
    }
  }

  /** will create and add new adapter to the list
   * @param data - items data for list
   * @param template - special item control class that describe how te item should looks like onto list
   * */
  public void setAdapter(ArrayList<DataType> data, CommonItem<DataType> template){
    generateAdapter(new CommonAdapter<>(data, template));
    this.getUxElement().setAdapter(adapter);
  }

  public DataType getItemByListId(int listItemId){
    checkAdapter();
    return adapter.getItem(listItemId);
  }

  /**
   * will update particular item into the list
   * @param updatedItem -data for updating
   * @param listItemId - list item unique id for searching into the list
   * */
  public void update(DataType updatedItem, int listItemId){
    checkAdapter();
    adapter.updateItem(updatedItem, listItemId);
  }

  /** will add new item into the list
   * @param newItem - data for new item
   * */
  public void add(DataType newItem){
    checkAdapter();
    adapter.addItem(newItem, true);
  }

  /** delete particular item in the list by the unique list item id */
  public void delete(int listItemId){
    checkAdapter();
    adapter.deleteItem(listItemId);
  }

  public ComponentList(@NonNull View androidSystemComponent){
    this.setUxElement((RecyclerView) androidSystemComponent);
  }

}
