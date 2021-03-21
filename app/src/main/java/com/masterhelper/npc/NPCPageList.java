package com.masterhelper.npc;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.npc.repository.NPCModel;
import com.masterhelper.npc.repository.NPCRepository;
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.dialog.TextDialog;
import com.masterhelper.ux.components.library.list.*;
import com.masterhelper.ux.components.library.search.ISearchBar;

import java.util.ArrayList;

import static com.masterhelper.npc.NPCPage.INTENT_PAGE_NPC_ID;

public class NPCPageList extends AppMenuActivity implements ListItemControlsListener, ISearchBar {
  final String createTitle = NPCLocale.getLocalizationByKey(NPCLocale.Keys.createNewNPC);
  final String updateTitle = NPCLocale.getLocalizationByKey(NPCLocale.Keys.updateNewNPC);

  ComponentUIList list;
  FragmentManager mn;
  NPCRepository npcRepository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_search_list);
    setPageTitle(NPCLocale.getLocalizationByKey(NPCLocale.Keys.listCaption));
    setItemControlTitle(createTitle);
    mn = getSupportFragmentManager();
    npcRepository = GlobalApplication.getAppDB().npcRepository;
    initList(npcRepository.list(0, 0, ""));
  }

  @Override
  protected void onAppBarMenuItemControl() {
    TextDialog dialog = new TextDialog(this, createTitle, npcRepository.getNameLength(), "", typedValue -> {
      NPCModel newNPC = npcRepository.getDraftRecord();
      newNPC.name.set(typedValue);
      newNPC.save();
      CommonHolderPayloadData newItem = new CommonHolderPayloadData(newNPC.id, typedValue, "");
      list.controls.add(newItem, true);
    });
    dialog.show();
  }

  void initList(NPCModel[] items) {
    list = ComponentUIList.cast(mn.findFragmentById(R.id.LIST_ID));
    ArrayList<CommonItem.Flags> flags = new ArrayList<>();
    flags.add(CommonItem.Flags.showPreview);
    flags.add(CommonItem.Flags.showDelete);
    flags.add(CommonItem.Flags.showEdit);
    flags.add(CommonItem.Flags.showDescription);
    ArrayList<CommonHolderPayloadData> listItems = new ArrayList<>();
    for (NPCModel model : items) {
      CommonHolderPayloadData listItem = new CommonHolderPayloadData(model.id, model.name.get(), model.previewUrl.get());
      listItems.add(listItem);
    }
    list.controls.setAdapter(listItems, this, flags);
  }

  @Override
  public void listItemChanged(ListItemActionCodes code, int listItemId) {
    CommonHolderPayloadData listItem = list.controls.getItemByListId(listItemId);
    switch (code) {
      case select:
        Intent npcPage = new Intent(this, NPCPage.class);
        npcPage.putExtra(INTENT_PAGE_NPC_ID, listItem.getId().get().toString());
        startActivity(npcPage);
        break;
      case delete:
        list.controls.delete(listItemId);
        npcRepository.removeRecord(listItem.getId());
        break;
      case update:
        NPCModel model = npcRepository.getRecord(listItem.getId());
        TextDialog dialog = new TextDialog(this, updateTitle, npcRepository.getNameLength(), model.name.get(), typedValue -> {
          model.name.set(typedValue);
          model.save();
          listItem.setTitle(typedValue);
          list.controls.update(listItem, listItemId);
        });
        dialog.show();
        break;
    }
  }

  @Override
  public void doSearch(String searchStr) {
    initList(npcRepository.list(0, 0, searchStr));
  }
}