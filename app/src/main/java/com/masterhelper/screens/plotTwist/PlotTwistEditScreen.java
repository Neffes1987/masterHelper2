package com.masterhelper.screens.plotTwist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.screens.CommonScreen;
import com.masterhelper.ux.ApplyButtonFragment;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.TextDialogBuilder;
import com.masterhelper.ux.list.propertyBar.PropertyBar;

public class PlotTwistEditScreen extends CommonScreen {
  public static final String INTENT_PLOT_TWIST_ID = "plotTwistId";
  String plotTwistId;

  ContextPopupMenuBuilder popupBuilder;
  PlotTwistRepository plotTwistRepository;
  PlotTwistModel currentModel;

  PropertyBar nameFragment;
  PropertyBar descriptionFragment;
  PropertyBar deadlineFragment;

  final String NAME = "name";
  final String DESCRIPTION = "description";
  final String DEADLINE = "deadline";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_plot_line_edit);
    setActionBarTitle(R.string.plot_twist_edit);
    showBackButton(true);

    plotTwistId = getIntent().getStringExtra(INTENT_PLOT_TWIST_ID);
    plotTwistRepository = GlobalApplication.getAppDB().plotRepository;

    if (plotTwistId == null) {
      currentModel = plotTwistRepository.draft();
    } else {
      currentModel = plotTwistRepository.get(plotTwistId);
    }

    popupBuilder = new ContextPopupMenuBuilder(new int[]{
      R.string.edit
    });
    popupBuilder.setPopupMenuClickHandler(this::onPopupMenuItemClick);
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, PlotTwistEditScreen.class);
  }

  @Override
  protected void onInitScreen() {
    ApplyButtonFragment applyButtonFragment = (ApplyButtonFragment) getSupportFragmentManager().findFragmentById(R.id.APPLY_BUTTON_FRAGMENT_ID);
    if (applyButtonFragment != null) {
      applyButtonFragment.setListener(v -> {
        if (currentModel.getTitle().length() == 0) {
          Toast.makeText(this, R.string.plot_edit_title_error, Toast.LENGTH_LONG).show();
          return;
        }

        if (currentModel.getDescription().length() == 0) {
          Toast.makeText(this, R.string.plot_description_title_error, Toast.LENGTH_LONG).show();
          return;
        }

        if (currentModel.getDeadLine() == 0) {
          Toast.makeText(this, R.string.plot_deadline_title_error, Toast.LENGTH_LONG).show();
          return;
        }

        if (plotTwistId != null && plotTwistId.length() > 0) {
          plotTwistRepository.update(currentModel);
        } else {
          plotTwistRepository.create(currentModel);
        }

        Intent result = new Intent();
        result.putExtra(INTENT_PLOT_TWIST_ID, currentModel.getId());
        setResult(RESULT_OK, result);
        finish();
      });
    }


    nameFragment = initProperty(R.id.PLOT_TITLE_FRAGMENT_ID, R.string.plot_twist_name, currentModel.getTitle(), NAME, popupBuilder);
    descriptionFragment = initProperty(R.id.PLOT_DESCRIPTION_FRAGMENT_ID, R.string.plot_twist_description, currentModel.getDescription(), DESCRIPTION, popupBuilder);
    deadlineFragment = initProperty(R.id.PLOT_DEADLINE_FRAGMENT_ID, R.string.plot_twist_deadline, currentModel.getDeadLine() + "", DEADLINE, popupBuilder);
  }

  boolean onPopupMenuItemClick(String tag, int menuItemIndex) {
    TextDialogBuilder textDialogBuilder = new TextDialogBuilder(this);
    String value = "";
    switch (tag) {
      case NAME:
        value = currentModel.getTitle();
        textDialogBuilder.setMaxLength(PlotTwistModel.TITLE_MAX_LENGTH);
        break;
      case DESCRIPTION:
        value = currentModel.getDescription();
        textDialogBuilder.setMaxLength(PlotTwistModel.DESCRIPTION_MAX_LENGTH);
        break;
      case DEADLINE:
        value = currentModel.getDeadLine() + "";
        textDialogBuilder.setNumeric();
        break;
    }

    AlertDialog dialog = textDialogBuilder
      .setValue(value)
      .setControlsButton((v) -> {
        String newValue = textDialogBuilder.getValue();
        switch (tag) {
          case NAME:
            currentModel.setTitle(newValue);
            nameFragment.setTitle(newValue);
            break;
          case DESCRIPTION:
            currentModel.setDescription(newValue);
            descriptionFragment.setTitle(newValue);
            break;
          case DEADLINE:
            currentModel.setDeadLine(Integer.parseInt(newValue));
            deadlineFragment.setTitle(newValue);

            break;
        }
      }, null)
      .setTitle(R.string.edit)
      .create();

    dialog.show();
    return true;
  }
}