package com.masterhelper.ux.pages.events;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.db.repositories.events.EventModel;
import com.masterhelper.db.repositories.events.EventRepository;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.ux.pages.events.subPageAccidents.PageAccident;
import com.masterhelper.ux.pages.events.subPageEncounter.PageEncounterEnimiesList;
import com.masterhelper.ux.pages.events.list.EventDialog;
import com.masterhelper.ux.pages.events.list.ListItemEvent;
import com.masterhelper.ux.pages.events.subPageMeetings.PageMeeting;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import static com.masterhelper.ux.pages.events.EventLocale.getLocalizationByKey;
import static com.masterhelper.ux.pages.scenes.PageSceneList.INTENT_SCENE_ID;
import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.musicStarted;
import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.primary;


public class PageEventsList extends AppCompatActivity implements SetBtnEvent, ListItemEvents {
    public static final String INTENT_EVENT_ID = "sceneId";
    FragmentManager mn;
    EventRepository repository;

    EventDialog eventDialog;
    ComponentUIList<EventModel> list;
    ComponentUIFloatingButton newItemButton;
    ComponentUIFloatingButton musicControlButton;
    boolean isMusicActive = false;

    void toggleMusicControl(){
        isMusicActive = !isMusicActive;
    }

    ComponentUIList<EventModel> initList(EventModel[] items){
        ComponentUIList<EventModel> list = ComponentUIList.cast(mn.findFragmentById(R.id.EVENTS_LIST_ID));
        list.controls.setAdapter(items, new ListItemEvent(getSupportFragmentManager(), this));
        return list;
    }

    private void onCreateItem(String text, String description, EventModel.EventType type) {
        EventModel newEvent = repository.getDraftRecord();
        newEvent.name.set(text);
        newEvent.description.set(description);
        newEvent.type.set(type);
        newEvent.save();
        list.controls.add(newEvent);
    }

    void initNewItemButton(){
        newItemButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.EVENTS_ADD_NEW_ITEM_ID));
        newItemButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.add));
        newItemButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
        newItemButton.controls.setId(View.generateViewId());
        newItemButton.controls.setOnClick(this);
    }

    void initMusicButton(){
        musicControlButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.EVENTS_MUSIC_CONTROL_BUTTON));
        musicControlButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.music));
        musicControlButton.controls.setIconColor(primary);
        musicControlButton.controls.setOnClick(this);
        musicControlButton.controls.setId(View.generateViewId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_events_list);
        mn = getSupportFragmentManager();
        repository = GlobalApplication.getAppDB().eventRepository;
        repository.setSceneId(getIntent().getStringExtra(INTENT_SCENE_ID));
        UIToolbar.setTitle(this, getLocalizationByKey(EventLocale.Keys.listCaption), null);
        eventDialog = new EventDialog(this, repository.getNameLength(), repository.getDescriptionLength());
        initNewItemButton();
        initMusicButton();
        list = initList(repository.list(0,0));
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

    private void setBackgroundMusicState() {
        toggleMusicControl();
        musicControlButton.controls.setIconColor(isMusicActive ? musicStarted : primary);
    }
    private void startOpenMusicConsole() {
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
            setBackgroundMusicState();
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
            startOpenMusicConsole();
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
        repository.removeRecord(item.id);
    }

    @Override
    public void onSelect(int listItemId) {
        EventModel item = list.controls.getItemByListId(listItemId);
        Intent eventIntent;
        switch (item.type.get()){
            case accident:
                eventIntent = new Intent(this, PageAccident.class);
                break;
            case battle:
                eventIntent = new Intent(this, PageEncounterEnimiesList.class);
                break;
            case meeting:
                eventIntent = new Intent(this, PageMeeting.class);
                break;
            default: throw new Error("PageEventsList: onSelect - wrong event type");
        }
        eventIntent.putExtra(INTENT_EVENT_ID, item.id.get().toString());
        eventIntent.putExtra(INTENT_SCENE_ID, getIntent().getStringExtra(INTENT_SCENE_ID));
        startActivity(eventIntent);
    }
}