package com.masterhelper.ux.journeys;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.fragments.*;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

  ComponentUILabel label;
  ComponentUIInputText field;
  ComponentUITextButton button;
  ComponentUIImageButton iconButton;
  ComponentUICheckBox checkBox;
  ComponentUIFloatingButton floatingButton;
  ComponentUIRadioGroup group;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    FragmentManager mn = getSupportFragmentManager();

    label = ComponentUILabel.cast(mn.findFragmentById(R.id.SOME_LABEL_ID));
    label.controls.setText("Test string");

    field = ComponentUIInputText.cast(mn.findFragmentById(R.id.SOME_INPUT_FIELD_ID));
    field.controls.setText("test");

    button = ComponentUITextButton.cast(mn.findFragmentById(R.id.SOME_TEXT_BUTTON_ID));
    button.controls.setText("test");
    button.controls.setColor("#FF0000");
    button.controls.setOnClick(new SetBtnEvent() {
      @Override
      public void onClick(int btnId) {

      }

      @Override
      public void onLongClick(int btnId) {

      }
    });
    iconButton = ComponentUIImageButton.cast(mn.findFragmentById(R.id.SOME_IMAGE_BUTTON_ID));
    iconButton.controls.setIcon(R.drawable.ic_launcher_foreground);

    checkBox = ComponentUICheckBox.cast(mn.findFragmentById(R.id.SOME_IMAGE_CHECK_ID));
    checkBox.controls.setLabelText("test");

    floatingButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.SOME_FLOAT_BUTTON_ID));
    floatingButton.controls.setIcon(R.mipmap.ic_launcher_round);
    floatingButton.controls.setColor("#FF00FF");

    group = ComponentUIRadioGroup.cast(mn.findFragmentById(R.id.SOME_RADIO_GROUP));
    group.controls.setRadioList(Arrays.asList("test", "test1", "test2"));
  }

}
