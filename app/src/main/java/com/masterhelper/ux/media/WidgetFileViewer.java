package com.masterhelper.ux.media;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.filesystem.AppFilesLibrary;
import com.masterhelper.filesystem.AudioPlayer;
import com.masterhelper.filesystem.LibraryFileData;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.ux.media.list.ListItemFile;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class WidgetFileViewer extends AppCompatActivity implements SetBtnEvent, ListItemEvents {
  String[] permissions = new String[] {
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
  };

  private static final int PICK_AUDIO_FILE = 1;
  private static final String PICK_AUDIO_TYPE = "audio/*";

  final String FORMAT_IMAGE_PATH = "/images";
  final String FORMAT_AUDIO_PATH = "/audio";
  AppFilesLibrary library;
  AudioPlayer player;

  public static final int WIDGET_RESULT_CODE = 1000;
  public static final String FORMAT_INTENT_EXTRA_NAME = "format";
  public static final String LAYOUT_INTENT_EXTRA_NAME = "layout";
  public static final String SELECTED_IDS_INTENT_EXTRA_NAME = "selectedPaths";

  private Formats format;
  String widgetTitle;
  String widgetWorkingDir;
  private Layout layout;
  private ComponentUIFloatingButton newItemButton;
  private ComponentUIFloatingButton applyItemsButton;
  private ComponentUIList<LibraryFileData> list;
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
    switch (this.format){
      case audio:
        widgetWorkingDir = FORMAT_AUDIO_PATH;
        widgetTitle = FilesLocale.getLocalizationByKey(FilesLocale.Keys.audiosListCaption);
        break;
      case imagePng:
        widgetWorkingDir = FORMAT_IMAGE_PATH;
        widgetTitle = FilesLocale.getLocalizationByKey(FilesLocale.Keys.imagesListCaption);
        break;
      default: throw new Error("WidgetFileViewer: wrong type");
    }
  }

  void setLayout(String layout){
    this.layout = Layout.valueOf(layout);
  }

  ArrayList<LibraryFileData> getLibraryItems(File[] items, String[] defaultSelectedFilePaths){
    ArrayList<LibraryFileData> libraryFileDataArrayList = new ArrayList<>();
    ArrayList<String> selectedPaths = new ArrayList<>();
    if(defaultSelectedFilePaths != null && defaultSelectedFilePaths.length > 0){
      selectedPaths.addAll(Arrays.asList(defaultSelectedFilePaths));
    }

    for (File file: items) {
      String filePath = file.getPath();
      libraryFileDataArrayList.add(new LibraryFileData("", filePath, selectedPaths.contains(filePath), false));
    }

    return libraryFileDataArrayList;
  }

  String[] getSelectedItemsFileNames(ArrayList<LibraryFileData> currentList){
    ArrayList<String> selectedPaths = new ArrayList<>();

    for (LibraryFileData libraryRecord: currentList) {
      if(libraryRecord.isSelected){
        selectedPaths.add(libraryRecord.getFileName());
      }
    }

    return selectedPaths.toArray(new String[0]);
  }

  void initAddButton(){
    newItemButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.WIDGET_FILES_ADD_ID));
    newItemButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.add));
    newItemButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
    newItemButton.controls.setId(View.generateViewId());
    newItemButton.controls.setOnClick(this);
  }

  void initApplyButton(){
    applyItemsButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.WIDGET_FILES_APPLY_BTN_ID));
    applyItemsButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.done));
    applyItemsButton.controls.setIconColor(ResourceColors.ResourceColorType.primary);
    applyItemsButton.controls.setId(View.generateViewId());
    applyItemsButton.controls.setOnClick(this);
  }

  ComponentUIList<LibraryFileData> initList(ArrayList<LibraryFileData> items){
    ComponentUIList<LibraryFileData> list = ComponentUIList.cast(mn.findFragmentById(R.id.WIDGET_FILES_LIST_ID));
    list.controls.setAdapter(items.toArray(new LibraryFileData[0]), new ListItemFile(getSupportFragmentManager(), this, layout == Layout.global));
    return list;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent inputIntent = getIntent();
    setContentView(R.layout.activity_widget_file_viewer);
    setFormat(inputIntent.getStringExtra(FORMAT_INTENT_EXTRA_NAME));
    setLayout(inputIntent.getStringExtra(LAYOUT_INTENT_EXTRA_NAME));
    mn = getSupportFragmentManager();
    library = new AppFilesLibrary(widgetWorkingDir);

    UIToolbar.setTitle(this, widgetTitle, "");
    initAddButton();
    initApplyButton();
    player = GlobalApplication.getPlayer();
    stopTrack();
    ArrayList<LibraryFileData> libraryFileDataArrayList = getLibraryItems(library.getFilesLibraryList(), inputIntent.getStringArrayExtra(SELECTED_IDS_INTENT_EXTRA_NAME));
    list = initList(libraryFileDataArrayList);
  }

  @Override
  protected void onStop() {
    super.onStop();
    stopTrack();
  }

  void stopTrack(){
    if(getCurrentAudioTrack() != 0){
      LibraryFileData currentPlayedTrack = list.controls.getItemByListId(getCurrentAudioTrack());
      player.stopMediaRecord();
      currentPlayedTrack.isPlayed = false;
      list.controls.update(currentPlayedTrack, getCurrentAudioTrack());
      setCurrentAudioTrack(0);
    }
  }

  void startRecord(int listItemId){
    LibraryFileData listRecord = list.controls.getItemByListId(listItemId);
    player.startMediaRecord(listRecord.getFile());
    listRecord.isPlayed = true;
    setCurrentAudioTrack(listItemId);
    list.controls.update(listRecord, listItemId);
  }

  /**
   * click callback for short click event
   *
   * @param btnId - element unique id that fired event
   * @param tag   - element unique tag for searching in list
   */
  @Override
  public void onClick(int btnId, String tag) {
    if(btnId == newItemButton.controls.getId()){
      if(Build.VERSION.SDK_INT>22){
        requestPermissions(permissions, 1);
        return;
      }
      StartFilePickerIntent();
      return;
    }
    if(btnId == applyItemsButton.controls.getId()){
      String[] currentSelectedFiles = getSelectedItemsFileNames(list.controls.getList());
      Intent returnedIntent = new Intent();
      returnedIntent.putExtra(SELECTED_IDS_INTENT_EXTRA_NAME, currentSelectedFiles);
      setResult(WIDGET_RESULT_CODE, returnedIntent);
      finish();
    }

  }

  /**
   * click callback for long click event
   *
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {

  }

  @Override
  public void onUpdate(int listItemId) {
    if(format == Formats.audio){
      if(listItemId == currentAudioTrack){
        stopTrack();
        return;
      }
      stopTrack();
      startRecord(listItemId);
    }

  }

  @Override
  public void onDelete(int listItemId) {
    stopTrack();
    LibraryFileData item = list.controls.getItemByListId(listItemId);
    library.deleteRecordFromMediaLibrary(item.getFile());
    list.controls.delete(listItemId);
  }

  @Override
  public void onSelect(int listItemId) {
    LibraryFileData selectedListItem = list.controls.getItemByListId(listItemId);
    selectedListItem.isSelected = !selectedListItem.isSelected;
    list.controls.update(selectedListItem, listItemId);
  }

  public enum Formats{
    imagePng,
    audio
  }

  public enum Layout{
    selectable,
    global
  }

  public static Intent getWidgetIntent(Context context, Formats format, Layout layout, String[] selections){
    Intent intent = new Intent(context, WidgetFileViewer.class);
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
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    HashSet<Uri> selectedFilesPaths = new HashSet<>();
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode != RESULT_OK){
      return;
    }

    if(requestCode == PICK_AUDIO_FILE){
      assert data != null;
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
    library.updateMediaLibrary();
    String[] currentSelectedFiles = getSelectedItemsFileNames(list.controls.getList());
    list = initList(getLibraryItems(library.getFilesLibraryList(), currentSelectedFiles));
  }

}