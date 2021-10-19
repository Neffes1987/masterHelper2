package com.masterhelper.screens.plotTwist;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.screens.ListScreen;
import com.masterhelper.screens.journey.JourneyProgressRepository;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.propertyBar.PropertyBarContentModel;

import java.util.ArrayList;

import static com.masterhelper.screens.EditScreen.INTENT_EDIT_SCREEN_ID;
import static com.masterhelper.screens.journey.CurrentScreen.INTENT_CURRENT_JOURNEY_ACT_NUMBER;
import static com.masterhelper.screens.journey.CurrentScreen.INTENT_CURRENT_JOURNEY_ID;

public class PlotTwistsList extends ListScreen<PlotTwistModel> {
  int EDIT_PLOT_LINE_REQUEST_CODE = 200;

  @Override
  public String getListTitle() {
    return getResources().getString(R.string.plot_twist_list);
  }

  @Override
  public Boolean getBackButtonVisible() {
    return true;
  }

  @Override
  public Intent getCreateItemIntent() {

    return PlotTwistEditScreen.getScreenIntent(this);
  }

  @Override
  public ContextPopupMenuBuilder getHeaderPopup() {
    int[] options;
    if (getIntent().getStringExtra(INTENT_CURRENT_JOURNEY_ID) == null) {
      options = new int[]{R.string.details, R.string.delete};
    } else {
      options = new int[]{R.string.details, R.string.delete, R.string.attach};
    }
    ContextPopupMenuBuilder contextPopupMenuBuilder = new ContextPopupMenuBuilder(options);

    JourneyProgressRepository journeyProgressRepository = GlobalApplication.getAppDB().journeyProgressRepository;
    PlotTwistRepository plotTwistRepository = GlobalApplication.getAppDB().plotRepository;


    contextPopupMenuBuilder.setPopupMenuClickHandler((plotId, itemIndex) -> {
      if (options[itemIndex] == R.string.attach) {
        Intent journeyIntent = getIntent();

        journeyProgressRepository.setPlotToJourney(
          journeyIntent.getStringExtra(INTENT_CURRENT_JOURNEY_ID),
          journeyIntent.getIntExtra(INTENT_CURRENT_JOURNEY_ACT_NUMBER, 0),
          plotId
        );

        deleteItem(plotId);
      } else if (options[itemIndex] == R.string.details) {
        Intent editPlotIntent = PlotTwistEditScreen.getScreenIntent(this);
        editPlotIntent.putExtra(INTENT_EDIT_SCREEN_ID, plotId);

        startActivityForResult(editPlotIntent, EDIT_PLOT_LINE_REQUEST_CODE);
      } else if (options[itemIndex] == R.string.delete) {
        journeyProgressRepository.detachPlot(plotId);
        plotTwistRepository.delete(plotId);
        deleteItem(plotId);
      }

      return true;
    });

    return contextPopupMenuBuilder;
  }

  @Override
  public ArrayList<PropertyBarContentModel> getListItems() {
    ArrayList<PropertyBarContentModel> listItems = new ArrayList<>();

    String journeyId = getIntent().getStringExtra(INTENT_CURRENT_JOURNEY_ID);

    ArrayList<PlotTwistModel> bdData;
    if (journeyId != null) {
      JourneyProgressRepository journeyProgressRepository = GlobalApplication.getAppDB().journeyProgressRepository;
      bdData = journeyProgressRepository.getDetachedPlots(journeyId);
    } else {
      PlotTwistRepository plotTwistRepository = GlobalApplication.getAppDB().plotRepository;
      bdData = plotTwistRepository.list(0, 0, null, null);
    }

    for (PlotTwistModel model : bdData) {
      listItems.add(convertToItem(model));
    }

    return listItems;
  }

  @Override
  public PropertyBarContentModel convertToItem(PlotTwistModel model) {
    return new PropertyBarContentModel(
      model.getId(),
      model.getTitle(),
      model.getDescription()
    );
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (data == null) {
      return;
    }

    String newPlotId = data.getStringExtra(INTENT_EDIT_SCREEN_ID);
    PlotTwistModel newModel = GlobalApplication.getAppDB().plotRepository.get(newPlotId);

    if (requestCode == CREATE_POINT_REQUEST_CODE && resultCode == RESULT_OK) {
      addToList(newModel, true);
    }

    if (requestCode == EDIT_PLOT_LINE_REQUEST_CODE && resultCode == RESULT_OK) {
      updateItem(newModel);
    }
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, PlotTwistsList.class);
  }
}