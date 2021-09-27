package com.masterhelper.screens.journey;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.screens.EditScreen;
import com.masterhelper.ux.TextDialogBuilder;

public class EditorScreen extends EditScreen {
  int[] options = new int[]{
    R.string.journey_popup_edit_title,
    R.string.journey_popup_edit_description_title,
    R.string.journey_popup_edit_restrictions_title
  };

  JourneyRepository repository;
  JourneyModel journeyModel;

  @Override
  protected int[] getOptions() {
    return options;
  }

  @Override
  protected AbstractModel getCurrentModel(String id) {
    repository = GlobalApplication.getAppDB().journeyRepository;
    journeyModel = id != null ? repository.get(id) : repository.draft();
    return journeyModel;
  }

  protected boolean onPopupMenuItemClick(String tag, int menuItemIndex) {
    TextDialogBuilder textDialogBuilder = new TextDialogBuilder(this);
    String value;

    if (options[menuItemIndex] == R.string.journey_popup_edit_title) {
      value = journeyModel.getTitle();
      textDialogBuilder.setMaxLength(JourneyModel.TITLE_MAX_LENGTH);

      textDialogBuilder
        .setValue(value)
        .setControlsButton((v) -> {
          String newValue = textDialogBuilder.getValue();
          journeyModel.setTitle(newValue);
          updateView();
        }, null);


    } else if (options[menuItemIndex] == R.string.journey_popup_edit_description_title) {
      value = journeyModel.getDescription();
      textDialogBuilder.setMaxLength(JourneyModel.DESCRIPTION_MAX_LENGTH);

      textDialogBuilder
        .setValue(value)
        .setControlsButton((v) -> {
          String newValue = textDialogBuilder.getValue();
          journeyModel.setDescription(newValue);
          updateView();
        }, null);
    } else {
      value = journeyModel.getRestrictions();
      textDialogBuilder.setMaxLength(JourneyModel.RESTRICTION_MAX_LENGTH);

      textDialogBuilder
        .setValue(value)
        .setControlsButton((v) -> {
          String newValue = textDialogBuilder.getValue();
          journeyModel.setRestrictions(newValue);
          updateView();
        }, null);
    }


    textDialogBuilder.setTitle(R.string.edit).create().show();
    return true;
  }

  @Override
  protected void onUserApplyChanges(View v) {
    if (journeyModel.getTitle().length() == 0) {
      Toast.makeText(this, R.string.journey_edit_title_error, Toast.LENGTH_LONG).show();
      return;
    }

    String journeyId = getIntent().getStringExtra(EditScreen.INTENT_EDIT_SCREEN_ID);

    if (journeyId != null && journeyId.length() > 0) {
      repository.update(journeyModel);
      setResult(RESULT_OK);
      finish();
    } else {
      repository.create(journeyModel);
      startActivity(CurrentScreen.getScreenIntent(this, journeyModel.getId()));
    }
  }

  @Override
  protected String getTitleField() {
    return journeyModel.getTitle();
  }

  @Override
  protected String getDescriptionField() {
    return journeyModel.getRestrictions();
  }

  @Override
  protected int getLabelField() {
    return 0;
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, EditorScreen.class);
  }
}