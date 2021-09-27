package com.masterhelper.screens.journey;

import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.ListAdapter;
import com.masterhelper.ux.list.propertyBar.PropertyBarContentModel;

import java.util.ArrayList;
import java.util.List;

public class JourneyListAdapter extends ListAdapter {
  public JourneyListAdapter(List<PropertyBarContentModel> items) {
    super(items);
  }

  public JourneyListAdapter(ContextPopupMenuBuilder builder) {
    super(builder);
  }

  public void addJourneys(ArrayList<JourneyModel> models) {
    mValues = new ArrayList<>();

    for (JourneyModel model : models) {
      PropertyBarContentModel UIItem = new PropertyBarContentModel(
        model.getId(),
        model.getTitle(),
        model.getDescription(),
        builder.cloneBuilder(model.getId())
      );

      mValues.add(UIItem);
    }

    notifyDataSetChanged();
  }
}