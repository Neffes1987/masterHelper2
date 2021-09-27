package com.masterhelper.screens.plotTwist.point;

import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.ListAdapter;
import com.masterhelper.ux.list.propertyBar.PropertyBarContentModel;

import java.util.List;

public class PlotAdapter extends ListAdapter {
  public PlotAdapter(List<PropertyBarContentModel> items) {
    super(items);
  }

  public PlotAdapter(ContextPopupMenuBuilder builder) {
    super(builder);
  }
}
