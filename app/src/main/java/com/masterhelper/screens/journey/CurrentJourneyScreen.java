package com.masterhelper.screens.journey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.masterhelper.R;
import com.masterhelper.screens.CommonScreen;

public class CurrentJourneyScreen extends CommonScreen {
  Button currentActName;
  ImageButton prevActButton;
  ImageButton nextActButton;

  int MIN_ACT_NUMBER = 0;
  int MAX_ACT_NUMBER = 11;
  int currentActIndex = MIN_ACT_NUMBER;

  int[] ACT_TITLES = {
    R.string.act_number_1,
    R.string.act_number_2,
    R.string.act_number_3,
    R.string.act_number_4,
    R.string.act_number_5,
    R.string.act_number_6,
    R.string.act_number_7,
    R.string.act_number_8,
    R.string.act_number_9,
    R.string.act_number_10,
    R.string.act_number_11,
    R.string.act_number_12
  };

  int[] ACT_TITLES_DESCRIPTIONS = {
    R.string.act_number_1_description,
    R.string.act_number_2_description,
    R.string.act_number_3_description,
    R.string.act_number_4_description,
    R.string.act_number_5_description,
    R.string.act_number_6_description,
    R.string.act_number_7_description,
    R.string.act_number_8_description,
    R.string.act_number_9_description,
    R.string.act_number_10_description,
    R.string.act_number_11_description,
    R.string.act_number_12_description
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_current_journey_screen);

    currentActName = findViewById(R.id.JOURNEY_CURRENT_ACT_BUTTON_ID);
    currentActName.setOnLongClickListener(v -> {
      AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

      dialogBuilder
        .setTitle(ACT_TITLES[currentActIndex])
        .setMessage(ACT_TITLES_DESCRIPTIONS[currentActIndex])
        .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());

      dialogBuilder.create().show();
      return false;
    });

    prevActButton = findViewById(R.id.JOURNEY_PREV_ACT_BUTTON_ID);
    prevActButton.setOnClickListener(this::toggleAct);
    prevActButton.setVisibility(View.GONE);

    nextActButton = findViewById(R.id.JOURNEY_NEXT_ACT_BUTTON_ID);
    nextActButton.setOnClickListener(this::toggleAct);

    addContextMenuItems(new Integer[]{R.string.edit});
  }

  private void toggleAct(View v) {
    if (v.getId() == R.id.JOURNEY_PREV_ACT_BUTTON_ID) {
      currentActIndex -= 1;
    }

    if (v.getId() == R.id.JOURNEY_NEXT_ACT_BUTTON_ID) {
      currentActIndex += 1;
    }

    prevActButton.setVisibility(currentActIndex <= MIN_ACT_NUMBER ? View.GONE : View.VISIBLE);
    nextActButton.setVisibility(currentActIndex >= MAX_ACT_NUMBER ? View.GONE : View.VISIBLE);

    currentActName.setText(ACT_TITLES[currentActIndex]);

    // TODO: set page update from repo for each call
  }

  @Override
  protected void onInitScreen() {
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, CurrentJourneyScreen.class);
  }
}