package com.masterhelper.media.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.media.Formats;
import com.masterhelper.media.filesystem.LibraryFileData;
import com.masterhelper.media.list.elements.*;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.image.ComponentUIImage;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ListItemLocation;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

/**  */
public class ListItemFile extends CommonItem<LibraryFileData> implements SetBtnLocation {
  private FileName name;
  private FileDeleteControl deleteButton;
  private FilePlayControl playButton;
  private FileSelection selection;
  private FilePreview preview;
  private final boolean isGlobal;
  private Formats format;
  private final boolean hidePreview;


  public ListItemFile(FragmentManager manager, ListItemLocation listItemJourneyEvents, boolean isGlobal, boolean hidePreview, Formats format) {
    super(manager, listItemJourneyEvents);
    this.isGlobal = isGlobal;
    this.hidePreview = hidePreview;
    this.format = format;
  }

  public ListItemFile(View view, FragmentManager manager, ListItemLocation listItemJourneyEvents, boolean isGlobal, boolean hidePreview, Formats format) {
    super(view, manager, listItemJourneyEvents);
    this.isGlobal = isGlobal;
    this.hidePreview = hidePreview;
    this.format = format;
    View pLayout = getHeader();

    if (!isGlobal) {
      selection = new FileSelection(pLayout, manager, "", this);
    }
    if (format == Formats.imagePng) {
      preview = new FilePreview(pLayout, manager);
    }
    name = new FileName(pLayout, manager);

    if (isGlobal) {
      deleteButton = new FileDeleteControl(pLayout, manager, this);
    }

    if (!hidePreview) {
      playButton = new FilePlayControl(pLayout, manager, this);
    }
  }

  @Override
  protected void update(LibraryFileData itemData, int listItemId) {
    setListItemId(listItemId);
    boolean isPlayed = itemData.isPlayed;
    name.setElementData(itemData.getFileName());
    if (!isGlobal) {
      selection.setElementData(itemData.isSelected);
    }

    if (format == Formats.imagePng) {
      preview.setElementData(itemData.getFile().getPath());
    }

    if (playButton != null && playButton.getBtn() != null) {
      playButton.getBtn().controls.setIcon(
        ResourceIcons.getIcon(isPlayed ? ResourceIcons.ResourceColorType.pause : ResourceIcons.ResourceColorType.play)
      );
      playButton.getBtn().controls.setIconColor(
        isPlayed ? ResourceColors.ResourceColorType.musicStarted : ResourceColors.ResourceColorType.common
      );
    }
  }

  @Override
  public CommonItem<LibraryFileData> clone(View view) {
    return new ListItemFile(view, getManager(), getListItemSceneEvents(), isGlobal, hidePreview, format);
  }

  /**
   * click callback for short click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onClick(int btnId, String tag) {
    if (getListItemSceneEvents() == null) {
      return;
    }
    if (deleteButton != null && deleteButton.getTag().equals(tag)) {
      getListItemSceneEvents().onDelete(pListItemId);
      return;
    }
    if (playButton.getTag().equals(tag)) {
      getListItemSceneEvents().onUpdate(pListItemId);
    }

    if(selection != null && selection.getCheckbox().getId() == btnId){
      getListItemSceneEvents().onSelect(pListItemId);
    }
  }

  /**
   * click callback for long click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {}

}