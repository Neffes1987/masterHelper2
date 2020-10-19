package com.masterhelper.ux.pages.journeys;

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
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.ux.pages.journeys.list.ListItemJourney;
import com.masterhelper.ux.pages.journeys.list.UIJourneyItemData;
import com.masterhelper.ux.pages.scenes.PageSceneList;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import java.util.ArrayList;

import static com.masterhelper.ux.pages.journeys.JourneyLocale.getLocalizationByKey;

public class PageJourneyList extends AppCompatActivity implements ListItemEvents {
  FragmentManager mn;

  ComponentUIDialog dialog;
  ComponentUIList<UIJourneyItemData> list;

  ComponentUIDialog initDialog(){
    ComponentUIDialog dialog = new ComponentUIDialog(this);
    dialog.pNameField.setText("");
    dialog.pNameField.show();
    return dialog;
  }

  void initNewItemButton(ComponentUIDialog itemDialog){
    ComponentUIFloatingButton floatingButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.JOURNEY_ADD_NEW_ITEM_BUTTON));
    floatingButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.add));
    floatingButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
    floatingButton.controls.setOnClick(new SetBtnEvent() {
      @Override
      public void onClick(int btnId, String tag) {
        itemDialog.setTitle(getLocalizationByKey(JourneyLocale.Keys.createJourney));
        itemDialog.setListener(new ComponentUIDialog.DialogClickListener() {
          @Override
          public void onResolve() {
            onCreateItem(itemDialog.pNameField.getText());
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

  ComponentUIList<UIJourneyItemData> initList(ArrayList<UIJourneyItemData> items){
    ComponentUIList<UIJourneyItemData> list = ComponentUIList.cast(mn.findFragmentById(R.id.JOURMEY_ITEMS_LIST));
    list.controls.setAdapter(items, new ListItemJourney(getSupportFragmentManager(), this));
    return list;
  }

  void onCreateItem(String text){
    Toast.makeText(PageJourneyList.this, text, Toast.LENGTH_SHORT).show();
  }

  void onUpdateItem(String text){
    Toast.makeText(PageJourneyList.this, text, Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_journey_list);
    mn = getSupportFragmentManager();

    UIToolbar.setTitle(this, getLocalizationByKey(JourneyLocale.Keys.listCaption));

    ArrayList<UIJourneyItemData> items = new ArrayList<>();
    UIJourneyItemData item = new UIJourneyItemData();

    item.setText("test list item111111");
    items.add(item);
    items.add(item);
    items.add(item);
    items.add(item);
    items.add(item);
    items.add(item);

    // init page components
    dialog = initDialog();
    list = initList(items);
    initNewItemButton(dialog);
  }

  @Override
  public void onUpdate(int listItemId) {
    dialog.setTitle(getLocalizationByKey(JourneyLocale.Keys.updateJourney));
    dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        UIJourneyItemData item = list.controls.getItemByListId(listItemId);
        item.setText(dialog.pNameField.getText());
        onUpdateItem(item.getText());
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
    Toast.makeText(PageJourneyList.this, "delete " + listItemId, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onSelect(int listItemId) {
    Intent sceneIntent = new Intent(this, PageSceneList.class);
    startActivity(sceneIntent);
  }
}
