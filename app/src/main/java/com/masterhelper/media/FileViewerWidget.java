package com.masterhelper.media;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.media.filesystem.AppFilesLibrary;
import com.masterhelper.media.filesystem.AudioPlayer;
import com.masterhelper.media.filesystem.FilesLocale;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.media.repository.MediaModel;
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.dialog.TextDialog;
import com.masterhelper.ux.components.library.list.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static com.masterhelper.media.filesystem.AppFilesLibrary.FORMAT_AUDIO_PATH;
import static com.masterhelper.media.filesystem.AppFilesLibrary.FORMAT_IMAGE_PATH;
import static com.masterhelper.ux.components.library.list.CommonItem.Flags.*;

public class FileViewerWidget extends AppMenuActivity implements ListItemControlsListener {
  String[] permissions = new String[]{
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
  };

  private static final int PICK_AUDIO_FILE = 1;
  private static final int PICK_IMAGE_FILE = 2;
  private static final String PICK_AUDIO_TYPE = "audio/*";
  private static final String PICK_IMAGE_TYPE = "image/*";
  AppFilesLibrary library;
  AudioPlayer player;

  public static final String FORMAT_INTENT_EXTRA_NAME = "format";
  public static final String LAYOUT_INTENT_EXTRA_NAME = "layout";
  public static final String SELECTED_IDS_INTENT_EXTRA_NAME = "selectedPaths";

  private Formats format;
  String widgetTitle;
  String widgetWorkingDir;
  private Layout layout;
  private ComponentUIList list;
  private FragmentManager mn;

  private int currentAudioTrack = 0;

  public void setCurrentAudioTrack(int currentAudioTrack) {
    this.currentAudioTrack = currentAudioTrack;
  }

  public int getCurrentAudioTrack() {
    return currentAudioTrack;
  }

  void setFormat(String format) {
    this.format = Formats.valueOf(format);
    switch (this.format) {
      case audio:
        widgetWorkingDir = FORMAT_AUDIO_PATH;
        widgetTitle = FilesLocale.getLocalizationByKey(FilesLocale.Keys.audiosListCaption);
        break;
      case imagePng:
        widgetWorkingDir = FORMAT_IMAGE_PATH;
        widgetTitle = FilesLocale.getLocalizationByKey(FilesLocale.Keys.imagesListCaption);
        break;
      default:
        throw new Error("WidgetFileViewer: wrong type");
    }
  }

  void setLayout(String layout) {
    this.layout = Layout.valueOf(layout);
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

  String[] getSelectedItemsFileUri(ArrayList<CommonHolderPayloadData> currentList) {
    ArrayList<String> selectedPaths = new ArrayList<>();

    for (CommonHolderPayloadData libraryRecord : currentList) {
      if (libraryRecord.isSelected) {
        selectedPaths.add(libraryRecord.getId().toString());
      }
    }

    return selectedPaths.toArray(new String[0]);
  }

  ComponentUIList initList(ArrayList<CommonHolderPayloadData> items) {
    ComponentUIList list = ComponentUIList.cast(mn.findFragmentById(R.id.ITEMS_LIST));
    final ArrayList<CommonItem.Flags> flags = new ArrayList<>();

    if (layout == Layout.global) {
      flags.add(showDelete);
      flags.add(showEdit);
    } else {
      flags.add(showSelection);
    }

    if (format == Formats.imagePng) {
      flags.add(showPreview);
    } else {
      flags.add(showPlay);
    }


    list.controls.setAdapter(items, this, flags);
    return list;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent inputIntent = getIntent();
    setContentView(R.layout.activity_page_simple_list);
    setFormat(inputIntent.getStringExtra(FORMAT_INTENT_EXTRA_NAME));
    setLayout(inputIntent.getStringExtra(LAYOUT_INTENT_EXTRA_NAME));
    setHiddenItemsCode(MENU_ITEMS_CODES.media);
    setItemControlTitle(FilesLocale.getLocalizationByKey(FilesLocale.Keys.addNewMedia));
    mn = getSupportFragmentManager();
    library = new AppFilesLibrary(widgetWorkingDir, format);

    UIToolbar.setTitle(this, widgetTitle, "");
    player = GlobalApplication.getPlayer();
    stopTrack();
    ArrayList<CommonHolderPayloadData> libraryFileDataArrayList = getLibraryItems(library.getFilesLibraryList(), inputIntent.getStringArrayExtra(SELECTED_IDS_INTENT_EXTRA_NAME));
    list = initList(libraryFileDataArrayList);
  }

  @Override
  protected void onStop() {
    super.onStop();
    stopTrack();
  }

  void stopTrack(){
    if(getCurrentAudioTrack() != 0){
      CommonHolderPayloadData currentPlayedTrack = list.controls.getItemByListId(getCurrentAudioTrack());
      player.stopMediaRecord();
      currentPlayedTrack.isPlayed = false;
      list.controls.update(currentPlayedTrack, getCurrentAudioTrack());
      setCurrentAudioTrack(0);
    }
  }

  void startRecord(int listItemId) {
    CommonHolderPayloadData listRecord = list.controls.getItemByListId(listItemId);
    player.startMediaRecord(new File(listRecord.getPreviewUrl()));
    listRecord.isPlayed = true;
    setCurrentAudioTrack(listItemId);
    list.controls.update(listRecord, listItemId);
  }

  public void onUpdate(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    TextDialog dialog = new TextDialog(this, FilesLocale.getLocalizationByKey(FilesLocale.Keys.editFileName), 0, item.getTitle(), (result) -> {
      library.updateFileName(item.getId().toString(), result);
      item.setTitle(result);
      list.controls.update(item, listItemId);
    });

    dialog.show();
  }

  public void onDelete(int listItemId) {
    stopTrack();
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    library.deleteRecordFromMediaLibrary(item.getId());
    list.controls.delete(listItemId);
  }

  void checkSelectedItem(int listItemId){
    CommonHolderPayloadData selectedListItem = list.controls.getItemByListId(listItemId);
    selectedListItem.isSelected = !selectedListItem.isSelected;
    list.controls.update(selectedListItem, listItemId);
  }

  void returnSelectedItem(int listItemId) {
    CommonHolderPayloadData selectedListItem = list.controls.getItemByListId(listItemId);
    String[] currentSelectedFiles = new String[]{selectedListItem.getId().toString()};
    Intent returnedIntent = new Intent();
    returnedIntent.putExtra(SELECTED_IDS_INTENT_EXTRA_NAME, currentSelectedFiles);
    setResult(RESULT_OK, returnedIntent);
    finish();
  }

  public void onSelect(int listItemId) {
    if (layout == Layout.selectable) {
      checkSelectedItem(listItemId);
      return;
    }
    returnSelectedItem(listItemId);
  }

  public void onPlay(int listItemId) {
    if (format == Formats.audio) {
      if (listItemId == currentAudioTrack) {
        stopTrack();
        return;
      }
      stopTrack();
      startRecord(listItemId);
    }
  }

  @Override
  protected void onAppBarMenuItemControl() {
    if (Build.VERSION.SDK_INT > 22) {
      requestPermissions(permissions, 1);
      return;
    }
    StartFilePickerIntent();
  }

  @Override
  public void listItemChanged(ListItemActionCodes code, int listItemId) {
    switch (code) {
      case play:
        onPlay(listItemId);
        break;
      case select:
        onSelect(listItemId);
        break;
      case delete:
        onDelete(listItemId);
        break;
      case update:
        onUpdate(listItemId);
        break;
    }
  }

  public enum Layout {
    selectable,
    global
  }

  public static Intent getWidgetIntent(Context context, Formats format, Layout layout, String[] selections) {
    Intent intent = new Intent(context, FileViewerWidget.class);
    intent.putExtra(FORMAT_INTENT_EXTRA_NAME, format.name());
    intent.putExtra(LAYOUT_INTENT_EXTRA_NAME, layout.name());
    intent.putExtra(SELECTED_IDS_INTENT_EXTRA_NAME, selections);
    return intent;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == 1) {
      if (!(grantResults.length > 0
        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
        Toast.makeText(this, "Permission denied to access your location.", Toast.LENGTH_SHORT).show();
        return;
      }
      StartFilePickerIntent();
    }
  }

  private void StartFilePickerIntent() {
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    if(format == Formats.audio){
      intent.setType(PICK_AUDIO_TYPE);
      startActivityForResult(intent, PICK_AUDIO_FILE);
    }
    if(format == Formats.imagePng){
      intent.setType(PICK_IMAGE_TYPE);
      startActivityForResult(intent, PICK_IMAGE_FILE);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    HashSet<Uri> selectedFilesPaths = new HashSet<>();
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode != RESULT_OK){
      return;
    }

    if(data != null){
      ClipData clipData = data.getClipData();
      if(clipData == null){
        selectedFilesPaths.add(data.getData());
      } else {
        for(int i=0; i<clipData.getItemCount(); i++){
          ClipData.Item item = clipData.getItemAt(i);
          Uri uri = item.getUri();
          selectedFilesPaths.add(uri);
        }
      }
    }

    library.copyFilesBunchToMediaLibrary(selectedFilesPaths.toArray(new Uri[0]));

    library.updateMediaLibrary(format);
    String[] currentSelectedFiles = getSelectedItemsFileUri(list.controls.getList());
    list = initList(getLibraryItems(library.getFilesLibraryList(), currentSelectedFiles));

    String alertTitle = FilesLocale.getLocalizationByKey(FilesLocale.Keys.removeSourceFiles);

    TextDialog dialog = new TextDialog(this, alertTitle, 0, "", (result) -> library.removeSourceFilesBunch(selectedFilesPaths.toArray(new Uri[0])));

    dialog.alert();
  }

}