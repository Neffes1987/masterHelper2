package com.masterhelper.journeys;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.journeys.repository.JourneyModel;
import com.masterhelper.journeys.repository.JourneyRepository;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.plotLine.PagePlotsList;
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.dialog.TextDialog;
import com.masterhelper.ux.components.library.list.*;

import java.util.ArrayList;

import static com.masterhelper.journeys.JourneyLocale.getLocalizationByKey;
import static com.masterhelper.ux.components.library.list.CommonHolderPayloadData.convertFromModels;

public class PageJourneyList extends AppMenuActivity implements ListItemControlsListener {
  public static final String INTENT_JOURNEY_ID = "journeyId";
  FragmentManager mn;

  ComponentUIList list;
  JourneyRepository journeyRepository;


  ComponentUIList initList(JourneyModel[] items) {
    ComponentUIList list = ComponentUIList.cast(mn.findFragmentById(R.id.ITEMS_LIST));
    ArrayList<CommonItem.Flags> flags = new ArrayList<>();
    flags.add(CommonItem.Flags.showDelete);
    flags.add(CommonItem.Flags.showEdit);
    list.controls.setAdapter(convertFromModels(items), this, flags);
    return list;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_simple_list);
    setItemControlTitle(JourneyLocale.getLocalizationByKey(JourneyLocale.Keys.createJourney));
    mn = getSupportFragmentManager();
    journeyRepository = GlobalApplication.getAppDB().journeyRepository;

    UIToolbar.setTitle(this, getLocalizationByKey(JourneyLocale.Keys.listCaption), null);

    list = initList(journeyRepository.list(0, 0, null));
  }


  public void onUpdate(int listItemId) {
    String updateTitle = getLocalizationByKey(JourneyLocale.Keys.updateJourney);
    int nameLength = journeyRepository.getNameLength();
    CommonHolderPayloadData listItem = list.controls.getItemByListId(listItemId);
    JourneyModel item = journeyRepository.getRecord(listItem.getId());
    String defaultValue = item.name.get();

    TextDialog dialog = new TextDialog(this, updateTitle, nameLength, defaultValue, (result) -> {
      item.name.set(result);
      listItem.setTitle(result);
      item.save();
      list.controls.update(listItem, listItemId);
    });

    dialog.show();
  }

  public void onDelete(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    list.controls.delete(listItemId);
    journeyRepository.removeRecord(item.getId());
  }

  public void onSelect(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    Intent sceneIntent = new Intent(this, PagePlotsList.class);
    sceneIntent.putExtra(INTENT_JOURNEY_ID, item.getId().get().toString());
    startActivity(sceneIntent);
  }

  @Override
  protected void onAppBarMenuItemControl() {
    String updateTitle = getLocalizationByKey(JourneyLocale.Keys.createJourney);
    int nameLength = journeyRepository.getNameLength();
    TextDialog dialog = new TextDialog(this, updateTitle, nameLength, "", (result) -> {
      JourneyModel newJourney = journeyRepository.getDraftRecord();
      newJourney.name.set(result);
      CommonHolderPayloadData newItem = new CommonHolderPayloadData(newJourney.id, result, "");
      list.controls.add(newItem, true);
      newJourney.save();
    });

    dialog.show();
  }

  @Override
  public void listItemChanged(ListItemActionCodes code, int listItemId) {
    switch (code) {
      case select:
        onSelect(listItemId);
        break;
      case delete:
        onDelete(listItemId);
        break;
      case update:
        onUpdate(listItemId);
        break;
    }
  }
}
