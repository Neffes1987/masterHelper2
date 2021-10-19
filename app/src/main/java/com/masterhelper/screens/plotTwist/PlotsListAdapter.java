package com.masterhelper.screens.plotTwist;

import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.ListAdapter;
import com.masterhelper.ux.list.propertyBar.PropertyBarContentModel;

import java.util.ArrayList;
import java.util.List;

public class PlotsListAdapter extends ListAdapter {

  public PlotsListAdapter(List<PropertyBarContentModel> items) {
    super(items);
  }

  public PlotsListAdapter(ContextPopupMenuBuilder builder) {
    super(builder);
  }


  public void addPlots(ArrayList<PlotTwistModel> models) {
    mValues = new ArrayList<>();

    for (PlotTwistModel model : models) {
      addPlot(model, false);
    }

    notifyDataSetChanged();
  }

  public void addPlot(PlotTwistModel model, Boolean shouldNotify) {
    mValues.add(mapPlotToProperty(model));

    if (shouldNotify) {
      notifyItemInserted(mValues.size() - 1);
    }
  }

  public void updatePlot(PlotTwistModel model) {
    int position = getListPositionByListId(model.getId());

    mValues.set(position, mapPlotToProperty(model));

    notifyItemChanged(position);
  }

  private PropertyBarContentModel mapPlotToProperty(PlotTwistModel model) {
    return new PropertyBarContentModel(
      model.getId(),
      model.getTitle(),
      model.getDescription()
    );
  }
}