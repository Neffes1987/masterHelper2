package com.masterhelper.locations;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.locations.repository.LocationModel;
import com.masterhelper.locations.repository.LocationRepository;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.image.ComponentUIImage;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.components.widgets.musicButton.WidgetMusicFloatingButton;
import com.masterhelper.locations.list.LocationDialog;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import java.io.File;

import static com.masterhelper.locations.LocationLocale.getLocalizationByKey;
import static com.masterhelper.ux.components.library.image.Image.IMAGE_WIDGET_INTENT_RESULT;
import static com.masterhelper.ux.media.FileViewerWidget.SELECTED_IDS_INTENT_EXTRA_NAME;
import static com.masterhelper.ux.media.FileViewerWidget.WIDGET_RESULT_CODE;
import static com.masterhelper.locations.PageLocationsList.INTENT_EVENT_ID;

public class PageLocation extends AppCompatActivity implements SetBtnLocation, ComponentUIDialog.DialogClickListener {
    private ComponentUIFloatingButton editButton;
    private WidgetMusicFloatingButton musicControl;
    private ComponentUILabel description;
    private ComponentUIImage previewControl;
    private LocationDialog locationDialog;
    private LocationModel location;

    FragmentManager mn;
    LocationRepository repository;

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
        previewControl.controls.setFile(new File(location.previewId.get()));
    }

    void initDescriptionLabel(){
        description = ComponentUILabel.cast(mn.findFragmentById(R.id.MEETING_DESCRIPTION_VIEW_ID));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_location);
        repository = GlobalApplication.getAppDB().locationRepository;
        location = repository.getRecord(getIntent().getStringExtra(INTENT_EVENT_ID));

        UIToolbar.setTitle(this, getLocalizationByKey(LocationLocale.Keys.name), location.name.get());
        mn = getSupportFragmentManager();
        initEditItemButton();

        musicControl = WidgetMusicFloatingButton.cast(mn.findFragmentById(R.id.MUSIC_CONTROL_ID));
        musicControl.init(this);

        initImageWidget();
        initDescriptionLabel();
        locationDialog = new LocationDialog(this, repository.getNameLength());
        locationDialog.dialog.pRadioGroup.hide();
        description.controls.setText(location.description.get());
    }

    @Override
    public void onClick(int btnId, String tag) {
        if(btnId == editButton.controls.getId()){
            locationDialog.initUpdateState(location.name.get(), location.description.get());
            locationDialog.dialog.setListener(this);
            locationDialog.show();
            return;
        }
        if(btnId == musicControl.controls.getId()){
            musicControl.setBackgroundMusicState(location.getMusicHashes());
        }
    }

    @Override
    public void onLongClick(int btnId) {
        if(btnId == musicControl.controls.getId()){
            musicControl.openMusicConsole(location.getMusicHashes());
            return;
        }
        if(btnId == previewControl.controls.getId()){
            previewControl.controls.openImageSelector(this);
        }
    }

    @Override
    public void onResolve() {
        location.name.set(locationDialog.getName());
        location.description.set(locationDialog.getDescription());
        location.save();
        UIToolbar.setTitle(this, getLocalizationByKey(LocationLocale.Keys.name), location.name.get());
        description.controls.setText(locationDialog.getDescription());
    }

    @Override
    public void onReject() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == WIDGET_RESULT_CODE && data != null){
                location.setMusicPathsArray(data.getStringArrayExtra(SELECTED_IDS_INTENT_EXTRA_NAME));
                location.save();
            }
            if(requestCode == IMAGE_WIDGET_INTENT_RESULT && data != null){
                String[] selectedItems = data.getStringArrayExtra(SELECTED_IDS_INTENT_EXTRA_NAME);
                if(selectedItems !=null && selectedItems.length > 0){
                    Uri fileUri = Uri.parse(Uri.decode(selectedItems[0]));
                    location.previewId.set(fileUri.getPath());
                    previewControl.controls.setFile(new File(fileUri.getPath()));
                } else {
                    location.previewId.set("");
                    previewControl.controls.clearPreview();
                }
                location.save();
            }
        }
    }
}