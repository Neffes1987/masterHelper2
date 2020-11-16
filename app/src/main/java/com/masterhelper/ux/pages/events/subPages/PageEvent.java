package com.masterhelper.ux.pages.events.subPages;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
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
import com.masterhelper.ux.components.library.image.ComponentUIImage;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.components.widgets.musicButton.WidgetMusicFloatingButton;
import com.masterhelper.ux.pages.events.list.EventDialog;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import static com.masterhelper.ux.pages.events.PageEventsList.INTENT_EVENT_ID;
import static com.masterhelper.ux.pages.events.subPages.MeetingLocale.getLocalizationByKey;
import static com.masterhelper.ux.pages.scenes.PageSceneList.INTENT_SCENE_ID;
import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.musicStarted;
import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.primary;

public class PageEvent extends AppCompatActivity implements SetBtnEvent, ComponentUIDialog.DialogClickListener {
    private ComponentUIFloatingButton editButton;
    private WidgetMusicFloatingButton musicControl;
    private ComponentUILabel description;
    private ComponentUIImage previewControl;
    private EventDialog eventDialog;
    private EventModel event;

    FragmentManager mn;
    EventRepository repository;

    private boolean isMusicActive;

    void initEditItemButton(){
        editButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.MEETING_EDIT_BTN));
        editButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.pencil));
        editButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
        editButton.controls.setOnClick(this);
        editButton.controls.setId(View.generateViewId());
    }

    void initImageWidget(){
        previewControl = ComponentUIImage.cast(mn.findFragmentById(R.id.MEETING_PREVIEW_ID));
        previewControl.controls.setOnClick(this);
        previewControl.controls.setId(View.generateViewId());
        previewControl.controls.setResource(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.enemy1));
    }

    void initDescriptionLabel(){
        description = ComponentUILabel.cast(mn.findFragmentById(R.id.MEETING_DESCRIPTION_VIEW_ID));
    }

    void toggleMusicControl(){
        isMusicActive = !isMusicActive;
    }
    private void setBackgroundMusicState() {
        toggleMusicControl();
        musicControl.controls.setIconColor(isMusicActive ? musicStarted : primary);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_event);
        repository = GlobalApplication.getAppDB().eventRepository;
        repository.setSceneId(getIntent().getStringExtra(INTENT_SCENE_ID));
        event = repository.getRecord(getIntent().getStringExtra(INTENT_EVENT_ID));

        UIToolbar.setTitle(this, getLocalizationByKey(MeetingLocale.Keys.name), event.name.get());
        mn = getSupportFragmentManager();
        initEditItemButton();

        musicControl = WidgetMusicFloatingButton.cast(mn.findFragmentById(R.id.MUSIC_CONTROL_ID));
        musicControl.init(this);

        initImageWidget();
        initDescriptionLabel();
        eventDialog = new EventDialog(this, repository.getNameLength(), repository.getDescriptionLength());
        eventDialog.dialog.pRadioGroup.hide();
        description.controls.setText(event.description.get());
    }

    @Override
    public void onClick(int btnId, String tag) {
        if(btnId == editButton.controls.getId()){
            eventDialog.initUpdateState(event.name.get(), event.description.get(), event.type.get());
            eventDialog.dialog.setListener(this);
            eventDialog.show();
            return;
        }
        if(btnId == musicControl.controls.getId()){
            setBackgroundMusicState();
            return;
        }
        if(btnId == previewControl.controls.getId()){
            Toast.makeText(this, "preview show", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLongClick(int btnId) {
        if(btnId == musicControl.controls.getId()){
            Toast.makeText(this, "music open list", Toast.LENGTH_SHORT).show();
            return;
        }
        if(btnId == previewControl.controls.getId()){
            Toast.makeText(this, "preview upload", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResolve() {
        event.name.set(eventDialog.getName());
        event.description.set(eventDialog.getDescription());
        event.save();
        UIToolbar.setTitle(this, getLocalizationByKey(MeetingLocale.Keys.name), event.name.get());
        description.controls.setText(eventDialog.getDescription());
    }

    @Override
    public void onReject() {

    }
}