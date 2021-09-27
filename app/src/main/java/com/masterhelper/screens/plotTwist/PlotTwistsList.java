package com.masterhelper.screens.plotTwist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.screens.CommonScreen;
import com.masterhelper.screens.journey.JourneyProgressRepository;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.UIAddButtonFragment;
import com.masterhelper.ux.list.ListAdapter;
import com.masterhelper.ux.list.ListFragment;

import java.util.ArrayList;

import static com.masterhelper.screens.journey.CurrentScreen.INTENT_CURRENT_JOURNEY_ACT_NUMBER;
import static com.masterhelper.screens.journey.CurrentScreen.INTENT_CURRENT_JOURNEY_ID;
import static com.masterhelper.screens.plotTwist.PlotTwistEditScreen.INTENT_PLOT_TWIST_ID;

public class PlotTwistsList extends CommonScreen {
  int EDIT_PLOT_LINE_REQUEST_CODE = 200;
  int CREATE_PLOT_LINE_REQUEST_CODE = 300;
  private PlotsListAdapter adapter;

  private ArrayList<PlotTwistModel> getList() {
    String journeyId = getIntent().getStringExtra(INTENT_CURRENT_JOURNEY_ID);

    JourneyProgressRepository journeyProgressRepository = GlobalApplication.getAppDB().journeyProgressRepository;

    if (journeyId != null) {
      return journeyProgressRepository.getDetachedPlots(journeyId);
    }

    PlotTwistRepository plotTwistRepository = GlobalApplication.getAppDB().plotRepository;
    return plotTwistRepository.list(0, 0, null, null);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_plots_list);

    int[] options;
    if (getIntent().getStringExtra(INTENT_CURRENT_JOURNEY_ID) == null) {
      options = new int[]{R.string.edit, R.string.delete};
    } else {
      options = new int[]{R.string.edit, R.string.delete, R.string.attach};
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

        adapter.deleteItem(plotId);
      } else if (options[itemIndex] == R.string.edit) {
        Intent editPlotIntent = PlotTwistEditScreen.getScreenIntent(this);
        editPlotIntent.putExtra(INTENT_PLOT_TWIST_ID, plotId);

        startActivityForResult(editPlotIntent, EDIT_PLOT_LINE_REQUEST_CODE);
      } else if (options[itemIndex] == R.string.delete) {
        journeyProgressRepository.detachPlot(plotId);
        plotTwistRepository.delete(plotId);
        adapter.deleteItem(plotId);
      }

      return true;
    });

    setActionBarTitle(R.string.plot_twist_list);
    showBackButton(true);

    adapter = new PlotsListAdapter(contextPopupMenuBuilder);
    adapter.addPlots(getList());
  }

  @Override
  protected void onInitScreen() {
    UIAddButtonFragment addPlotLineButtonFragment = (UIAddButtonFragment) getSupportFragmentManager().findFragmentById(R.id.ADD_JOURNEY_FRAGMENT_ID);
    assert addPlotLineButtonFragment != null;
    addPlotLineButtonFragment.setListener(v -> {
      Intent createPlotIntent = PlotTwistEditScreen.getScreenIntent(this);
      startActivityForResult(createPlotIntent, CREATE_PLOT_LINE_REQUEST_CODE);
    });

    ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.PLOTS_LIST_FRAGMENT_ID);
    assert listFragment != null;
    listFragment.setAdapter(adapter);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (data == null) {
      return;
    }

    String newPlotId = data.getStringExtra(INTENT_PLOT_TWIST_ID);
    PlotTwistModel newModel = GlobalApplication.getAppDB().plotRepository.get(newPlotId);

    if (requestCode == CREATE_PLOT_LINE_REQUEST_CODE && resultCode == RESULT_OK) {
      adapter.addPlot(newModel, true);
    }

    if (requestCode == EDIT_PLOT_LINE_REQUEST_CODE && resultCode == RESULT_OK) {
      adapter.updatePlot(newModel);
    }
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, PlotTwistsList.class);
  }
}