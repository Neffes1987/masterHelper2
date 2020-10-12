package com.masterhelper.ux.components.library.list;

/** holder data wrapper that wrap payload data for list item view into abstract container */
public class CommonHolderPayloadData<DataModel> {
  private final int id;
  private DataModel payload;

  /**
   * @param id - position into the list
   * @param payload - data for list item
   * */
  public CommonHolderPayloadData(int id, DataModel payload){
    this.id = id;
    this.payload = payload;
  }

  public int getId() {
    return id;
  }

  public DataModel getPayload() {
    return payload;
  }

  public void setPayload(DataModel payload){
    this.payload = payload;
  }
}
