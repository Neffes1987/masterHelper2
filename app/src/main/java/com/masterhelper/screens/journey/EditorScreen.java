package com.masterhelper.screens.journey;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.global.db.repository.AbstractModel;
import com.masterhelper.screens.EditScreen;
import com.masterhelper.screens.NamesGenerator;
import com.masterhelper.screens.TextEditor;

public class EditorScreen extends EditScreen {
  private final int EDIT_TITLE_RESULT_CODE = 0;
  private final int EDIT_DESCRIPTION_RESULT_CODE = 1;
  private final int EDIT_RESTRICTIONS_RESULT_CODE = 2;

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
    if (options[menuItemIndex] == R.string.journey_popup_edit_title) {
      Intent title = NamesGenerator.getIntent(
        this,
        journeyModel.getTitle(),
        JourneyModel.TITLE_MAX_LENGTH
      );

      startActivityForResult(title, EDIT_TITLE_RESULT_CODE);
    } else if (options[menuItemIndex] == R.string.journey_popup_edit_description_title) {
      Intent editDescriptionIntent = TextEditor.getIntent(
        this,
        getResources().getString(R.string.journey_popup_edit_description_title),
        journeyModel.getDescription(),
        JourneyModel.DESCRIPTION_MAX_LENGTH
      );

      startActivityForResult(editDescriptionIntent, EDIT_DESCRIPTION_RESULT_CODE);
    } else {
      Intent editRestrictionIntent = TextEditor.getIntent(
        this,
        getResources().getString(R.string.journey_popup_edit_restrictions_title),
        journeyModel.getRestrictions(),
        JourneyModel.RESTRICTION_MAX_LENGTH
      );

      startActivityForResult(editRestrictionIntent, EDIT_RESTRICTIONS_RESULT_CODE);
    }
    return true;
  }

  @Override
  protected String getTitleField() {
    return journeyModel.getTitle();
  }

  @Override
  protected String getSubtitleField() {
    return getResources().getString(R.string.journey_about);
  }

  @Override
  protected String getDescriptionField() {
    String descriptionContent = journeyModel.getDescription();
    String descriptionHtml = "";

    String restrictionContent = journeyModel.getRestrictions();
    String restrictionHtml = "";

    String template = "<h4>{0}</h4><p>{1}</p>";

    if (descriptionContent.length() > 0) {
      descriptionHtml = journeyModel.printToHtml(
        template + "<br/>",
        new String[]{
          getResources().getString(R.string.journey_theme),
          journeyModel.getDescription().replaceAll("/\r/", "<br/>")
        });
    }

    if (restrictionContent.length() > 0) {
      restrictionHtml = journeyModel.printToHtml(
        template,
        new String[]{
          getResources().getString(R.string.journey_restrictions),
          journeyModel.getRestrictions()
        });
    }

    return descriptionHtml + restrictionHtml;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode != RESULT_OK) {
      return;
    }

    switch (requestCode) {
      case EDIT_DESCRIPTION_RESULT_CODE:
        journeyModel.setDescription(TextEditor.getIntentTextEditValue(data));
        break;
      case EDIT_RESTRICTIONS_RESULT_CODE:
        journeyModel.setRestrictions(TextEditor.getIntentTextEditValue(data));
        break;
      case EDIT_TITLE_RESULT_CODE:
        journeyModel.setTitle(NamesGenerator.getIntentTextEditValue(data));
        break;
    }

    repository.update(journeyModel);
    updateView();
  }

  @Override
  protected String getLabelField() {
    return "";
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, EditorScreen.class);
  }
}