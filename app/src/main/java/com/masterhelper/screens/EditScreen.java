package com.masterhelper.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.masterhelper.R;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.ux.ApplyButtonFragment;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.propertyBar.PropertyBar;

public abstract class EditScreen extends CommonScreen {
  public static String INTENT_EDIT_SCREEN_ID = "INTENT_EDIT_SCREEN_ID";
  ContextPopupMenuBuilder popupBuilder;
  PropertyBar editorFragment;

  protected abstract int[] getOptions();

  protected abstract AbstractModel getCurrentModel(String id);

  protected abstract boolean onPopupMenuItemClick(String tag, int menuItemIndex);

  protected abstract void onUserApplyChanges(View v);

  protected abstract String getTitleField();

  protected abstract String getDescriptionField();

  protected abstract int getLabelField();

  protected PropertyBar.CardStatus getStatusField() {
    return PropertyBar.CardStatus.Active;
  }

  protected void updateView() {
    editorFragment.setLabel(getLabelField(), getStatusField());
    editorFragment.setTitle(getTitleField());
    editorFragment.setDescription(getDescriptionField());
  }

  ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_property_edit_screen);

    String entityId = getIntent().getStringExtra(INTENT_EDIT_SCREEN_ID);

    AbstractModel model = getCurrentModel(entityId);

    if (model.getTitle() != null) {
      setActionBarTitle(model.getTitle());
    }

    showBackButton(true);

    popupBuilder = new ContextPopupMenuBuilder(getOptions());
    popupBuilder.setPopupMenuClickHandler(this::onPopupMenuItemClick);
  }

  @Override
  protected void onInitScreen() {
    editorFragment = new PropertyBar(getSupportFragmentManager().findFragmentById(R.id.EDIT_FRAGMENT_ID));
    editorFragment.setCardContextMenu(popupBuilder);
    updateView();


    ApplyButtonFragment applyButtonFragment = (ApplyButtonFragment) getSupportFragmentManager().findFragmentById(R.id.EDIT_FRAGMENT_APPLY_BTN_ID);
    if (applyButtonFragment != null) {
      applyButtonFragment.setListener(this::onUserApplyChanges);
    }
  }
}