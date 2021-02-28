package com.masterhelper.plotLine;

import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.goals.GoalLocale;
import com.masterhelper.goals.repository.GoalRepository;
import com.masterhelper.plotLine.repository.PlotLineModel;
import com.masterhelper.plotLine.repository.PlotLineRepository;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.list.CommonHolderPayloadData;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.list.ComponentUIList;
import com.masterhelper.ux.components.library.list.ListItemControlsListener;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import java.util.ArrayList;

import static com.masterhelper.journeys.PageJourneyList.INTENT_JOURNEY_ID;
import static com.masterhelper.plotLine.PlotLinePage.INTENT_PLOT_ID;

public class PagePlotsList extends AppCompatActivity implements ListItemControlsListener {

  FragmentManager mn;
  GoalRepository goalRepository;
  PlotLineRepository plotLineRepository;
  ComponentUIList list;

  ComponentUIDialog dialog;

  ComponentUIDialog initDialog(int nameMaxLength) {
    ComponentUIDialog dialog = new ComponentUIDialog(this);
    dialog.pNameLabel.show();
    dialog.pNameLabel.setText(PlotLocale.getLocalizationByKey(PlotLocale.Keys.name));

    dialog.pNameField.setText("");
    dialog.pNameField.setMaxLength(nameMaxLength);
    dialog.pNameField.show();
    return dialog;
  }


  void initNewItemButton(ComponentUIDialog itemDialog) {
    ComponentUIFloatingButton floatingButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.PLOT_CREATE_BTN_ID));
    floatingButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.add));
    floatingButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
    floatingButton.controls.setOnClick(new SetBtnLocation() {
      @Override
      public void onClick(int btnId, String tag) {
        itemDialog.setTitle(PlotLocale.getLocalizationByKey(PlotLocale.Keys.create));
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

  void initList(PlotLineModel[] items) {
    list = ComponentUIList.cast(mn.findFragmentById(R.id.PLOT_LIST_ID));
    ArrayList<CommonItem.Flags> flags = new ArrayList<>();
    flags.add(CommonItem.Flags.showDelete);
    flags.add(CommonItem.Flags.showEdit);
    flags.add(CommonItem.Flags.showDescription);
    ArrayList<CommonHolderPayloadData> listItems = new ArrayList<>();
    for (PlotLineModel model : items) {
      CommonHolderPayloadData listItem = new CommonHolderPayloadData(model.id, model.name.get(), "");
      listItem.setDescription(GoalLocale.getLocalizationByKey(GoalLocale.Keys.goalName) + ": " + model.getCurrentPlotPointName());
      listItems.add(listItem);
    }
    list.controls.setAdapter(listItems, this, flags);
  }

  private void onCreateItem(String text) {
    PlotLineModel newPlot = plotLineRepository.getDraftRecord();
    newPlot.name.set(text);
    newPlot.save();
    CommonHolderPayloadData newItem = new CommonHolderPayloadData(newPlot.id, text, "");
    list.controls.add(newItem, false);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_plots_list);
    UIToolbar.setTitle(this, PlotLocale.getLocalizationByKey(PlotLocale.Keys.caption), "");
    String journeyId = getIntent().getStringExtra(INTENT_JOURNEY_ID);
    mn = getSupportFragmentManager();
    goalRepository = GlobalApplication.getAppDB().goalRepository;
    plotLineRepository = GlobalApplication.getAppDB().plotLineRepository;

    goalRepository.setPlotId(journeyId);
    plotLineRepository.setJourneyId(journeyId);

    dialog = initDialog(
      goalRepository.getNameLength()
    );
    initNewItemButton(dialog);
  }

  @Override
  protected void onStart() {
    super.onStart();
    initList(plotLineRepository.list());
  }

  @Override
  public void onUpdate(int listItemId) {
    dialog.setTitle("");
    CommonHolderPayloadData listItem = list.controls.getItemByListId(listItemId);
    PlotLineModel item = plotLineRepository.getRecord(listItem.getId());
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
    plotLineRepository.removeRecord(item.getId());
  }

  @Override
  public void onSelect(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    Intent eventIntent = new Intent(this, PlotLinePage.class);
    eventIntent.putExtra(INTENT_PLOT_ID, item.getId().toString());
    startActivity(eventIntent);
  }
}