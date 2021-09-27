package com.masterhelper.ux;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;

public class ActsFragment extends Fragment {
  Button currentActName;
  ImageButton prevActButton;
  ImageButton nextActButton;

  int MIN_ACT_NUMBER = 0;
  int MAX_ACT_NUMBER = 11;

  int currentJourney = MAX_ACT_NUMBER;

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
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_acts, container, false);

    currentActName = view.findViewById(R.id.JOURNEY_CURRENT_ACT_BUTTON_ID);
    currentActName.setOnLongClickListener(v -> {
      AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

      dialogBuilder
        .setTitle(ACT_TITLES[currentJourney])
        .setMessage(ACT_TITLES_DESCRIPTIONS[currentJourney])
        .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());

      dialogBuilder.create().show();
      return false;
    });

    prevActButton = view.findViewById(R.id.JOURNEY_PREV_ACT_BUTTON_ID);
    prevActButton.setOnClickListener(this::toggleAct);

    nextActButton = view.findViewById(R.id.JOURNEY_NEXT_ACT_BUTTON_ID);
    nextActButton.setOnClickListener(this::toggleAct);
    return view;
  }

  public void setCurrentActIndex(int index) {
    currentActName.setText(ACT_TITLES[index]);
    currentJourney = index;
    prevActButton.setVisibility(index <= MIN_ACT_NUMBER ? View.GONE : View.VISIBLE);
    nextActButton.setVisibility(index >= MAX_ACT_NUMBER ? View.GONE : View.VISIBLE);
  }

  private void toggleAct(View v) {
    if (v.getId() == R.id.JOURNEY_PREV_ACT_BUTTON_ID) {
      currentJourney -= 1;
    }

    if (v.getId() == R.id.JOURNEY_NEXT_ACT_BUTTON_ID) {
      currentJourney += 1;
    }

    setCurrentActIndex(currentJourney);
    if (getActivity() instanceof ActControls) {
      ((ActControls) getActivity()).onActChanged(currentJourney);
    }
  }

  public interface ActControls {
    void onActChanged(int newActNumber);
  }
}