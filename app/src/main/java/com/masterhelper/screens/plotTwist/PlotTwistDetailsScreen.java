package com.masterhelper.screens.plotTwist;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.screens.DetailsScreen;
import com.masterhelper.screens.NamesGenerator;
import com.masterhelper.screens.TextEditor;
import com.masterhelper.ux.TextDialogBuilder;

public class PlotTwistDetailsScreen extends DetailsScreen {
  private final int EDIT_TITLE_RESULT_CODE = 0;
  private final int EDIT_DESCRIPTION_RESULT_CODE = 1;
  private boolean isNew = false;

  PlotTwistRepository plotTwistRepository;
  PlotTwistModel currentModel;
  int[] options = new int[]{
    R.string.plot_popup_edit_title,
    R.string.plot_popup_edit_description,
    R.string.plot_popup_edit_deadline
  };

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, PlotTwistDetailsScreen.class);
  }

  @Override
  protected int[] getOptions() {
    return options;
  }

  @Override
  protected AbstractModel getCurrentModel(String id) {
    String plotTwistId = getIntent().getStringExtra(INTENT_EDIT_SCREEN_ID);
    plotTwistRepository = GlobalApplication.getAppDB().plotRepository;
    isNew = plotTwistId == null;

    if (isNew) {
      currentModel = plotTwistRepository.draft();
    } else {
      currentModel = plotTwistRepository.get(plotTwistId);
    }
    return currentModel;
  }

  protected boolean onPopupMenuItemClick(String tag, int menuItemIndex) {
    if (options[menuItemIndex] == R.string.plot_popup_edit_title) {
      Intent title = NamesGenerator.getIntent(
        this,
        currentModel.getTitle(),
        AbstractModel.TITLE_MAX_LENGTH
      );

      startActivityForResult(title, EDIT_TITLE_RESULT_CODE);
    } else if (options[menuItemIndex] == R.string.plot_popup_edit_description) {
      Intent title = TextEditor.getIntent(
        this,
        getResources().getString(R.string.plot_twist_edit),
        currentModel.getDescription(),
        AbstractModel.DESCRIPTION_MAX_LENGTH
      );

      startActivityForResult(title, EDIT_DESCRIPTION_RESULT_CODE);
    } else {
      TextDialogBuilder textDialogBuilder = new TextDialogBuilder(this);
      String value = currentModel.getDeadLine() + "";
      textDialogBuilder.setNumeric();

      textDialogBuilder.setValue(value)
        .setControlsButton((v) -> {
          String newValue = textDialogBuilder.getValue();
          currentModel.setDeadLine(Integer.parseInt(newValue));
          updateView();
        }, null)
        .setTitle(R.string.edit)
        .create()
        .show();
    }


    return true;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != RESULT_OK) {
      return;
    }

    switch (requestCode) {
      case EDIT_DESCRIPTION_RESULT_CODE:
        currentModel.setDescription(TextEditor.getIntentTextEditValue(data));
        break;
      case EDIT_TITLE_RESULT_CODE:
        currentModel.setTitle(NamesGenerator.getIntentTextEditValue(data));
        break;
    }

    if (isNew) {
      plotTwistRepository.create(currentModel);
      isNew = false;
    } else {
      plotTwistRepository.update(currentModel);
    }
    updateView();
  }

  @Override
  protected String getTitleField() {
    return currentModel.getTitle();
  }

  @Override
  protected String getSubtitleField() {
    return getResources().getString(R.string.plot_about);
  }

  @Override
  protected String getDescriptionField() {
    return currentModel.getDescription();
  }

  @Override
  protected String getLabelField() {
    return getResources().getString(R.string.plot_twist_deadline) + ": " + currentModel.getDeadLine();
  }
}