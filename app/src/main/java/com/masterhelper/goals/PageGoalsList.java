package com.masterhelper.goals;

import android.content.Intent;
import android.widget.TextView;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.goals.repository.GoalModel;
import com.masterhelper.goals.repository.GoalRepository;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.CommonHolderPayloadData;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.goals.acts.IActsTabs;
import com.masterhelper.journeys.JourneyLocale;
import com.masterhelper.ux.components.library.list.ListItemControlsListener;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import java.util.ArrayList;

import static com.masterhelper.goals.PageGoal.INTENT_GOAL_ID;
import static com.masterhelper.journeys.PageJourneyList.INTENT_JOURNEY_ID;
import static com.masterhelper.goals.GoalLocale.getLocalizationByKey;
import static com.masterhelper.ux.components.library.list.CommonHolderPayloadData.convertFromModels;

public class PageGoalsList extends AppMenuActivity implements ListItemControlsListener, IActsTabs {

  FragmentManager mn;
  GoalRepository repository;
  ComponentUIList list;
  int selectedTab = 1;

  ComponentUIDialog dialog;

  ComponentUIDialog initDialog(int nameMaxLength) {
    ComponentUIDialog dialog = new ComponentUIDialog(this);
    dialog.pNameLabel.show();
    dialog.pNameLabel.setText(GoalLocale.getLocalizationByKey(GoalLocale.Keys.goalName));

    dialog.pNameField.setText("");
    dialog.pNameField.setMaxLength(nameMaxLength);
    dialog.pNameField.show();
    return dialog;
  }

  void initNewItemButton(ComponentUIDialog itemDialog){
    ComponentUIFloatingButton floatingButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.GOAL_ADD_NEW_ITEM));
    floatingButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.add));
    floatingButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
    floatingButton.controls.setOnClick(new SetBtnLocation() {
      @Override
      public void onClick(int btnId, String tag) {
        itemDialog.setTitle(GoalLocale.getLocalizationByKey(GoalLocale.Keys.createGoal));
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

  void initList(GoalModel[] items) {
    list = ComponentUIList.cast(mn.findFragmentById(R.id.GOAL_LIST));
    ArrayList<CommonItem.Flags> flags = new ArrayList<>();
    flags.add(CommonItem.Flags.showDelete);
    flags.add(CommonItem.Flags.showEdit);
    list.controls.setAdapter(convertFromModels(items), this, flags);
  }

  private void onCreateItem(String text) {
    GoalModel newGoal = repository.getDraftRecord();
    newGoal.name.set(text);
    newGoal.description.set("");
    newGoal.actNumber.set(selectedTab);
    newGoal.save();
    CommonHolderPayloadData newItem = new CommonHolderPayloadData(newGoal.id, text, "");
    list.controls.add(newItem, false);
  }

  void showHintByAct(int actNumber){
    String actHint;
    switch (actNumber){
      case 1: actHint = getLocalizationByKey(GoalLocale.Keys.actIHint);  break;
      case 2: actHint = getLocalizationByKey(GoalLocale.Keys.actIIHint);  break;
      case 3: actHint = getLocalizationByKey(GoalLocale.Keys.actIIIHint);  break;
      case 4: actHint = getLocalizationByKey(GoalLocale.Keys.actIVHint);  break;
      case 5: actHint = getLocalizationByKey(GoalLocale.Keys.actVHint);  break;
      default: actHint = null;
    }
    TextView hint = findViewById(R.id.GOAL_ACT_HINT_ID);
    hint.setText(actHint);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_goals_list);
    UIToolbar.setTitle(this, getLocalizationByKey(GoalLocale.Keys.listCaption), "");
    String journeyId = getIntent().getStringExtra(INTENT_JOURNEY_ID);
    mn = getSupportFragmentManager();
    repository = GlobalApplication.getAppDB().goalRepository;
    repository.setJourneyId(journeyId);
    dialog = initDialog(
      repository.getNameLength()
    );
    initNewItemButton(dialog);
    initList(repository.listByAct(selectedTab));
    showHintByAct(selectedTab);
  }

  @Override
  public void onUpdate(int listItemId) {
    dialog.setTitle(JourneyLocale.getLocalizationByKey(JourneyLocale.Keys.updateJourney));
    CommonHolderPayloadData listItem = list.controls.getItemByListId(listItemId);
    GoalModel item = repository.getRecord(listItem.getId());
    dialog.pNameField.setText(item.name.get());
    dialog.setListener(new ComponentUIDialog.DialogClickListener() {
      @Override
      public void onResolve() {
        item.name.set(dialog.pNameField.getText());
        item.save();
        listItem.setTitle(dialog.pNameField.getText());
        list.controls.update(listItem, listItemId);
      }
      @Override
      public void onReject() {

      }
    });
    dialog.show();
  }

  @Override
  public void onDelete(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    list.controls.delete(listItemId);
    repository.removeRecord(item.getId());
  }

  @Override
  public void onSelect(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    Intent eventIntent = new Intent(this, PageGoal.class);
    eventIntent.putExtra(INTENT_GOAL_ID, item.getId().toString());
    startActivity(eventIntent);
  }

  @Override
  public void updateSelectedTab(int newCurrentTab) {
    selectedTab = newCurrentTab;
    initList(repository.listByAct(newCurrentTab));
    showHintByAct(newCurrentTab);
  }
}
