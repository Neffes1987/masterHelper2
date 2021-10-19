package com.masterhelper.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.masterhelper.R;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.UIAddButtonFragment;
import com.masterhelper.ux.list.ListAdapter;
import com.masterhelper.ux.list.ListFragment;
import com.masterhelper.ux.list.propertyBar.PropertyBarContentModel;

import java.util.ArrayList;

public abstract class ListScreen<Model extends AbstractModel> extends CommonScreen {
  protected ContextPopupMenuBuilder popupMenuBuilder;
  protected ListAdapter adapter;
  public static int CREATE_POINT_REQUEST_CODE = 1;

  public abstract String getListTitle();

  public abstract Boolean getBackButtonVisible();

  public abstract Intent getCreateItemIntent();

  public abstract ContextPopupMenuBuilder getHeaderPopup();

  public abstract ArrayList<PropertyBarContentModel> getListItems();

  public abstract PropertyBarContentModel convertToItem(Model model);

  public void addToList(Model model, Boolean toFirst) {
    adapter.addItem(convertToItem(model), toFirst);
  }

  public void updateItem(Model model) {
    adapter.updateItem(convertToItem(model));
  }

  public void deleteItem(String id) {
    adapter.deleteItem(id);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    setActionBarTitle(getListTitle());
    showBackButton(getBackButtonVisible());

    popupMenuBuilder = getHeaderPopup();

    adapter = new ListAdapter(popupMenuBuilder);
    adapter.setValues(getListItems());
  }

  @Override
  protected void onStart() {
    super.onStart();

    adapter.setValues(getListItems());
    adapter.notifyDataSetChanged();
  }

  @Override
  protected void onInitScreen() {
    UIAddButtonFragment addPlotLineButtonFragment = (UIAddButtonFragment) getSupportFragmentManager().findFragmentById(R.id.ADD_NEW_ITEM_FRAGMENT_ID);
    assert addPlotLineButtonFragment != null;

    addPlotLineButtonFragment.setListener(v -> startActivityForResult(getCreateItemIntent(), CREATE_POINT_REQUEST_CODE));

    ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.LIST_FRAGMENT_ID);
    assert listFragment != null;
    listFragment.setAdapter(adapter);
  }
}