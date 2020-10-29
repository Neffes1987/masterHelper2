package com.masterhelper.ux.pages.events;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.ux.pages.accidents.PageAccident;
import com.masterhelper.ux.pages.encounter.PageEncounterEnimiesList;
import com.masterhelper.ux.pages.events.list.ListItemEvent;
import com.masterhelper.ux.pages.events.list.UIEventItemData;
import com.masterhelper.ux.pages.meetings.PageMeeting;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import java.util.*;

import static com.masterhelper.ux.pages.events.EventLocale.getLocalizationByKey;
import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.musicStarted;
import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.primary;


public class PageEventsList extends AppCompatActivity implements SetBtnEvent, ListItemEvents {
    FragmentManager mn;

    ComponentUIDialog dialog;
    ComponentUIList<UIEventItemData> list;
    ComponentUIFloatingButton newItemButton;
    ComponentUIFloatingButton musicControlButton;
    boolean isMusicActive = false;
    HashMap<UIEventItemData.EventType, String> eventsTypesList = new HashMap<>();

    void toggleMusicControl(){
        isMusicActive = !isMusicActive;
    }

    ComponentUIList<UIEventItemData> initList(UIEventItemData[] items){
        ComponentUIList<UIEventItemData> list = ComponentUIList.cast(mn.findFragmentById(R.id.EVENTS_LIST_ID));
        list.controls.setAdapter(items, new ListItemEvent(getSupportFragmentManager(), this));
        return list;
    }

    ComponentUIDialog initDialog(){
        ComponentUIDialog dialog = new ComponentUIDialog(this);
        dialog.setTitle(getLocalizationByKey(EventLocale.Keys.createEvent));
        dialog.pNameLabel.show();
        dialog.pNameLabel.setText(getLocalizationByKey(EventLocale.Keys.eventName));

        dialog.pNameField.setText("");
        dialog.pNameField.show();

        dialog.pDescriptionLabel.show();
        dialog.pDescriptionLabel.setText(getLocalizationByKey(EventLocale.Keys.shortDescription));

        dialog.pDescriptionField.setText("");
        dialog.pDescriptionField.show();

        eventsTypesList.put(UIEventItemData.EventType.battle, getLocalizationByKey(EventLocale.Keys.eventBattle));
        eventsTypesList.put(UIEventItemData.EventType.accident, getLocalizationByKey(EventLocale.Keys.eventAccident));
        eventsTypesList.put(UIEventItemData.EventType.meeting, getLocalizationByKey(EventLocale.Keys.eventMeeting));

        dialog.pRadioGroup.setList(new ArrayList<>(eventsTypesList.values()));
        dialog.pRadioGroup.show();
        return dialog;
    }

    private void onCreateItem(String text, String description) {

    }

    private void onUpdateItem(String text, String description) {

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
        UIToolbar.setTitle(this, getLocalizationByKey(EventLocale.Keys.listCaption), null);
        dialog = initDialog();
        initNewItemButton();
        initMusicButton();

        ArrayList<UIEventItemData> items = new ArrayList<>();
        items.add(new UIEventItemData("name", "description", UIEventItemData.EventType.battle));
        items.add(new UIEventItemData("name", "description", UIEventItemData.EventType.battle));
        items.add(new UIEventItemData("name", "description", UIEventItemData.EventType.battle));
        items.add(new UIEventItemData("name", "description", UIEventItemData.EventType.battle));
        items.add(new UIEventItemData("name", "description", UIEventItemData.EventType.battle));
        items.add(new UIEventItemData("name", "description", UIEventItemData.EventType.battle));
        list = initList(items.toArray(new UIEventItemData[0]));
    }

    void openAddNewItemDialog(){
        dialog.setTitle(getLocalizationByKey(EventLocale.Keys.createEvent));
        dialog.setListener(new ComponentUIDialog.DialogClickListener() {
            @Override
            public void onResolve() {
                onCreateItem(dialog.pNameField.getText(), dialog.pDescriptionField.getText());
            }

            @Override
            public void onReject() {

            }
        });
        dialog.show();
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
        dialog.setTitle(EventLocale.getLocalizationByKey(EventLocale.Keys.updateEvent));
        UIEventItemData item = list.controls.getItemByListId(listItemId);
        Toast.makeText(this, "onUpdate " + item.getName() + " : " + listItemId, Toast.LENGTH_SHORT).show();

        dialog.pDescriptionField.setText(item.getDescription());
        dialog.pNameField.setText(item.getName());
        ArrayList<UIEventItemData.EventType> eventTypesKeys = new ArrayList<>(eventsTypesList.keySet());
        dialog.pRadioGroup.setSelectedItem(eventTypesKeys.indexOf(item.getEventType()));
        dialog.setListener(new ComponentUIDialog.DialogClickListener() {
            @Override
            public void onResolve() {
                item.setName(dialog.pNameField.getText());
                item.setDescription(dialog.pDescriptionField.getText());
                int selectedIndex = dialog.pRadioGroup.getSelectedItemIndex();
                UIEventItemData.EventType type = eventTypesKeys.get(selectedIndex);
                item.setEventType(type);
                onUpdateItem(item.getName(), item.getDescription());
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
        list.controls.delete(listItemId);
        Toast.makeText(this, "delete " + listItemId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelect(int listItemId) {
        UIEventItemData item = list.controls.getItemByListId(listItemId);
        Intent eventIntent;
        switch (item.getEventType()){
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
        startActivity(eventIntent);
    }
}