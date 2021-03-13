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
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.CommonHolderPayloadData;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.components.library.list.ListItemControlsListener;

import java.util.ArrayList;

import static com.masterhelper.journeys.JourneyLocale.getLocalizationByKey;
import static com.masterhelper.ux.components.library.list.CommonHolderPayloadData.convertFromModels;

public class PageJourneyList extends AppMenuActivity implements ListItemControlsListener {
  public static final String INTENT_JOURNEY_ID = "journeyId";
  FragmentManager mn;

  ComponentUIDialog dialog;
  ComponentUIList list;
  JourneyRepository journeyRepository;

  ComponentUIDialog initDialog(int maxNameLength) {
    ComponentUIDialog dialog = new ComponentUIDialog(this);
    dialog.pNameField.setText("");
    dialog.pNameField.setMaxLength(maxNameLength);
    dialog.pNameField.show();
    return dialog;
  }

  ComponentUIList initList(JourneyModel[] items) {
    ComponentUIList list = ComponentUIList.cast(mn.findFragmentById(R.id.JOURMEY_ITEMS_LIST));
    ArrayList<CommonItem.Flags> flags = new ArrayList<>();
    flags.add(CommonItem.Flags.showDelete);
    flags.add(CommonItem.Flags.showEdit);
    list.controls.setAdapter(convertFromModels(items), this, flags);
    return list;
  }

  void onCreateItem(String text) {
    JourneyModel newJourney = journeyRepository.getDraftRecord();
    newJourney.name.set(text);
    CommonHolderPayloadData newItem = new CommonHolderPayloadData(newJourney.id, text, "");
    list.controls.add(newItem, true);
    newJourney.save();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_journey_list);
    setItemControlTitle(JourneyLocale.getLocalizationByKey(JourneyLocale.Keys.createJourney));
    mn = getSupportFragmentManager();
    journeyRepository = GlobalApplication.getAppDB().journeyRepository;

    UIToolbar.setTitle(this, getLocalizationByKey(JourneyLocale.Keys.listCaption), null);

    // init page components
    dialog = initDialog(journeyRepository.getNameLength());
    list = initList(journeyRepository.list(0, 0, null));
  }

  @Override
  public void onUpdate(int listItemId) {
    dialog.setTitle(getLocalizationByKey(JourneyLocale.Keys.updateJourney));
    CommonHolderPayloadData listItem = list.controls.getItemByListId(listItemId);
    JourneyModel item = journeyRepository.getRecord(listItem.getId());
    dialog.pNameField.setText(item.name.get());
    dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        item.name.set(dialog.pNameField.getText());
        listItem.setTitle(dialog.pNameField.getText());
        item.save();
        list.controls.update(listItem, listItemId);
      }

      @Override
      public void onReject() {

      }
    });
    dialog.show();
  }

  @Override
  public void onDelete(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    list.controls.delete(listItemId);
    journeyRepository.removeRecord(item.getId());
  }

  @Override
  public void onSelect(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    Intent sceneIntent = new Intent(this, PagePlotsList.class);
    sceneIntent.putExtra(INTENT_JOURNEY_ID, item.getId().get().toString());
    startActivity(sceneIntent);
  }

  @Override
  public void onPlay(int listItemId) {

  }

  @Override
  protected void onItemControl() {
    dialog.setTitle(getLocalizationByKey(JourneyLocale.Keys.createJourney));
    dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        onCreateItem(dialog.pNameField.getText());
      }

      @Override
      public void onReject() {

      }
    });
    dialog.show();
  }
}
