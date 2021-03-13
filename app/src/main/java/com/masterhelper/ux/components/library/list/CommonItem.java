package com.masterhelper.ux.components.library.list;

import android.net.Uri;
import android.view.View;
import android.widget.*;
import androidx.cardview.widget.CardView;
import com.masterhelper.R;

import java.util.ArrayList;

/**
 * list view control class where db data mapped into ui components
 */
public class CommonItem implements View.OnLongClickListener {
  boolean isShowControl = false;

  private final ImageView preview;
  private final TextView title;
  private final TextView description;
  private final CheckBox selection;
  private final ImageButton play;
  ImageButton deleteBtn;
  ImageButton editBtn;
  ArrayList<Flags> flags;

  public static final int TEMPLATE_ID = R.layout.fragment_component_ui_list_item;
  public static final int TEMPLATE_PREVIEW_ID = R.id.LIST_ITEM_PREVIEW_ID;
  public static final int TEMPLATE_BODY_ID = R.id.COMPONENT_LIST_ITEM_CARD;
  public static final int TEMPLATE_TITLE_ID = R.id.LIST_ITEM_TITLE_ID;
  public static final int TEMPLATE_DELETE_BTN_ID = R.id.LIST_ITEM_DELETE_BTN_ID;
  public static final int TEMPLATE_EDIT_BTN_ID = R.id.LIST_ITEM_EDIT_BTN_ID;
  public static final int TEMPLATE_PLAY_BTN_ID = R.id.LIST_ITEM_PLAY_BTN_ID;
  public static final int TEMPLATE_CHECK_BTN_ID = R.id.LIST_ITEM_CHECKBOX_ID;
  public static final int TEMPLATE_DESCRIPTION_ID = R.id.LIST_ITEM_DESCRIPTION_ID;

  protected int pListItemId = -1;

  protected void update(CommonHolderPayloadData itemData) {
    title.setText(itemData.getTitle());
    description.setText(itemData.getDescription());
    preview.setImageURI(Uri.parse(itemData.getPreviewUrl()));
    selection.setChecked(itemData.isSelected);
    play.setImageResource(itemData.isPlayed ? R.mipmap.pause : R.mipmap.play);
    pListItemId = itemData.getListId();
  }

  public CommonItem(View view, ListItemControlsListener listItemEvents, ArrayList<Flags> flags) {
    title = view.findViewById(CommonItem.TEMPLATE_TITLE_ID);
    this.flags = flags;
    CardView body = view.findViewById(CommonItem.TEMPLATE_BODY_ID);
    body.setOnLongClickListener(this);

    description = view.findViewById(CommonItem.TEMPLATE_DESCRIPTION_ID);
    if (!flags.contains(Flags.showDescription)) {
      description.setVisibility(View.GONE);
    }

    preview = view.findViewById(CommonItem.TEMPLATE_PREVIEW_ID);
    if (!flags.contains(Flags.showPreview)) {
      preview.setVisibility(View.GONE);
    }

    selection = view.findViewById(CommonItem.TEMPLATE_CHECK_BTN_ID);
    if (!flags.contains(Flags.showSelection)) {
      selection.setVisibility(View.GONE);
      body.setOnClickListener(v -> listItemEvents.listItemChanged(ListItemActionCodes.select, pListItemId));
    } else {
      selection.setOnClickListener(v -> listItemEvents.listItemChanged(ListItemActionCodes.select, pListItemId));
    }

    deleteBtn = view.findViewById(CommonItem.TEMPLATE_DELETE_BTN_ID);
    deleteBtn.setVisibility(View.GONE);
    if (flags.contains(Flags.showDelete)) {
      deleteBtn.setOnClickListener(v -> listItemEvents.listItemChanged(ListItemActionCodes.delete, pListItemId));
    }

    editBtn = view.findViewById(CommonItem.TEMPLATE_EDIT_BTN_ID);
    editBtn.setVisibility(View.GONE);
    if (flags.contains(Flags.showEdit)) {
      editBtn.setOnClickListener(v -> listItemEvents.listItemChanged(ListItemActionCodes.update, pListItemId));
    }

    play = view.findViewById(CommonItem.TEMPLATE_PLAY_BTN_ID);
    if (!flags.contains(Flags.showPlay)) {
      play.setVisibility(View.GONE);
    } else {
      play.setOnClickListener(v -> listItemEvents.listItemChanged(ListItemActionCodes.play, pListItemId));
    }
  }

  /**
   * Called when a view has been clicked and held.
   *
   * @param v The view that was clicked and held.
   * @return true if the callback consumed the long click, false otherwise.
   */
  @Override
  public boolean onLongClick(View v) {
    isShowControl = !isShowControl;
    if (flags.contains(Flags.showDelete)) {
      deleteBtn.setVisibility(isShowControl ? View.VISIBLE : View.GONE);
    }

    if (flags.contains(Flags.showEdit)) {
      editBtn.setVisibility(isShowControl ? View.VISIBLE : View.GONE);
    }

    return true;
  }


  public enum Flags {
    showPreview,
    showSelection,
    showEdit,
    showDelete,
    showPlay,
    showDescription
  }
}
