package com.masterhelper.goals;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.goals.repository.GoalModel;
import com.masterhelper.goals.repository.GoalRepository;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.components.widgets.acts.IActsTabs;
import com.masterhelper.ux.pages.events.PageEventsList;
import com.masterhelper.journeys.JourneyLocale;
import com.masterhelper.ux.components.library.list.ListItemEvents;
import com.masterhelper.goals.list.ListItemGoal;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import static com.masterhelper.journeys.PageJourneyList.INTENT_JOURNEY_ID;
import static com.masterhelper.goals.GoalLocale.getLocalizationByKey;

public class PageGoalsList extends AppCompatActivity implements ListItemEvents, IActsTabs {
  public static final String INTENT_SCENE_ID = "goalId";
  FragmentManager mn;
  GoalRepository repository;
  ComponentUIList<GoalModel> list;
  int selectedTab = 1;

  ComponentUIDialog dialog;

  ComponentUIDialog initDialog(int nameMaxLength, int descriptionMaxLength){
    ComponentUIDialog dialog = new ComponentUIDialog(this);
    dialog.pNameLabel.show();
    dialog.pNameLabel.setText(GoalLocale.getLocalizationByKey(GoalLocale.Keys.sceneName));

    dialog.pNameField.setText("");
    dialog.pNameField.setMaxLength(nameMaxLength);
    dialog.pNameField.show();

    dialog.pDescriptionLabel.show();
    dialog.pDescriptionLabel.setText(GoalLocale.getLocalizationByKey(GoalLocale.Keys.shortDescription));

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

  void initList(GoalModel[] items){
    list = ComponentUIList.cast(mn.findFragmentById(R.id.SCENE_LIST));
    list.controls.setAdapter(items, new ListItemGoal(getSupportFragmentManager(), this));
  }

  private void onCreateItem(String text, String description) {
    GoalModel newGoal = repository.getDraftRecord();
    newGoal.name.set(text);
    newGoal.description.set(description);
    newGoal.actNumber.set(selectedTab);
    newGoal.save();
    list.controls.add(newGoal, false);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_goals_list);
    UIToolbar.setTitle(this, getLocalizationByKey(GoalLocale.Keys.listCaption), null);
    String journeyId = getIntent().getStringExtra(INTENT_JOURNEY_ID);
    mn = getSupportFragmentManager();
    repository = GlobalApplication.getAppDB().goalRepository;
    repository.setJourneyId(journeyId);
    dialog = initDialog(
      repository.getNameLength(),
      repository.getDescriptionLength()
    );
    initNewItemButton(dialog);
    initList(repository.listByAct(selectedTab));
  }

  @Override
  public void onUpdate(int listItemId) {
    dialog.setTitle(JourneyLocale.getLocalizationByKey(JourneyLocale.Keys.updateJourney));
    GoalModel item = list.controls.getItemByListId(listItemId);
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
    GoalModel item = list.controls.getItemByListId(listItemId);
    list.controls.delete(listItemId);
    repository.removeRecord(item.id);
  }

  @Override
  public void onSelect(int listItemId) {
    GoalModel item = list.controls.getItemByListId(listItemId);
    Intent eventIntent = new Intent(this, PageEventsList.class);
    eventIntent.putExtra(INTENT_SCENE_ID, item.id.get().toString());
    startActivity(eventIntent);
  }

  @Override
  public void updateSelectedTab(int newCurrentTab) {
    selectedTab = newCurrentTab;
    initList(repository.listByAct(newCurrentTab));
  }
}
