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
public class CommonItem {
  private final ImageView preview;
  private final TextView title;
  private final CheckBox selection;
  private final ImageButton play;

  public static final int TEMPLATE_ID = R.layout.fragment_component_ui_list_item;
  public static final int TEMPLATE_PREVIEW_ID = R.id.LIST_ITEM_PREVIEW_ID;
  public static final int TEMPLATE_BODY_ID = R.id.COMPONENT_LIST_ITEM_CARD;
  public static final int TEMPLATE_TITLE_ID = R.id.LIST_ITEM_TITLE_ID;
  public static final int TEMPLATE_DELETE_BTN_ID = R.id.LIST_ITEM_DELETE_BTN_ID;
  public static final int TEMPLATE_EDIT_BTN_ID = R.id.LIST_ITEM_EDIT_BTN_ID;
  public static final int TEMPLATE_PLAY_BTN_ID = R.id.LIST_ITEM_PLAY_BTN_ID;
  public static final int TEMPLATE_CHECK_BTN_ID = R.id.LIST_ITEM_CHECKBOX_ID;

  protected int pListItemId = -1;

  protected void update(CommonHolderPayloadData itemData) {
    title.setText(itemData.getTitle());
    preview.setImageURI(Uri.parse(itemData.getPreviewUrl()));
    selection.setChecked(itemData.isSelected);
    play.setImageResource(itemData.isPlayed ? R.mipmap.pause : R.mipmap.play);
    pListItemId = itemData.getListId();
  }

  public CommonItem(View view, ListItemControlsListener listItemEvents, ArrayList<Flags> flags) {
    title = view.findViewById(CommonItem.TEMPLATE_TITLE_ID);
    CardView body = view.findViewById(CommonItem.TEMPLATE_BODY_ID);

    preview = view.findViewById(CommonItem.TEMPLATE_PREVIEW_ID);
    if (!flags.contains(Flags.showPreview)) {
      preview.setVisibility(View.GONE);
    }

    selection = view.findViewById(CommonItem.TEMPLATE_CHECK_BTN_ID);
    if (!flags.contains(Flags.showSelection)) {
      selection.setVisibility(View.GONE);
      body.setOnClickListener(v -> listItemEvents.onSelect(pListItemId));
    } else {
      selection.setOnClickListener(v -> listItemEvents.onSelect(pListItemId));
    }

    ImageButton deleteBtn = view.findViewById(CommonItem.TEMPLATE_DELETE_BTN_ID);
    if (!flags.contains(Flags.showDelete)) {
      deleteBtn.setVisibility(View.GONE);
    } else {
      deleteBtn.setOnClickListener(v -> listItemEvents.onDelete(pListItemId));
    }

    ImageButton editBtn = view.findViewById(CommonItem.TEMPLATE_EDIT_BTN_ID);
    if (!flags.contains(Flags.showEdit)) {
      editBtn.setVisibility(View.GONE);
    } else {
      editBtn.setOnClickListener(v -> listItemEvents.onUpdate(pListItemId));
    }

    play = view.findViewById(CommonItem.TEMPLATE_PLAY_BTN_ID);
    if (!flags.contains(Flags.showPlay)) {
      play.setVisibility(View.GONE);
    } else {
      play.setOnClickListener(v -> listItemEvents.onUpdate(pListItemId));
    }
  }

  public enum Flags {
    showPreview,
    showSelection,
    showEdit,
    showDelete,
    showPlay
  }
}
