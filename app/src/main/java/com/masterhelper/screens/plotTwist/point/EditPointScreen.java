package com.masterhelper.screens.plotTwist.point;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.screens.EditScreen;
import com.masterhelper.ux.TextDialogBuilder;
import com.masterhelper.ux.list.propertyBar.PropertyBar;

import static com.masterhelper.screens.plotTwist.PlotTwistEditScreen.INTENT_PLOT_TWIST_ID;

public class EditPointScreen extends EditScreen {
  public static String INTENT_POINT_ID = "pointId";
  PointModel currentModel;

  int[] options = new int[]{
    R.string.point_edit_status,
    R.string.point_edit_description,
    R.string.point_edit_name
  };

  private int convertStatusToCaption(PointModel.PointStatus status) {
    switch (status) {
      case Started:
        return R.string.point_started_status;
      case Succeed:
        return R.string.point_succeed_status;
      case Failure:
        return R.string.point_failure_status;
      case Hold:
      default:
        return R.string.point_hold_status;
    }
  }

  @Override
  protected int[] getOptions() {
    return options;
  }

  @Override
  protected PropertyBar.CardStatus getStatusField() {
    switch (currentModel.getStatus()) {
      case Started:
        return PropertyBar.CardStatus.Attention;
      case Succeed:
        return PropertyBar.CardStatus.Success;
      case Failure:
        return PropertyBar.CardStatus.Danger;
      case Hold:
      default:
        return PropertyBar.CardStatus.Active;
    }
  }

  @Override
  protected AbstractModel getCurrentModel(String id) {
    if (id != null) {
      currentModel = GlobalApplication.getAppDB().pointRepository.get(id);
    } else {
      currentModel = GlobalApplication.getAppDB().pointRepository.draft();
      currentModel.setPlotTwistId(getIntent().getStringExtra(INTENT_PLOT_TWIST_ID));
    }

    return currentModel;
  }

  protected boolean onPopupMenuItemClick(String tag, int menuItemIndex) {
    if (options[menuItemIndex] == R.string.point_edit_status) {
      int selectedItem;

      switch (currentModel.getStatus()) {
        case Started:
          selectedItem = 1;
          break;
        case Succeed:
          selectedItem = 2;
          break;
        case Failure:
          selectedItem = 3;
          break;
        case Hold:
        default:
          selectedItem = 0;
      }

      AlertDialog statusDialog = new AlertDialog.Builder(this)
        .setTitle(R.string.point_status)
        .setSingleChoiceItems(new CharSequence[]{
          getResources().getString(convertStatusToCaption(PointModel.PointStatus.Hold)),
          getResources().getString(convertStatusToCaption(PointModel.PointStatus.Started)),
          getResources().getString(convertStatusToCaption(PointModel.PointStatus.Succeed)),
          getResources().getString(convertStatusToCaption(PointModel.PointStatus.Failure)),
        }, selectedItem, (dialog, which) -> {
          switch (which) {
            case 1:
              currentModel.setStatus(PointModel.PointStatus.Started);
              break;
            case 2:
              currentModel.setStatus(PointModel.PointStatus.Succeed);
              break;
            case 3:
              currentModel.setStatus(PointModel.PointStatus.Failure);
              break;
            case 0:
            default:
              currentModel.setStatus(PointModel.PointStatus.Hold);
              break;
          }

          updateView();

          dialog.dismiss();
        })
        .create();

      statusDialog.show();
      return true;
    }
    TextDialogBuilder textDialogBuilder = new TextDialogBuilder(this);

    if (options[menuItemIndex] == R.string.point_edit_description) {
      textDialogBuilder.setMaxLength(PointModel.DESCRIPTION_MAX_LENGTH);

      textDialogBuilder
        .setValue(currentModel.getDescription())
        .setControlsButton((v) -> {
          String newValue = textDialogBuilder.getValue();

          currentModel.setTitle(newValue);
          updateView();
        }, null)
        .setTitle(R.string.edit)
        .create().show();
    }

    if (options[menuItemIndex] == R.string.point_edit_name) {
      textDialogBuilder.setMaxLength(PointModel.TITLE_MAX_LENGTH);

      textDialogBuilder
        .setValue(currentModel.getTitle())
        .setControlsButton((v) -> {
          String newValue = textDialogBuilder.getValue();

          currentModel.setTitle(newValue);
          updateView();
        }, null)
        .setTitle(R.string.edit)
        .create()
        .show();
    }
    return true;
  }

  @Override
  protected void onUserApplyChanges(View v) {

  }

  @Override
  protected String getTitleField() {
    return currentModel.getTitle();
  }

  @Override
  protected String getDescriptionField() {
    return currentModel.getDescription();
  }

  @Override
  protected int getLabelField() {
    return convertStatusToCaption(currentModel.getStatus());
  }

  public static Intent getIntent(Context context, String plotTwistId, String pointId) {
    Intent intent = new Intent(context, EditPointScreen.class);
    intent.putExtra(INTENT_EDIT_SCREEN_ID, pointId);
    intent.putExtra(INTENT_PLOT_TWIST_ID, plotTwistId);

    return intent;
  }
}