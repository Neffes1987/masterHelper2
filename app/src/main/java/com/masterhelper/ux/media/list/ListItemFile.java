package com.masterhelper.ux.media.list;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.filesystem.LibraryFileData;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.ux.media.list.elements.FileDeleteControl;
import com.masterhelper.ux.media.list.elements.FileName;
import com.masterhelper.ux.media.list.elements.FilePlayControl;
import com.masterhelper.ux.media.list.elements.FileSelection;
import com.masterhelper.ux.resources.ResourceIcons;

/**  */
public class ListItemFile extends CommonItem<LibraryFileData> implements SetBtnEvent {
  private FileName name;
  private FileDeleteControl deleteButton;
  private FilePlayControl playButton;
  private FileSelection selection;
  private final boolean isGlobal;
  private boolean isPlayed;


  public ListItemFile(FragmentManager manager, ListItemEvents listItemJourneyEvents, boolean isGlobal) {
    super(manager, listItemJourneyEvents);
    this.isGlobal = isGlobal;
  }

  public ListItemFile(View view, FragmentManager manager, ListItemEvents listItemJourneyEvents, boolean isGlobal) {
    super(view, manager, listItemJourneyEvents);
    this.isGlobal = isGlobal;
    View pLayout = getHeader();
    if(!isGlobal){
      selection = new FileSelection(pLayout, manager, "");
    }
    name = new FileName(pLayout, manager);
    deleteButton = new FileDeleteControl(pLayout, manager, this);
    playButton = new FilePlayControl(pLayout, manager, this);
  }

  @Override
  protected void update(LibraryFileData itemData, int listItemId) {
    setListItemId(listItemId);
    isPlayed = itemData.isPlayed.get();
    name.setElementData(itemData.getFileName());
    if(!isGlobal){
      selection.setElementData(itemData.isSelected.get());
    }
    if(playButton.getBtn() != null){
      playButton.getBtn().controls.setIcon(
        ResourceIcons.getIcon( isPlayed ? ResourceIcons.ResourceColorType.pause : ResourceIcons.ResourceColorType.play )
      );
    }
  }

  @Override
  public CommonItem<LibraryFileData> clone(View view) {
    return new ListItemFile(view, getManager(), getListItemSceneEvents(), isGlobal);
  }

  /**
   * click callback for short click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onClick(int btnId, String tag) {
    if(getListItemSceneEvents() == null){
      return;
    }
    if(deleteButton.getTag().equals(tag)){
      getListItemSceneEvents().onDelete(pListItemId);
      return;
    }
    if(playButton.getTag().equals(tag)){
      getListItemSceneEvents().onUpdate(pListItemId);
    }
  }

  /**
   * click callback for long click event
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {}

}