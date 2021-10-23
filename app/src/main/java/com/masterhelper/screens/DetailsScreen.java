package com.masterhelper.screens;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import com.masterhelper.R;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.propertyBar.PropertyBar;

public abstract class DetailsScreen extends CommonScreen {
  public static String INTENT_EDIT_SCREEN_ID = "INTENT_EDIT_SCREEN_ID";
  ContextPopupMenuBuilder popupBuilder;
  PropertyBar editorFragment;
  AbstractModel model;

  protected abstract int[] getOptions();

  protected abstract AbstractModel getCurrentModel(String id);

  protected abstract boolean onPopupMenuItemClick(String tag, int menuItemIndex);

  protected abstract String getTitleField();

  protected abstract String getSubtitleField();

  protected abstract String getDescriptionField();

  protected abstract String getLabelField();

  protected PropertyBar.CardStatus getStatusField() {
    return PropertyBar.CardStatus.Active;
  }

  protected void updateView() {
    editorFragment.setLabel(getLabelField(), getStatusField());
    editorFragment.setTitle(getTitleField());

    setActionBarTitle(model.getTitle());
    setActionBarSubtitle(getSubtitleField());

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      editorFragment.setDescription(Html.fromHtml(getDescriptionField(), Html.FROM_HTML_MODE_COMPACT));
    } else {
      editorFragment.setDescription(Html.fromHtml(getDescriptionField()));
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_property_view_screen);

    String entityId = getIntent().getStringExtra(INTENT_EDIT_SCREEN_ID);

    model = getCurrentModel(entityId);

    setActionBarTitle(model.getTitle());

    showBackButton(true);

    popupBuilder = new ContextPopupMenuBuilder(getOptions());
    popupBuilder.setPopupMenuClickHandler(this::onPopupMenuItemClick);
  }

  @Override
  protected void onInitScreen() {
    editorFragment = new PropertyBar(getSupportFragmentManager().findFragmentById(R.id.EDIT_FRAGMENT_ID));
    editorFragment.setCardContextMenu(popupBuilder);
    editorFragment.useFullHeight();
    updateView();
  }
}