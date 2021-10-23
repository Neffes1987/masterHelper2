package com.masterhelper.screens.journey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.screens.CommonScreen;
import com.masterhelper.screens.DetailsScreen;
import com.masterhelper.screens.plotTwist.PlotTwistsList;
import com.masterhelper.screens.plotTwist.PlotsListAdapter;
import com.masterhelper.screens.plotTwist.point.PointsListScreen;
import com.masterhelper.ux.ActsFragment;
import com.masterhelper.ux.BottomNavigationBlock;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.UIAddButtonFragment;
import com.masterhelper.ux.list.ListFragment;
import org.jetbrains.annotations.NotNull;

public class CurrentScreen extends CommonScreen implements ActsFragment.ActControls {
  public static final String INTENT_CURRENT_JOURNEY_ID = "CURRENT_JOURNEY_ID";
  public static final String INTENT_CURRENT_JOURNEY_ACT_NUMBER = "CURRENT_JOURNEY_ACT_NUMBER";
  int EDIT_JOURNEY_REQUEST_CODE = 100;
  private PlotsListAdapter plotsListAdapter;

  JourneyRepository journeyRepository;
  JourneyModel currentJourney;

  BottomNavigationBlock bottomNavigationBlock;
  ActsFragment actsFragment;
  UIAddButtonFragment addPlotLineButtonFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_current_journey_screen);

    String currentJourneyId = getIntent().getStringExtra(INTENT_CURRENT_JOURNEY_ID);

    journeyRepository = GlobalApplication.getAppDB().journeyRepository;
    currentJourney = journeyRepository.get(currentJourneyId);

    if (currentJourney == null) {
      cleanSetting(Setting.JourneyId);
      startActivity(JourneyList.getScreenIntent(this));

      return;
    }

    saveSetting(Setting.JourneyId, currentJourney.getId());

    addContextMenuItems(new Integer[]{
      R.string.journey_list,
      R.string.details
    });

    setActionBarTitle(currentJourney.getTitle());
    setActionBarSubtitle(getResources().getString(R.string.journey_current));

    ContextPopupMenuBuilder contextPopupMenuBuilder = new ContextPopupMenuBuilder(new int[]{
      R.string.open,
      R.string.detach
    });

    contextPopupMenuBuilder.setPopupMenuClickHandler((plotId, itemIndex) -> {
      if (itemIndex == 0) {
        startActivity(PointsListScreen.getIntent(this, plotId));
      }

      if (itemIndex == 1) {
        JourneyProgressRepository journeyProgressRepository = GlobalApplication.getAppDB().journeyProgressRepository;
        journeyProgressRepository.detachPlot(plotId);
        plotsListAdapter.deleteItem(plotId);
      }

      return true;
    });

    plotsListAdapter = new PlotsListAdapter(contextPopupMenuBuilder);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
    if (item.getTitle() == getResources().getString(R.string.journey_list)) {
      startActivity(JourneyList.getScreenIntent(this));
    }

    if (item.getTitle() == getResources().getString(R.string.details)) {
      Intent intent = JourneyDetailsScreen.getScreenIntent(this);
      intent.putExtra(DetailsScreen.INTENT_EDIT_SCREEN_ID, currentJourney.getId());

      startActivityForResult(intent, EDIT_JOURNEY_REQUEST_CODE);
    }

    return true;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == EDIT_JOURNEY_REQUEST_CODE && resultCode == RESULT_OK) {
      currentJourney = journeyRepository.get(currentJourney.getId());
      setActionBarTitle(currentJourney.getTitle());
    }
  }

  @Override
  protected void onStart() {
    super.onStart();

    if (currentJourney != null) {
      currentJourney = journeyRepository.get(currentJourney.getId());
      setActionBarTitle(currentJourney.getTitle());

      JourneyProgressRepository progressRepository = GlobalApplication.getAppDB().journeyProgressRepository;
      plotsListAdapter.addPlots(progressRepository.getJourneyPlots(currentJourney));
    }
  }

  @Override
  protected void onInitScreen() {
    if (currentJourney == null) {
      return;
    }
    actsFragment = (ActsFragment) getSupportFragmentManager().findFragmentById(R.id.JOURNEY_ACTS_FRAGMENT_ID);
    assert actsFragment != null;
    actsFragment.setCurrentActIndex(currentJourney.getSessionAct());

    addPlotLineButtonFragment = (UIAddButtonFragment) getSupportFragmentManager().findFragmentById(R.id.JOURNEY_ADD_PLOT_FRAGMENT_ID);
    assert addPlotLineButtonFragment != null;
    addPlotLineButtonFragment.setListener(v -> {
      Intent addPlotTwistIntent = PlotTwistsList.getScreenIntent(this);
      addPlotTwistIntent.putExtra(INTENT_CURRENT_JOURNEY_ID, currentJourney.getId());
      addPlotTwistIntent.putExtra(INTENT_CURRENT_JOURNEY_ACT_NUMBER, currentJourney.getSessionAct());
      startActivity(addPlotTwistIntent);
    });

    ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.JOURNEY_PLOTS_LIST_FRAGMENT_ID);
    assert listFragment != null;
    listFragment.setAdapter(plotsListAdapter);
  }

  public static Intent getScreenIntent(Context context, String id) {
    Intent intent = new Intent(context, CurrentScreen.class);
    intent.putExtra(INTENT_CURRENT_JOURNEY_ID, id);

    return intent;
  }

  @Override
  public void onActChanged(int newActNumber) {
    currentJourney.setSessionAct(newActNumber);
    journeyRepository.update(currentJourney);

    JourneyProgressRepository progressRepository = GlobalApplication.getAppDB().journeyProgressRepository;
    plotsListAdapter.addPlots(progressRepository.getJourneyPlots(currentJourney));
  }
}