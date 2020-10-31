package com.masterhelper.ux.pages.scenes;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.db.repositories.scenes.SceneModel;
import com.masterhelper.db.repositories.scenes.SceneRepository;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.pages.events.PageEventsList;
import com.masterhelper.ux.pages.journeys.JourneyLocale;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.ux.pages.scenes.list.ListItemScene;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import static com.masterhelper.ux.pages.journeys.PageJourneyList.INTENT_JOURNEY_ID;
import static com.masterhelper.ux.pages.scenes.SceneLocale.getLocalizationByKey;

public class PageSceneList extends AppCompatActivity implements ListItemEvents {
  public static final String INTENT_SCENE_ID = "sceneId";
  FragmentManager mn;
  SceneRepository repository;
  ComponentUIList<SceneModel> list;

  ComponentUIDialog dialog;

  ComponentUIDialog initDialog(int nameMaxLength, int descriptionMaxLength){
    ComponentUIDialog dialog = new ComponentUIDialog(this);
    dialog.pNameLabel.show();
    dialog.pNameLabel.setText(SceneLocale.getLocalizationByKey(SceneLocale.Keys.sceneName));

    dialog.pNameField.setText("");
    dialog.pNameField.setMaxLength(nameMaxLength);
    dialog.pNameField.show();

    dialog.pDescriptionLabel.show();
    dialog.pDescriptionLabel.setText(SceneLocale.getLocalizationByKey(SceneLocale.Keys.shortDescription));

    dialog.pDescriptionField.setText("");
    dialog.pDescriptionField.setMaxLength(descriptionMaxLength);
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

  void initList(SceneModel[] items){
    list = ComponentUIList.cast(mn.findFragmentById(R.id.SCENE_LIST));
    list.controls.setAdapter(items, new ListItemScene(getSupportFragmentManager(), this));
  }

  private void onCreateItem(String text, String description) {
    SceneModel newScene = repository.getDraftRecord();
    newScene.name.set(text);
    newScene.description.set(description);
    newScene.save();
    list.controls.add(newScene);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_scene_list);
    UIToolbar.setTitle(this, getLocalizationByKey(SceneLocale.Keys.listCaption), null);
    String journeyId = getIntent().getStringExtra(INTENT_JOURNEY_ID);
    mn = getSupportFragmentManager();
    repository = GlobalApplication.getAppDB().sceneRepository;
    repository.setJourneyId(journeyId);
    dialog = initDialog(
      repository.getNameLength(),
      repository.getDescriptionLength()
    );
    initNewItemButton(dialog);
    initList(repository.list(0,0));
  }

  @Override
  public void onUpdate(int listItemId) {
    dialog.setTitle(JourneyLocale.getLocalizationByKey(JourneyLocale.Keys.updateJourney));
    SceneModel item = list.controls.getItemByListId(listItemId);
    dialog.pDescriptionField.setText(item.description.get());
    dialog.pNameField.setText(item.name.get());
    dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        item.name.set(dialog.pNameField.getText());
        item.description.set(dialog.pDescriptionField.getText());
        item.save();
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
    SceneModel item = list.controls.getItemByListId(listItemId);
    list.controls.delete(listItemId);
    repository.removeRecord(item.id);
  }

  @Override
  public void onSelect(int listItemId) {
    SceneModel item = list.controls.getItemByListId(listItemId);
    Intent eventIntent = new Intent(this, PageEventsList.class);
    eventIntent.putExtra(INTENT_SCENE_ID, item.id.get().toString());
    startActivity(eventIntent);
  }
}
