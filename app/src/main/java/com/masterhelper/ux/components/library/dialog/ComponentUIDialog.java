package com.masterhelper.ux.components.library.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.UXElement;
import com.masterhelper.ux.components.library.text.input.ComponentUIInputText;
import com.masterhelper.ux.components.library.text.input.InputTextField;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.components.library.text.label.Label;
import com.masterhelper.ux.components.library.tickButton.check.CheckBoxesGroup;
import com.masterhelper.ux.components.library.tickButton.check.ComponentUICheckBox;
import com.masterhelper.ux.components.library.tickButton.radio.ComponentUIRadioGroup;
import com.masterhelper.ux.components.library.tickButton.radio.RadioControlsGroup;
import com.masterhelper.ux.resources.localization.DialogLocale;

/** class for working with dialogs popups */
public class ComponentUIDialog extends UXElement<View> {
  /** pointer on created dialog instance */
  AlertDialog pDialog;
  /** pointer on the currentActivity*/
  AppCompatActivity pActivity;

  public Label pNameLabel;
  public Label pDescriptionLabel;
  public InputTextField pNameField;
  public InputTextField pDescriptionField;
  public RadioControlsGroup pRadioGroup;
  public CheckBoxesGroup pCheckboxesGroup;

  private void intiRadioGroup(View dialogView){
    pRadioGroup = new RadioControlsGroup(dialogView.findViewById(R.id.DIALOG_RADIOS_ID).findViewById(ComponentUIRadioGroup.ID));
    pRadioGroup.hide();
  }

  private void intiCheckboxGroup(View dialogView){
    pCheckboxesGroup = new CheckBoxesGroup(dialogView.findViewById(R.id.DIALOG_CHECKBOXES_ID).findViewById(ComponentUICheckBox.ID));
    pCheckboxesGroup.hide();
  }

  private void initName(View dialogView){
    pNameLabel = new Label(dialogView.findViewById(R.id.DIALOG_NAME_LABEL_ID).findViewById(ComponentUILabel.ID));
    pNameLabel.hide();

    pNameField = new InputTextField(dialogView.findViewById(R.id.DIALOG_NAME_INPUT_ID).findViewById(ComponentUIInputText.ID));
    pNameField.hide();

  }

  private void initDescription(View dialogView){
    pDescriptionLabel = new Label(dialogView.findViewById(R.id.DIALOG_DESCRIPTION_LABEL_ID).findViewById(ComponentUILabel.ID));
    pDescriptionLabel.hide();
    pDescriptionField = new InputTextField(dialogView.findViewById(R.id.DIALOG_DESCRIPTION_INPUT_ID).findViewById(ComponentUIInputText.ID));
    pDescriptionField.hide();
  }

  /**
   * @param activity - activity class for getting context
   * @param listener - class with callbacks for resolve and reject buttons
   * */
  public ComponentUIDialog(@NonNull String dialogTitle, @NonNull AppCompatActivity activity, final DialogClickListener listener){
    this.pActivity = activity;

    AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(activity);
    View dialogView = activity.getLayoutInflater().inflate(R.layout.fragment_component_dialog, null);
    this.setUxElement(dialogView);

    dialogBuilder.setView(dialogView);
    dialogBuilder
      .setTitle(dialogTitle)
      .setPositiveButton(DialogLocale.getLocalizationByKey(DialogLocale.Keys.resolveBtn), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface d, int v) {
          if(listener != null){
            listener.onResolve();
          }
        }
      })
      .setNegativeButton(DialogLocale.getLocalizationByKey(DialogLocale.Keys.rejectBtn), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          if(listener != null){
            listener.onReject();
          }
        }
      });
    pDialog = dialogBuilder.create();

    initName(dialogView);
    initDescription(dialogView);
    intiRadioGroup(dialogView);
    intiCheckboxGroup(dialogView);
  }

  /** show dialog in app */
  public void show() {
    super.show();
    pDialog.show();
  }

  /** hide dialog in app */
  @Override
  public void hide() {
    super.hide();
    pDialog.hide();
  }

  /** callbacks for dialog buttons */
  public interface DialogClickListener{
    void onResolve();
    void onReject();
  }
}
