package com.masterhelper.journeys;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.journeys.repository.JourneyModel;
import com.masterhelper.journeys.repository.JourneyRepository;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.buttons.floating.FloatingButtonsPreset;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.journeys.list.ListItemJourney;
import com.masterhelper.goals.PageGoalsList;

import static com.masterhelper.journeys.JourneyLocale.getLocalizationByKey;

public class PageJourneyList extends AppCompatActivity implements ListItemEvents {
  public static final String INTENT_JOURNEY_ID = "journeyId";
  FragmentManager mn;

  ComponentUIDialog dialog;
  ComponentUIList<JourneyModel> list;
  JourneyRepository journeyRepository;

  ComponentUIDialog initDialog(int maxNameLength){
    ComponentUIDialog dialog = new ComponentUIDialog(this);
    dialog.pNameField.setText("");
    dialog.pNameField.setMaxLength(maxNameLength);
    dialog.pNameField.show();
    return dialog;
  }

  void initNewItemButton(ComponentUIDialog itemDialog){
    ComponentUIFloatingButton floatingButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.JOURNEY_ADD_NEW_ITEM_BUTTON));
    FloatingButtonsPreset.setPreset(FloatingButtonsPreset.Presets.addNewItem, floatingButton);
    floatingButton.controls.setOnClick(new SetBtnEvent() {
      @Override
      public void onClick(int btnId, String tag) {
        itemDialog.setTitle(getLocalizationByKey(JourneyLocale.Keys.createJourney));
        itemDialog.setListener(new ComponentUIDialog.DialogClickListener() {
          @Override
          public void onResolve() {
            onCreateItem(itemDialog.pNameField.getText());
          }

          @Override
          public void onReject() {

          }
        });
        itemDialog.show();
      }

      @Override
      public void onLongClick(int btnId) {

      }
    });
  }

  ComponentUIList<JourneyModel> initList(JourneyModel[] items){
    ComponentUIList<JourneyModel> list = ComponentUIList.cast(mn.findFragmentById(R.id.JOURMEY_ITEMS_LIST));
    list.controls.setAdapter(items, new ListItemJourney(getSupportFragmentManager(), this));
    return list;
  }

  void onCreateItem(String text){
    JourneyModel newJourney = journeyRepository.getDraftRecord();
    newJourney.name.set(text);
    list.controls.add(newJourney, true);
    newJourney.save();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_journey_list);
    mn = getSupportFragmentManager();
    journeyRepository = GlobalApplication.getAppDB().journeyRepository;

    UIToolbar.setTitle(this, getLocalizationByKey(JourneyLocale.Keys.listCaption), null);

    // init page components
    dialog = initDialog(journeyRepository.getNameLength());
    list = initList(journeyRepository.list(0,0));
    initNewItemButton(dialog);
  }

  @Override
  public void onUpdate(int listItemId) {
    dialog.setTitle(getLocalizationByKey(JourneyLocale.Keys.updateJourney));
    JourneyModel item = list.controls.getItemByListId(listItemId);
    dialog.pNameField.setText(item.name.get());
    dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        item.name.set(dialog.pNameField.getText());
        item.save();
        list.controls.update(item, listItemId);
      }
      @Override
      public void onReject() {

      }
    });
    dialog.show();
  }

  @Override
  public void onDelete(int listItemId) {
    JourneyModel item = list.controls.getItemByListId(listItemId);
    list.controls.delete(listItemId);
    journeyRepository.removeRecord(item.id);
  }

  @Override
  public void onSelect(int listItemId) {
    JourneyModel item = list.controls.getItemByListId(listItemId);
    Intent sceneIntent = new Intent(this, PageGoalsList.class);
    sceneIntent.putExtra(INTENT_JOURNEY_ID, item.id.get().toString());
    startActivity(sceneIntent);
  }
}
