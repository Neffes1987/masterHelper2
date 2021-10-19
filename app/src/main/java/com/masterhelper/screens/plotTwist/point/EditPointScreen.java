package com.masterhelper.screens.plotTwist.point;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.screens.EditScreen;
import com.masterhelper.screens.NamesGenerator;
import com.masterhelper.screens.TextEditor;
import com.masterhelper.ux.list.propertyBar.PropertyBar;

public class EditPointScreen extends EditScreen {
  private final int EDIT_TITLE_RESULT_CODE = 0;
  private final int EDIT_DESCRIPTION_RESULT_CODE = 1;
  public static String INTENT_PLOT_TWIST_ID = "INTENT_PLOT_TWIST_ID";

  private PointModel currentModel;
  private PointRepository pointRepository;
  private Boolean isNew = false;

  int[] options = new int[]{
    R.string.point_edit_status,
    R.string.point_edit_description,
    R.string.point_edit_name
  };

  private String convertStatusToCaption(PointModel.PointStatus status) {
    int statusResource;

    switch (status) {
      case Started:
        statusResource = R.string.point_started_status;
        break;
      case Succeed:
        statusResource = R.string.point_succeed_status;
        break;
      case Failure:
        statusResource = R.string.point_failure_status;
        break;
      case Hold:
      default:
        statusResource = R.string.point_hold_status;
    }

    return getResources().getString(statusResource);
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
    pointRepository = GlobalApplication.getAppDB().pointRepository;
    if (id != null) {
      currentModel = pointRepository.get(id);
    } else {
      currentModel = pointRepository.draft();
      currentModel.setPlotTwistId(getIntent().getStringExtra(INTENT_PLOT_TWIST_ID));
      isNew = true;
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
          convertStatusToCaption(PointModel.PointStatus.Hold),
          convertStatusToCaption(PointModel.PointStatus.Started),
          convertStatusToCaption(PointModel.PointStatus.Succeed),
          convertStatusToCaption(PointModel.PointStatus.Failure),
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

    if (options[menuItemIndex] == R.string.point_edit_description) {
      Intent editDescriptionIntent = TextEditor.getIntent(
        this,
        getResources().getString(R.string.plot_popup_edit_description),
        currentModel.getDescription(),
        AbstractModel.DESCRIPTION_MAX_LENGTH
      );

      startActivityForResult(editDescriptionIntent, EDIT_DESCRIPTION_RESULT_CODE);
    }

    if (options[menuItemIndex] == R.string.point_edit_name) {
      Intent title = NamesGenerator.getIntent(
        this,
        currentModel.getTitle(),
        AbstractModel.TITLE_MAX_LENGTH
      );

      startActivityForResult(title, EDIT_TITLE_RESULT_CODE);
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
      pointRepository.create(currentModel);
      isNew = false;
    } else {
      pointRepository.update(currentModel);
    }
    updateView();
  }

  @Override
  protected String getTitleField() {
    return currentModel.getTitle();
  }

  @Override
  protected String getSubtitleField() {
    return getResources().getString(R.string.point_about);
  }

  @Override
  protected String getDescriptionField() {
    return currentModel.getDescription();
  }

  @Override
  protected String getLabelField() {
    return convertStatusToCaption(currentModel.getStatus());
  }

  public static Intent getIntent(Context context, String plotTwistId, String pointId) {
    Intent intent = new Intent(context, EditPointScreen.class);
    intent.putExtra(INTENT_EDIT_SCREEN_ID, pointId);
    intent.putExtra(INTENT_PLOT_TWIST_ID, plotTwistId);

    return intent;
  }
}