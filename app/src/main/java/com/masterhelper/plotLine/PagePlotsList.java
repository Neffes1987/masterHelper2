package com.masterhelper.plotLine;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.goals.GoalLocale;
import com.masterhelper.goals.repository.GoalRepository;
import com.masterhelper.plotLine.repository.PlotLineModel;
import com.masterhelper.plotLine.repository.PlotLineRepository;
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.dialog.TextDialog;
import com.masterhelper.ux.components.library.list.*;

import java.util.ArrayList;

import static com.masterhelper.journeys.PageJourneyList.INTENT_JOURNEY_ID;
import static com.masterhelper.plotLine.PlotLinePage.INTENT_PLOT_ID;

public class PagePlotsList extends AppMenuActivity implements ListItemControlsListener {

  FragmentManager mn;
  GoalRepository goalRepository;
  PlotLineRepository plotLineRepository;
  ComponentUIList list;


  void initList(PlotLineModel[] items) {
    list = ComponentUIList.cast(mn.findFragmentById(R.id.ITEMS_LIST));
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_simple_list);
    UIToolbar.setTitle(this, PlotLocale.getLocalizationByKey(PlotLocale.Keys.caption), "");
    setItemControlTitle(PlotLocale.getLocalizationByKey(PlotLocale.Keys.create));
    String journeyId = getIntent().getStringExtra(INTENT_JOURNEY_ID);
    mn = getSupportFragmentManager();
    goalRepository = GlobalApplication.getAppDB().goalRepository;
    plotLineRepository = GlobalApplication.getAppDB().plotLineRepository;

    goalRepository.setPlotId(journeyId);
    plotLineRepository.setJourneyId(journeyId);
  }

  @Override
  protected void onStart() {
    super.onStart();
    initList(plotLineRepository.list());
  }

  public void onUpdate(int listItemId) {
    CommonHolderPayloadData listItem = list.controls.getItemByListId(listItemId);
    PlotLineModel item = plotLineRepository.getRecord(listItem.getId());

    String updateTitle = PlotLocale.getLocalizationByKey(PlotLocale.Keys.create);

    TextDialog dialog = new TextDialog(this, updateTitle, 0, item.name.get(), (result) -> {
      item.name.set(result);
      item.save();
      listItem.setTitle(result);
      list.controls.update(listItem, listItemId);
    });
    dialog.show();
  }

  public void onDelete(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    list.controls.delete(listItemId);
    plotLineRepository.removeRecord(item.getId());
  }

  public void onSelect(int listItemId) {
    CommonHolderPayloadData item = list.controls.getItemByListId(listItemId);
    Intent eventIntent = new Intent(this, PlotLinePage.class);
    eventIntent.putExtra(INTENT_PLOT_ID, item.getId().toString());
    startActivity(eventIntent);
  }

  @Override
  protected void onAppBarMenuItemControl() {
    String createTitle = PlotLocale.getLocalizationByKey(PlotLocale.Keys.create);

    TextDialog dialog = new TextDialog(this, createTitle, 0, "", (result) -> {
      PlotLineModel newPlot = plotLineRepository.getDraftRecord();
      newPlot.name.set(result);
      newPlot.save();
      CommonHolderPayloadData newItem = new CommonHolderPayloadData(newPlot.id, result, "");
      list.controls.add(newItem, false);
    });

    dialog.show();
  }

  @Override
  public void listItemChanged(ListItemActionCodes code, int listItemId) {
    switch (code) {
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
}