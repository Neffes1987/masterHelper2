package com.masterhelper.ux.pages.meetings;

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
import com.masterhelper.ux.components.library.image.ComponentUIImage;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import static com.masterhelper.ux.pages.meetings.MeetingLocale.getLocalizationByKey;
import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.musicStarted;
import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.primary;

public class PageMeeting extends AppCompatActivity implements SetBtnEvent {
    private ComponentUIFloatingButton editButton;
    private ComponentUIFloatingButton musicControl;
    private ComponentUILabel description;
    private ComponentUIImage previewControl;

    FragmentManager mn;

    ComponentUIDialog dialog;
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

    void initMusicButton(){
        musicControl = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.MEETING_MUSIC_CONTROL_ID));
        musicControl.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.music));
        musicControl.controls.setIconColor(primary);
        musicControl.controls.setOnClick(this);
        musicControl.controls.setId(View.generateViewId());
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
        setContentView(R.layout.activity_page_meeting);
        UIToolbar.setTitle(this, getLocalizationByKey(MeetingLocale.Keys.name), null);
        mn = getSupportFragmentManager();
        initEditItemButton();
        initMusicButton();
        initImageWidget();
        initDescriptionLabel();

        description.controls.setText("test message");
    }

    @Override
    public void onClick(int btnId, String tag) {
        if(btnId == editButton.controls.getId()){
            Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
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
}