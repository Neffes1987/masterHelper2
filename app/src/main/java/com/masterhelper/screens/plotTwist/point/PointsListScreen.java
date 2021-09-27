package com.masterhelper.screens.plotTwist.point;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.screens.CommonScreen;
import com.masterhelper.screens.plotTwist.PlotTwistModel;
import com.masterhelper.ux.UIAddButtonFragment;

import static com.masterhelper.screens.plotTwist.PlotTwistEditScreen.INTENT_PLOT_TWIST_ID;
import static com.masterhelper.screens.plotTwist.point.EditPointScreen.INTENT_POINT_ID;

public class PointsListScreen extends CommonScreen {
  public static int CREATE_POINT_REQUEST_CODE = 103;
  PlotTwistModel currentTwistModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_plot_twist_points);
    String plotTwistId = getIntent().getStringExtra(INTENT_PLOT_TWIST_ID);
    if (plotTwistId == null) {
      onBackPressed();
    }

    currentTwistModel = GlobalApplication.getAppDB().plotRepository.get(plotTwistId);

    setActionBarTitle(currentTwistModel.getTitle());
    showBackButton(true);
  }

  @Override
  protected void onInitScreen() {
    UIAddButtonFragment addPlotLineButtonFragment = (UIAddButtonFragment) getSupportFragmentManager().findFragmentById(R.id.ADD_POINT_FRAGMENT_ID);
    assert addPlotLineButtonFragment != null;
    addPlotLineButtonFragment.setListener(v -> {
      String plotTwistId = getIntent().getStringExtra(INTENT_PLOT_TWIST_ID);
      Intent createPlotIntent = EditPointScreen.getIntent(this, plotTwistId, null);
      startActivityForResult(createPlotIntent, CREATE_POINT_REQUEST_CODE);
    });
  }

  public static Intent getIntent(Context context, String pointId) {
    Intent intent = new Intent(context, PointsListScreen.class);
    intent.putExtra(INTENT_PLOT_TWIST_ID, pointId);

    return intent;
  }
}