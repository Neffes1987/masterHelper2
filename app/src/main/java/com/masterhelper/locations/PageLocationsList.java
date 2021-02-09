package com.masterhelper.locations;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.db.repositories.events.EventModel;
import com.masterhelper.db.repositories.events.EventRepository;
import com.masterhelper.goals.repository.GoalModel;
import com.masterhelper.goals.repository.GoalRepository;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.buttons.floating.FloatingButtonsPreset;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.ux.components.widgets.musicButton.WidgetMusicFloatingButton;
import com.masterhelper.locations.list.EventDialog;
import com.masterhelper.locations.list.ListItemEvent;

import static com.masterhelper.ux.media.FileViewerWidget.WIDGET_RESULT_CODE;
import static com.masterhelper.locations.LocationLocale.getLocalizationByKey;
import static com.masterhelper.goals.PageGoalsList.INTENT_GOAL_ID;


public class PageLocationsList extends AppCompatActivity implements SetBtnEvent, ListItemEvents {
  public static final String INTENT_EVENT_ID = "eventId";
  FragmentManager mn;
  EventRepository eventRepository;
  GoalRepository goalRepository;

  EventDialog eventDialog;
  ComponentUIList<EventModel> list;
  ComponentUIFloatingButton newItemButton;
  WidgetMusicFloatingButton musicControlButton;
  GoalModel parentScene;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_events_list);
    mn = getSupportFragmentManager();
    eventRepository = GlobalApplication.getAppDB().eventRepository;
    goalRepository = GlobalApplication.getAppDB().goalRepository;
    parentScene = goalRepository.getRecord(getIntent().getStringExtra(INTENT_GOAL_ID));
    eventRepository.setSceneId(parentScene.id.toString());
    UIToolbar.setTitle(this, getLocalizationByKey(LocationLocale.Keys.listCaption), null);
    eventDialog = new EventDialog(this, eventRepository.getNameLength(), eventRepository.getDescriptionLength());
    initNewItemButton();

    musicControlButton = WidgetMusicFloatingButton.cast(mn.findFragmentById(R.id.MUSIC_CONTROL_BUTTON));
    musicControlButton.init(this);

    list = initList(eventRepository.list(0,0));
  }

  ComponentUIList<EventModel> initList(EventModel[] items){
    ComponentUIList<EventModel> list = ComponentUIList.cast(mn.findFragmentById(R.id.EVENTS_LIST_ID));
    list.controls.setAdapter(items, new ListItemEvent(getSupportFragmentManager(), this));
    return list;
  }

  private void onCreateItem(String text, String description, EventModel.EventType type) {
    EventModel newEvent = eventRepository.getDraftRecord();
    newEvent.name.set(text);
    newEvent.description.set(description);
    newEvent.type.set(type);
    newEvent.save();
    list.controls.add(newEvent, false);
  }

  void initNewItemButton(){
    newItemButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.EVENTS_ADD_NEW_ITEM_ID));
    FloatingButtonsPreset.setPreset(FloatingButtonsPreset.Presets.addNewItem, newItemButton);
    newItemButton.controls.setOnClick(this);
  }

  void openAddNewItemDialog(){
    eventDialog.initCreateState();
    eventDialog.dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        onCreateItem(
          eventDialog.getName(),
          eventDialog.getDescription(),
          eventDialog.getSelectedType()
        );
      }
      @Override
      public void onReject() {

      }
    });
    eventDialog.show();
  }

  /**
   * click callback for short click event
   *
   * @param btnId - element unique id that fired event
   * @param tag -
   */
  @Override
  public void onClick(int btnId, String tag) {
    if(btnId == newItemButton.controls.getId()){
      openAddNewItemDialog();
      return;
    }
    if(btnId == musicControlButton.controls.getId()){
      //musicControlButton.setBackgroundMusicState(parentScene.getMusicHashes());
    }
  }

  /**
   * click callback for long click event
   *
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {
    if(btnId == musicControlButton.controls.getId()){
      //musicControlButton.openMusicConsole(parentScene.getMusicHashes());
    }
  }

  @Override
  public void onUpdate(int listItemId) {
    EventModel item = list.controls.getItemByListId(listItemId);
    eventDialog.initUpdateState(
      item.name.get(),
      item.description.get(),
      item.type.get()
    );

    eventDialog.dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        item.name.set(eventDialog.getName());
        item.description.set(eventDialog.getDescription());
        item.type.set(eventDialog.getSelectedType());
        item.save();
        list.controls.update(item, listItemId);
      }
      @Override
      public void onReject() {

      }
    });
    eventDialog.show();
  }

  @Override
  public void onDelete(int listItemId) {
    EventModel item = list.controls.getItemByListId(listItemId);
    list.controls.delete(listItemId);
    eventRepository.removeRecord(item.id);
  }

  @Override
  public void onSelect(int listItemId) {
    EventModel item = list.controls.getItemByListId(listItemId);
    Intent eventIntent;
    eventIntent = new Intent(this, PageLocation.class);
    eventIntent.putExtra(INTENT_EVENT_ID, item.id.get().toString());
    eventIntent.putExtra(INTENT_GOAL_ID, getIntent().getStringExtra(INTENT_GOAL_ID));
    startActivity(eventIntent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode == RESULT_OK){
      if(requestCode == WIDGET_RESULT_CODE && data != null){
        //parentScene.setMusicPathsArray(data.getStringArrayExtra(SELECTED_IDS_INTENT_EXTRA_NAME));
        parentScene.save();
      }
    }
  }
}