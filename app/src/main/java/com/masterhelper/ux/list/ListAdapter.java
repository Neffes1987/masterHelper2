package com.masterhelper.ux.list;

import android.text.Html;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.masterhelper.R;
import com.masterhelper.screens.journey.JourneyModel;
import com.masterhelper.screens.plotTwist.PlotTwistModel;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.propertyBar.PropertyBar;
import com.masterhelper.ux.list.propertyBar.PropertyBarContentModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
  protected ContextPopupMenuBuilder builder;

  protected List<PropertyBarContentModel> mValues;

  public void setValues(List<PropertyBarContentModel> mValues) {
    this.mValues = mValues;
  }

  public ListAdapter(List<PropertyBarContentModel> items) {
    mValues = items;
  }

  public ListAdapter(ContextPopupMenuBuilder builder) {
    mValues = new ArrayList<>();
    this.builder = builder;
  }

  @Override
  public @NotNull ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_property_bar, parent, false));
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    PropertyBarContentModel item = mValues.get(position);

    holder.propertyBar.setTitle(item.getTitle());
    holder.propertyBar.setDescription(Html.fromHtml(item.getDescription(), Html.FROM_HTML_MODE_COMPACT));

    holder.propertyBar.usePreviewHeight();

    if (builder != null) {
      holder.propertyBar.setCardContextMenu(builder.cloneBuilder(item.getId()));
    }
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public PropertyBar propertyBar;

    public ViewHolder(View binding) {
      super(binding);
      propertyBar = new PropertyBar(binding);
    }

    @Override
    public String toString() {
      return super.toString();
    }
  }

  public void deleteItem(String propertyId) {
    int position = getListPositionByListId(propertyId);
    mValues.remove(position);
    notifyItemRemoved(position);
  }

  public void updateItem(PropertyBarContentModel updatedData) {
    int position = getListPositionByListId(updatedData.getId());
    mValues.set(position, updatedData);
    notifyItemChanged(position, updatedData);
  }

  public void addItem(PropertyBarContentModel newData, boolean toFirst) {
    if (toFirst) {
      mValues.add(0, newData);
    } else {
      mValues.add(newData);
    }
    int position = getListPositionByListId(newData.getId());
    notifyItemInserted(position);
  }

  protected int getListPositionByListId(String listId) {
    int result = -1;
    for (int listIndexCounter = 0; listIndexCounter < mValues.size(); listIndexCounter += 1) {
      if (Objects.equals(mValues.get(listIndexCounter).getId(), listId)) {
        result = listIndexCounter;
        break;
      }
    }
    return result;
  }
}