package com.masterhelper.ux.pages.journeys;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.buttons.text.ComponentUITextButton;
import com.masterhelper.ux.components.library.dialog.DialogComponent;
import com.masterhelper.ux.components.library.text.input.ComponentUIInputText;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.components.library.tickButton.check.ComponentUICheckBox;
import com.masterhelper.ux.components.library.tickButton.radio.ComponentUIRadioGroup;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;
import com.masterhelper.ux.components.core.SetBtnEvent;

import java.util.Arrays;

public class PageJourneyList extends AppCompatActivity {

  ComponentUILabel label;
  ComponentUIInputText field;
  ComponentUITextButton button;
  ComponentUIImageButton iconButton;
  ComponentUICheckBox checkBox;
  ComponentUIFloatingButton floatingButton;
  ComponentUIRadioGroup group;
  DialogComponent dialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page_journey_list);
    FragmentManager mn = getSupportFragmentManager();

    dialog = new DialogComponent("test", this, null);

    label = ComponentUILabel.cast(mn.findFragmentById(R.id.SOME_LABEL_ID));
    label.controls.setText("Test string");

    field = ComponentUIInputText.cast(mn.findFragmentById(R.id.SOME_INPUT_FIELD_ID));
    field.controls.setText("test");

    button = ComponentUITextButton.cast(mn.findFragmentById(R.id.SOME_TEXT_BUTTON_ID));
    button.controls.setText("test");
    button.controls.setColor(ResourceColors.ResourceColorType.secondary);
    button.controls.setOnClick(new SetBtnEvent() {
      @Override
      public void onClick(int btnId) {
        dialog.show();
      }

      @Override
      public void onLongClick(int btnId) {

      }
    });
    iconButton = ComponentUIImageButton.cast(mn.findFragmentById(R.id.SOME_IMAGE_BUTTON_ID));
    iconButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.mace));

    checkBox = ComponentUICheckBox.cast(mn.findFragmentById(R.id.SOME_IMAGE_CHECK_ID));
    checkBox.controls.setList(Arrays.asList("test", "test1", "test2"));

    floatingButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.SOME_FLOAT_BUTTON_ID));
    floatingButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.add));
    floatingButton.controls.setColor(ResourceColors.ResourceColorType.primary);

    group = ComponentUIRadioGroup.cast(mn.findFragmentById(R.id.SOME_RADIO_GROUP));
    group.controls.setList(Arrays.asList("test", "test1", "test2"));
  }
}
