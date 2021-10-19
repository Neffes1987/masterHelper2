package com.masterhelper.screens.plotTwist.point;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.screens.EditScreen;
import com.masterhelper.screens.ListScreen;
import com.masterhelper.screens.plotTwist.PlotTwistModel;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.propertyBar.PropertyBarContentModel;

import java.util.ArrayList;

import static com.masterhelper.screens.EditScreen.INTENT_EDIT_SCREEN_ID;

public class PointsListScreen extends ListScreen<PointModel> {
  PlotTwistModel currentTwistModel;
  int[] options = new int[]{R.string.details, R.string.delete};

  @Override
  public String getListTitle() {
    String plotTwistId = getIntent().getStringExtra(EditScreen.INTENT_EDIT_SCREEN_ID);

    currentTwistModel = GlobalApplication.getAppDB().plotRepository.get(plotTwistId);
    return currentTwistModel.getTitle();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setActionBarSubtitle(getResources().getString(R.string.point_list));
  }

  @Override
  public Boolean getBackButtonVisible() {
    return true;
  }

  @Override
  public Intent getCreateItemIntent() {
    String plotTwistId = getIntent().getStringExtra(EditScreen.INTENT_EDIT_SCREEN_ID);
    return EditPointScreen.getIntent(this, plotTwistId, null);
  }

  @Override
  public ContextPopupMenuBuilder getHeaderPopup() {
    String plotTwistId = getIntent().getStringExtra(EditScreen.INTENT_EDIT_SCREEN_ID);
    ContextPopupMenuBuilder contextPopupMenuBuilder = new ContextPopupMenuBuilder(options);

    contextPopupMenuBuilder.setPopupMenuClickHandler((plotId, itemIndex) -> {
      if (options[itemIndex] == R.string.details) {
        Intent editPlotIntent = EditPointScreen.getIntent(this, plotTwistId, currentTwistModel.getId());
        editPlotIntent.putExtra(INTENT_EDIT_SCREEN_ID, plotId);

        startActivity(editPlotIntent);
      } else if (options[itemIndex] == R.string.delete) {
        GlobalApplication.getAppDB().pointRepository.delete(plotId);
        deleteItem(plotId);
      }

      return true;
    });

    return contextPopupMenuBuilder;
  }

  @Override
  public ArrayList<PropertyBarContentModel> getListItems() {
    ArrayList<PropertyBarContentModel> items = new ArrayList<>();
    PointRepository repository = GlobalApplication.getAppDB().pointRepository;
    String plotTwistId = getIntent().getStringExtra(EditScreen.INTENT_EDIT_SCREEN_ID);

    for (PointModel model : repository.list(PointRepository.plotTwistId.getColumnTitle() + "='" + plotTwistId + "'")
    ) {
      items.add(convertToItem(model));
    }
    return items;
  }

  @Override
  public PropertyBarContentModel convertToItem(PointModel model) {
    return new PropertyBarContentModel(
      model.getId(),
      model.getTitle(),
      model.getDescription()
    );
  }

  public static Intent getIntent(Context context, String pointId) {
    Intent intent = new Intent(context, PointsListScreen.class);
    intent.putExtra(EditScreen.INTENT_EDIT_SCREEN_ID, pointId);

    return intent;
  }
}