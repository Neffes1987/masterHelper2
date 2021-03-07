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
import com.masterhelper.locations.tabs.ITabs;
import com.masterhelper.media.Formats;
import com.masterhelper.media.filesystem.AppFilesLibrary;
import com.masterhelper.media.filesystem.AudioPlayer;
import com.masterhelper.media.filesystem.EffectsPlayer;
import com.masterhelper.media.music_player.IMusicPlayerWidget;
import com.masterhelper.media.repository.MediaModel;
import com.masterhelper.media.repository.MediaRepository;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.image.ComponentUIImage;
import com.masterhelper.ux.components.library.list.CommonHolderPayloadData;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.components.library.list.ListItemControlsListener;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.masterhelper.media.filesystem.AppFilesLibrary.FORMAT_AUDIO_PATH;
import static com.masterhelper.ux.components.library.image.Image.IMAGE_WIDGET_INTENT_RESULT;
import static com.masterhelper.media.FileViewerWidget.SELECTED_IDS_INTENT_EXTRA_NAME;
import static com.masterhelper.locations.PageLocationsList.INTENT_LOCATION_ID;
import static com.masterhelper.ux.components.library.list.CommonItem.Flags.*;
import static com.masterhelper.ux.components.library.list.CommonItem.Flags.showPlay;

public class PageControlsListener extends AppCompatActivity implements SetBtnLocation, ComponentUIDialog.DialogClickListener, ITabs, ListItemControlsListener, IMusicPlayerWidget {
    private int currentSelectedTab = 1;
    private ComponentUIFloatingButton editButton;
    private ComponentUILabel description;
    private ComponentUILabel name;
    private ComponentUIImage previewControl;
    private ComponentUIDialog locationDialog;
    private LocationModel location;
    private ComponentUIList mediaFilesList;
    AudioPlayer player;
    EffectsPlayer effectsPlayer;

    FragmentManager mn;
    private int currentAudioTrack = 0;

    public void setCurrentAudioTrack(int currentAudioTrack) {
        this.currentAudioTrack = currentAudioTrack;
    }

    public int getCurrentAudioTrack() {
        return currentAudioTrack;
    }

    LocationRepository repository;
    MediaRepository mediaRepository;
    AppFilesLibrary library;

    void initEditItemButton() {
        editButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.LOCATION_EDIT_BTN));
        editButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.pencil));
        editButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
        editButton.controls.setOnClick(this);
        editButton.controls.setId(View.generateViewId());
    }

    void initImageWidget(){
        previewControl = ComponentUIImage.cast(mn.findFragmentById(R.id.LOCATION_PREVIEW_ID));
        previewControl.controls.setOnClick(this);
        previewControl.controls.setId(View.generateViewId());
        if (location.previewUrl != null && location.previewUrl.get() != null && location.previewUrl.get().length() > 0) {
            previewControl.controls.setFile(new File(location.previewUrl.get()));
        }
    }

    void initDescriptionLabel() {
        description = ComponentUILabel.cast(mn.findFragmentById(R.id.LOCATION_DESCRIPTION_VIEW_ID));
    }

    void initNameLabel() {
        name = ComponentUILabel.cast(mn.findFragmentById(R.id.LOCATION_NAME_VIEW_ID));
    }


    ComponentUIDialog initDialog(int nameMaxLength, int descriptionLength) {
        ComponentUIDialog dialog = new ComponentUIDialog(this);
        dialog.pNameLabel.show();
        dialog.pNameLabel.setText(LocationLocale.getLocalizationByKey(LocationLocale.Keys.locationName));

        dialog.pNameField.setText("");
        dialog.pNameField.setMaxLength(nameMaxLength);
        dialog.pNameField.show();

        dialog.pDescriptionLabel.show();
        dialog.pDescriptionLabel.setText(LocationLocale.getLocalizationByKey(LocationLocale.Keys.shortDescription));

        dialog.pDescriptionField.setText("");
        dialog.pDescriptionLabel.setMaxLength(descriptionLength);
        dialog.pDescriptionField.show();
        return dialog;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_location);
        repository = GlobalApplication.getAppDB().locationRepository;
        mediaRepository = GlobalApplication.getAppDB().mediaRepository;
        location = repository.getRecord(getIntent().getStringExtra(INTENT_LOCATION_ID));
        player = GlobalApplication.getPlayer();
        effectsPlayer = GlobalApplication.getEffectsPlayer();

        mn = getSupportFragmentManager();
        initEditItemButton();


        initImageWidget();
        initDescriptionLabel();
        initNameLabel();
        locationDialog = initDialog(repository.getNameLength(), repository.getDescriptionLength());
        description.controls.setText(location.description.get());
        name.controls.setText(location.name.get());

        library = new AppFilesLibrary(FORMAT_AUDIO_PATH, Formats.audio);
        setSoundsMusicList(true);
    }

    void setSoundsMusicList(Boolean isBackground) {
        ArrayList<CommonHolderPayloadData> libraryFileDataArrayList = getLibraryItems(library.getFilesLibraryList(), isBackground ? location.getMusicIds() : location.getMusicEffectsIds());
        mediaFilesList = initList(libraryFileDataArrayList);
        reInitMusicPlayer();
    }

    void stopTrack() {
        if (getCurrentAudioTrack() != 0) {
            CommonHolderPayloadData currentPlayedTrack = mediaFilesList.controls.getItemByListId(getCurrentAudioTrack());
            player.stopMediaRecord();
            currentPlayedTrack.isPlayed = false;
            mediaFilesList.controls.update(currentPlayedTrack, getCurrentAudioTrack());
            setCurrentAudioTrack(0);
        }
    }

    void startRecord(int listItemId) {
        CommonHolderPayloadData listRecord = mediaFilesList.controls.getItemByListId(listItemId);
        player.startMediaRecord(new File(listRecord.getPreviewUrl()));
        listRecord.isPlayed = true;
        setCurrentAudioTrack(listItemId);
        mediaFilesList.controls.update(listRecord, listItemId);
    }

    @Override
    public void onClick(int btnId, String tag) {
        if (btnId == editButton.controls.getId() && currentSelectedTab == 1) {
            locationDialog.pNameField.setText(location.name.get());
            locationDialog.pDescriptionField.setText(location.description.get());
            locationDialog.setListener(this);
            locationDialog.show();
        }

        if (btnId == editButton.controls.getId() && currentSelectedTab == 2) {
            location.save();
            reInitMusicPlayer();
        }
        if (btnId == editButton.controls.getId() && currentSelectedTab == 3) {
            location.save();
        }
    }

    @Override
    public void onLongClick(int btnId) {
        if(btnId == previewControl.controls.getId()){
            previewControl.controls.openImageSelector(this);
        }
    }

    @Override
    public void onResolve() {
        location.name.set(locationDialog.pNameField.getText());
        location.description.set(locationDialog.pDescriptionField.getText());
        location.save();
        description.controls.setText(location.description.get());
        name.controls.setText(location.name.get());
    }

    @Override
    public void onReject() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_WIDGET_INTENT_RESULT && data != null){
                String[] selectedItems = data.getStringArrayExtra(SELECTED_IDS_INTENT_EXTRA_NAME);
                if(selectedItems !=null && selectedItems.length > 0){
                    MediaModel media = mediaRepository.getRecord(selectedItems[0]);
                    Uri fileUri = Uri.parse(media.filePath.get());
                    location.previewId.set(selectedItems[0]);
                    location.previewUrl.set(fileUri.getPath());
                    previewControl.controls.setFile(new File(fileUri.getPath()));
                } else {
                    location.previewId.set("");
                    previewControl.controls.clearPreview();
                }
                location.save();
            }
        }
    }

    ArrayList<CommonHolderPayloadData> getLibraryItems(MediaModel[] items, String[] defaultSelectedFilePaths) {
        ArrayList<CommonHolderPayloadData> libraryFileDataArrayList = new ArrayList<>();
        ArrayList<String> selectedFilesByUri = new ArrayList<>();
        if (defaultSelectedFilePaths != null && defaultSelectedFilePaths.length > 0) {
            selectedFilesByUri.addAll(Arrays.asList(defaultSelectedFilePaths));
        }

        for (MediaModel model : items) {
            boolean isSelected = selectedFilesByUri.contains(model.id.get().toString());
            CommonHolderPayloadData fileData = new CommonHolderPayloadData(model.id, model.fileName.get(), model.filePath.get(), isSelected, false);
            libraryFileDataArrayList.add(fileData);
        }

        return libraryFileDataArrayList;
    }

    ComponentUIList initList(ArrayList<CommonHolderPayloadData> items) {
        ComponentUIList list = ComponentUIList.cast(mn.findFragmentById(R.id.LOCATION_MUSIC_LIST_ID));
        final ArrayList<CommonItem.Flags> flags = new ArrayList<>();
        flags.add(showPlay);
        flags.add(showSelection);
        list.controls.setAdapter(items, this, flags);
        return list;
    }

    void setVisibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateSelectedTab(int newCurrentTab) {
        View meta = findViewById(R.id.LOCATION_META_CONTAINER_ID);
        View music = findViewById(R.id.LOCATION_MUSIC_CONTAINER_ID);
        currentSelectedTab = newCurrentTab;
        switch (newCurrentTab) {
            case 1:
                setVisibility(meta, true);
                setVisibility(music, false);
                editButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.pencil));
                break;
            case 2:
                setVisibility(meta, false);
                setVisibility(music, true);
                editButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.done));
                setSoundsMusicList(true);
                break;
            case 3:
                setVisibility(meta, false);
                setVisibility(music, true);
                editButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.done));
                setSoundsMusicList(false);
                break;
        }

    }

    @Override
    public void onUpdate(int listItemId) {

    }

    @Override
    public void onDelete(int listItemId) {

    }

    void reInitMusicPlayer() {
        View locationPlayerWidget = findViewById(R.id.LOCATION_MUSIC_PLAYER_WIDGET_ID);

        if (location.getMusicIds().length == 0) {
            locationPlayerWidget.setVisibility(View.GONE);
            return;
        }
        locationPlayerWidget.setVisibility(View.VISIBLE);


        MediaModel[] mediaModels = library.getFilesLibraryList();

        Collection<String> currentBackgroundUris = new ArrayList<>();
        Collection<String> currentBackgroundSelectedList = new ArrayList<>(Arrays.asList(location.getMusicIds()));
        for (MediaModel model : mediaModels) {
            if (currentBackgroundSelectedList.contains(model.id.toString())) {
                currentBackgroundUris.add(model.filePath.get());
            }
        }

        player.setMediaListOfUri(currentBackgroundUris.toArray(new String[0]));

        Collection<String> currentEffectsUris = new ArrayList<>();
        Collection<String> currentEffectsSelectedList = new ArrayList<>(Arrays.asList(location.getMusicEffectsIds()));
        for (MediaModel model : mediaModels) {
            if (currentEffectsSelectedList.contains(model.id.toString())) {
                currentEffectsUris.add(model.filePath.get());
            }
        }

        effectsPlayer.setMediaListOfUri(currentEffectsUris.toArray(new String[0]));
    }

    @Override
    public void onSelect(int listItemId) {
        Collection<String> currentSelectedList = new ArrayList<>();

        if (currentSelectedTab == 2) {
            currentSelectedList = new ArrayList<>(Arrays.asList(location.getMusicIds()));
        }

        if (currentSelectedTab == 3) {
            currentSelectedList = new ArrayList<>(Arrays.asList(location.getMusicEffectsIds()));
        }

        MediaModel selectedModel = library.getFilesLibraryList()[listItemId - 1];
        String selectedId = selectedModel.id.toString();
        if (!currentSelectedList.contains(selectedId)) {
            currentSelectedList.add(selectedId);
        } else {
            currentSelectedList.remove(selectedId);
        }

        if (currentSelectedTab == 2) {
            location.setMusicIdsArray(currentSelectedList.toArray(new String[0]));
        }

        if (currentSelectedTab == 3) {
            location.setMusicEffectsIdsArray(currentSelectedList.toArray(new String[0]));
        }

    }

    @Override
    public void onPlay(int listItemId) {
        if (listItemId == currentAudioTrack) {
            stopTrack();
            return;
        }
        stopTrack();
        startRecord(listItemId);
    }

    @Override
    public void next() {
        player.startNextMediaFile();
        effectsPlayer.next();
    }

    @Override
    public void play() {
        player.startNextMediaFile();
        effectsPlayer.start();
    }

    @Override
    public void stop() {
        player.stopMediaRecord();
        effectsPlayer.stop();
    }

    @Override
    public String getCurrentTrackName() {
        File currentTrack = library.getFileByPosition(player.getCurrentAudioIndex());
        if (currentTrack == null) {
            return "";
        }
        return currentTrack.getName();
    }

    @Override
    public boolean checkIsPlaying() {
        return player.isPlayed();
    }
}