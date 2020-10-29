package com.masterhelper.ux.pages.scenes;

import android.content.Intent;
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
import com.masterhelper.ux.pages.events.PageEventsList;
import com.masterhelper.ux.pages.journeys.JourneyLocale;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.ux.pages.scenes.list.ListItemScene;
import com.masterhelper.ux.pages.scenes.list.UISceneItemData;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import java.util.ArrayList;

import static com.masterhelper.ux.pages.scenes.SceneLocale.getLocalizationByKey;

public class PageSceneList extends AppCompatActivity implements ListItemEvents {

  FragmentManager mn;

  ComponentUIDialog dialog;
  ComponentUIList<UISceneItemData> list;

  ComponentUIDialog initDialog(){
    ComponentUIDialog dialog = new ComponentUIDialog(this);
    dialog.pNameLabel.show();
    dialog.pNameLabel.setText(SceneLocale.getLocalizationByKey(SceneLocale.Keys.sceneName));

    dialog.pNameField.setText("");
    dialog.pNameField.show();

    dialog.pDescriptionLabel.show();
    dialog.pDescriptionLabel.setText(SceneLocale.getLocalizationByKey(SceneLocale.Keys.shortDescription));

    dialog.pDescriptionField.setText("");
    dialog.pDescriptionField.show();
    return dialog;
  }

  void initNewItemButton(ComponentUIDialog itemDialog){
    ComponentUIFloatingButton floatingButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.SCENE_ADD_NEW_ITEM));
    floatingButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.add));
    floatingButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
    floatingButton.controls.setOnClick(new SetBtnEvent() {
      @Override
      public void onClick(int btnId, String tag) {
        itemDialog.setTitle(JourneyLocale.getLocalizationByKey(JourneyLocale.Keys.createJourney));
        itemDialog.setListener(new ComponentUIDialog.DialogClickListener() {
          @Override
          public void onResolve() {
            onCreateItem(itemDialog.pNameField.getText(), itemDialog.pDescriptionField.getText());
          }

          @Override
          public void onReject() {

          }
        });
        itemDialog.show();
      }

      @Override
      public void onLongClick(int btnId) {

      }
    });
  }

  ComponentUIList<UISceneItemData> initList(UISceneItemData[] items){
    ComponentUIList<UISceneItemData> list = ComponentUIList.cast(mn.findFragmentById(R.id.SCENE_LIST));
    list.controls.setAdapter(items, new ListItemScene(getSupportFragmentManager(), this));
    return list;
  }

  private void onCreateItem(String text, String description) {

  }

  private void onUpdateItem(String text, String description) {

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_scene_list);
    UIToolbar.setTitle(this, getLocalizationByKey(SceneLocale.Keys.listCaption), null);
    mn = getSupportFragmentManager();
    dialog = initDialog();
    initNewItemButton(dialog);

    ArrayList<UISceneItemData> items = new ArrayList<>();
    UISceneItemData item = new UISceneItemData("name", "description", 1, 5);

    item.setText("test list item111111");
    items.add(item);
    items.add(item);
    items.add(item);
    items.add(item);
    items.add(item);
    items.add(item);
    list = initList(items.toArray(new UISceneItemData[0]));
  }

  @Override
  public void onUpdate(int listItemId) {
    dialog.setTitle(JourneyLocale.getLocalizationByKey(JourneyLocale.Keys.updateJourney));
    UISceneItemData item = list.controls.getItemByListId(listItemId);
    dialog.pDescriptionField.setText(item.getDescription());
    dialog.pNameField.setText(item.getText());
    dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        item.setText(dialog.pNameField.getText());
        item.setDescription(dialog.pDescriptionField.getText());
        onUpdateItem(item.getText(), item.getDescription());
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
    Intent eventIntent = new Intent(this, PageEventsList.class);
    startActivity(eventIntent);
  }
}
