package com.masterhelper.locations;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import androidx.annotation.Nullable;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.image.ComponentUIImage;
import com.masterhelper.ux.components.library.list.*;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.global.autocomplitefield.editField.EditTextField;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.masterhelper.goals.PageGoal.INTENT_GOAL_ID;
import static com.masterhelper.media.filesystem.AppFilesLibrary.FORMAT_AUDIO_PATH;
import static com.masterhelper.ux.components.library.image.Image.IMAGE_WIDGET_INTENT_RESULT;
import static com.masterhelper.media.FileViewerWidget.SELECTED_IDS_INTENT_EXTRA_NAME;
import static com.masterhelper.locations.PageLocationsList.INTENT_LOCATION_ID;
import static com.masterhelper.ux.components.library.list.CommonItem.Flags.*;

public class PageControlsListener extends AppMenuActivity implements SetBtnLocation, ITabs, ListItemControlsListener, IMusicPlayerWidget {
    private int currentSelectedTab = 1;
    private ComponentUILabel description;
    private ComponentUIImage previewControl;
    private LocationModel location;
    private ComponentUIList mediaFilesList;
    FloatingActionButton applyBtn;

    EditTextField nameEdit;
    EditTextField descriptionEdit;

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

    void setMetaDefault(LocationModel location) {
        nameEdit = new EditTextField(R.id.EDIT_LOCATION_NAME_FIELD_ID, this);
        nameEdit.setText(location.name.get());
        nameEdit.setMaxLength(repository.getNameLength());
        nameEdit.setMultiLIneText();

        descriptionEdit = new EditTextField(R.id.EDIT_LOCATION_DESCRIPTION_FIELD_ID, this);
        descriptionEdit.setText(location.description.get());
        descriptionEdit.setMaxLength(repository.getDescriptionLength());
        descriptionEdit.setMultiLIneText();
    }

    LocationRepository repository;
    MediaRepository mediaRepository;
    AppFilesLibrary library;

    void initImageWidget() {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_location);

        setHiddenItemsCode(MENU_ITEMS_CODES.create);

        repository = GlobalApplication.getAppDB().locationRepository;
        mediaRepository = GlobalApplication.getAppDB().mediaRepository;
        location = repository.getRecord(getIntent().getStringExtra(INTENT_LOCATION_ID));
        UIToolbar.setTitle(this, location.name.get(), null);
        applyBtn = findViewById(R.id.LOCATION_APPLY_BTN_ID);
        applyBtn.setOnClickListener(v -> onApplyBtnClicked());
        setVisibility(applyBtn, false);

        player = GlobalApplication.getPlayer();
        effectsPlayer = GlobalApplication.getEffectsPlayer();

        mn = getSupportFragmentManager();

        initImageWidget();
        initDescriptionLabel();
        description.controls.setText(location.description.get());

        library = new AppFilesLibrary(FORMAT_AUDIO_PATH, Formats.audio);
        reInitMusicPlayer();
    }

    void setSoundsMusicList(Boolean isBackground) {
        ArrayList<CommonHolderPayloadData> libraryFileDataArrayList = getLibraryItems(library.getFilesLibraryList(), isBackground ? location.getMusicIds() : location.getMusicEffectsIds());
        mediaFilesList = initList(libraryFileDataArrayList);
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

    }

    @Override
    public void onLongClick(int btnId) {
        if(btnId == previewControl.controls.getId()){
            previewControl.controls.openImageSelector(this);
        }
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

        libraryFileDataArrayList.sort((o1, o2) -> Boolean.compare(o2.isSelected, o1.isSelected));

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
        View edit = findViewById(R.id.LOCATION_EDIT_CONTAINER_ID);
        currentSelectedTab = newCurrentTab;
        switch (newCurrentTab) {
            case 1:
                setItemControlTitle("");
                setVisibility(meta, true);
                setVisibility(music, false);
                setVisibility(edit, false);
                setVisibility(applyBtn, false);
                break;
            case 2:
                setVisibility(meta, false);
                setVisibility(music, true);
                setSoundsMusicList(true);
                setVisibility(edit, false);
                setVisibility(applyBtn, true);
                break;
            case 3:
                setVisibility(meta, false);
                setVisibility(music, true);
                setSoundsMusicList(false);
                setVisibility(edit, false);
                setVisibility(applyBtn, true);
                break;
            case 4:
                setVisibility(meta, false);
                setVisibility(music, false);
                setSoundsMusicList(false);
                setVisibility(edit, true);
                setMetaDefault(location);
                setVisibility(applyBtn, true);
                break;
        }

    }

    String[] getSelectedMedia(String[] mediaListIds, boolean isFilePath) {
        MediaModel[] mediaModels = library.getFilesLibraryList();

        Collection<String> currentUris = new ArrayList<>();
        Collection<String> currentSelectedList = new ArrayList<>(Arrays.asList(mediaListIds));
        for (MediaModel model : mediaModels) {
            if (currentSelectedList.contains(model.id.toString())) {
                if (isFilePath) {
                    currentUris.add(model.filePath.get());
                } else {
                    currentUris.add(model.fileName.get());
                }
            }
        }

        return currentUris.toArray(new String[0]);
    }

    void reInitMusicPlayer() {
        View locationPlayerWidget = findViewById(R.id.LOCATION_MUSIC_PLAYER_WIDGET_ID);

        if (location.getMusicIds().length == 0) {
            locationPlayerWidget.setVisibility(View.GONE);
            return;
        }
        locationPlayerWidget.setVisibility(View.VISIBLE);

        player.stopMediaRecord();
        player.setMediaListOfUri(getSelectedMedia(location.getMusicIds(), true));
        effectsPlayer.setMediaListOfUri(getSelectedMedia(location.getMusicEffectsIds(), true));
    }

    public void onSelect(int listItemId) {
        Collection<String> currentSelectedList = new ArrayList<>();

        if (currentSelectedTab == 2) {
            currentSelectedList = new ArrayList<>(Arrays.asList(location.getMusicIds()));
        }

        if (currentSelectedTab == 3) {
            currentSelectedList = new ArrayList<>(Arrays.asList(location.getMusicEffectsIds()));
        }

        CommonHolderPayloadData listMediaItem = mediaFilesList.controls.getItemByListId(listItemId);
        String selectedId = listMediaItem.getId().toString();
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
        String[] fileNamesList = getSelectedMedia(location.getMusicIds(), false);
        if (fileNamesList.length == 0) {
            return "";
        }
        return fileNamesList[player.getCurrentAudioIndex()];
    }

    @Override
    public boolean checkIsPlaying() {
        return player.isPlayed();
    }

    void onApplyBtnClicked() {
        if (currentSelectedTab == 4) {
            location.name.set(nameEdit.getText().toString());
            location.description.set(descriptionEdit.getText().toString());
            location.save();
            description.controls.setText(location.description.get());
            UIToolbar.setTitle(this, location.name.get(), null);
        }

        if (currentSelectedTab == 2) {
            location.save();

            reInitMusicPlayer();
            setSoundsMusicList(true);
        }

        if (currentSelectedTab == 3) {
            location.save();
            setSoundsMusicList(false);
        }
    }

    @Override
    protected void onAppBarMenuItemControl() {
    }

  @Override
  public void listItemChanged(ListItemActionCodes code, int listItemId) {
    switch (code) {
      case play:
        onPlay(listItemId);
      case select:
        onSelect(listItemId);
    }
  }

  public static Intent getIntent(Activity context, String locationId) {
    Intent locationIntent = new Intent(context, PageControlsListener.class);
    locationIntent.putExtra(INTENT_LOCATION_ID, locationId);
    return locationIntent;
  }
}