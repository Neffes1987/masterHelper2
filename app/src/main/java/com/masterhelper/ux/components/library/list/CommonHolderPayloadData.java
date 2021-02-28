package com.masterhelper.ux.components.library.list;

import com.masterhelper.global.db.repositories.common.model.GeneralModel;
import com.masterhelper.global.fields.DataID;

import java.util.ArrayList;

/**
 * holder data wrapper that wrap payload data for list item view into abstract container
 */
public class CommonHolderPayloadData {
  private int position;
  private final DataID id;
  private String title;
  private String description;
  private final String previewUrl;
  public Boolean isSelected;
  public Boolean isPlayed;

  public void setListId(int position) {
    this.position = position;
  }

  public int getListId() {
    return position;
  }

  /**
   * @param id - position into the list
   */
  public CommonHolderPayloadData(DataID id, String title, String previewUrl) {
    this.id = id;
    this.title = title;
    this.previewUrl = previewUrl;
    this.isSelected = false;
    this.isPlayed = false;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public static ArrayList<CommonHolderPayloadData> convertFromModels(GeneralModel[] models) {
    ArrayList<CommonHolderPayloadData> list = new ArrayList<>();
    for (GeneralModel model : models) {
      list.add(new CommonHolderPayloadData(model.id, model.name.get().toString(), ""));
    }
    return list;
  }

  public CommonHolderPayloadData(DataID id, String title, String previewUrl, boolean isSelected, boolean isPlayed) {
    this.id = id;
    this.title = title;
    this.previewUrl = previewUrl;
    this.isSelected = isSelected;
    this.isPlayed = isPlayed;
  }

  public DataID getId() {
    return id;
  }

  public String getPreviewUrl() {
    return previewUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
