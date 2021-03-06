package com.masterhelper.plotLine;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.os.Bundle;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.goals.GoalLocale;
import com.masterhelper.goals.PageGoal;
import com.masterhelper.goals.repository.GoalModel;
import com.masterhelper.goals.repository.GoalRepository;
import com.masterhelper.plotLine.repository.PlotLineModel;
import com.masterhelper.plotLine.repository.PlotLineRepository;
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.dialog.TextDialog;

import java.util.HashMap;

import static com.masterhelper.goals.PageGoal.INTENT_GOAL_ID;

public class PlotLinePage extends AppMenuActivity {
  public static final String INTENT_PLOT_ID = "plotId";
  final int actICheckboxID = R.id.ACT_I_PLOT_POINT_CHECK_ID;
  int actIBtnID = R.id.ACT_I_PLOT_POINT_BTN_ID;

  final int actIICheckboxID = R.id.ACT_II_PLOT_POINT_CHECK_ID;
  int actIIBtnID = R.id.ACT_II_PLOT_POINT_BTN_ID;

  final int actIIICheckboxID = R.id.ACT_III_PLOT_POINT_CHECK_ID;
  int actIIIBtnID = R.id.ACT_III_PLOT_POINT_BTN_ID;

  final int actIVCheckboxID = R.id.ACT_IV_PLOT_POINT_CHECK_ID;
  int actIVBtnID = R.id.ACT_IV_PLOT_POINT_BTN_ID;

  final int actVCheckboxID = R.id.ACT_V_PLOT_POINT_CHECK_ID;
  int actVBtnID = R.id.ACT_V_PLOT_POINT_BTN_ID;

  CheckBox actICheckboxView;
  CheckBox actIICheckboxView;
  CheckBox actIIICheckboxView;
  CheckBox actIVCheckboxView;
  CheckBox actVCheckboxView;
  PlotLineModel currentPlotLine;

  PlotLineRepository plotLineRepository;
  GoalRepository goalRepository;
  HashMap<String, GoalModel> goals;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    String selectedPlotId = getIntent().getStringExtra(INTENT_PLOT_ID);
    plotLineRepository = GlobalApplication.getAppDB().plotLineRepository;
    currentPlotLine = plotLineRepository.getRecord(selectedPlotId);

    goalRepository = GlobalApplication.getAppDB().goalRepository;
    goals = goalRepository.listByIds(currentPlotLine.getPlotPoints());
    goalRepository.setPlotId(selectedPlotId);

    setContentView(R.layout.activity_page_plot_line);

    UIToolbar.setTitle(this, currentPlotLine.name.get(), "");

    ImageButton actIAddControlView = findViewById(actIBtnID);
    actIAddControlView.setOnClickListener(listener);

    ImageButton actIIAddControlView = findViewById(actIIBtnID);
    actIIAddControlView.setOnClickListener(listener);

    ImageButton actIIIAddControlView = findViewById(actIIIBtnID);
    actIIIAddControlView.setOnClickListener(listener);

    ImageButton actIVAddControlView = findViewById(actIVBtnID);
    actIVAddControlView.setOnClickListener(listener);

    ImageButton actVAddControlView = findViewById(actVBtnID);
    actVAddControlView.setOnClickListener(listener);

    actICheckboxView = findViewById(actICheckboxID);
    actICheckboxView.setOnClickListener(listener);
    actICheckboxView.setText(initActName(currentPlotLine.getActIPlotLinePointId()));

    actIICheckboxView = findViewById(actIICheckboxID);
    actIICheckboxView.setOnClickListener(listener);
    actIICheckboxView.setText(initActName(currentPlotLine.getActIIPlotLinePointId()));

    actIIICheckboxView = findViewById(actIIICheckboxID);
    actIIICheckboxView.setOnClickListener(listener);
    actIIICheckboxView.setText(initActName(currentPlotLine.getActIIIPlotLinePointId()));

    actIVCheckboxView = findViewById(actIVCheckboxID);
    actIVCheckboxView.setOnClickListener(listener);
    actIVCheckboxView.setText(initActName(currentPlotLine.getActIVPlotLinePointId()));

    actVCheckboxView = findViewById(actVCheckboxID);
    actVCheckboxView.setOnClickListener(listener);
    actVCheckboxView.setText(initActName(currentPlotLine.getActVPlotLinePointId()));

    initProgress(currentPlotLine.getPlotLineProgress());
  }

  String initActName(String actId) {
    String goalTitle = "";
    if (actId != null) {
      GoalModel model = goals.get(actId);
      if (model != null) {
        goalTitle = model.name.get();
      }
    }
    return goalTitle;
  }

  void initProgress(String progressId) {
    int progressIndex = 0;
    if (progressId.length() > 0 && progressId.equals(currentPlotLine.getActIPlotLinePointId())) {
      progressIndex = 1;
    }
    if (progressId.length() > 0 && progressId.equals(currentPlotLine.getActIIPlotLinePointId())) {
      progressIndex = 2;
    }
    if (progressId.length() > 0 && progressId.equals(currentPlotLine.getActIIIPlotLinePointId())) {
      progressIndex = 3;
    }
    if (progressId.length() > 0 && progressId.equals(currentPlotLine.getActIVPlotLinePointId())) {
      progressIndex = 4;
    }
    if (progressId.length() > 0 && progressId.equals(currentPlotLine.getActVPlotLinePointId())) {
      progressIndex = 5;
    }
    actICheckboxView.setChecked(progressIndex >= 1);
    actIICheckboxView.setChecked(progressIndex >= 2);
    actIIICheckboxView.setChecked(progressIndex >= 3);
    actIVCheckboxView.setChecked(progressIndex >= 4);
    actVCheckboxView.setChecked(progressIndex >= 5);
  }

  View.OnClickListener listener = v -> {
    String selectedGoalId = "";
    if (v instanceof CheckBox) {
      if (v.getId() == actICheckboxID) {
        selectedGoalId = currentPlotLine.getActIPlotLinePointId();
        currentPlotLine.setPlotLineProgress(selectedGoalId);
        currentPlotLine.save();
        initProgress(selectedGoalId);
      }
      if (v.getId() == actIICheckboxID) {
        selectedGoalId = currentPlotLine.getActIIPlotLinePointId();
        currentPlotLine.setPlotLineProgress(selectedGoalId);
        currentPlotLine.save();
        initProgress(selectedGoalId);
      }
      if (v.getId() == actIIICheckboxID) {
        selectedGoalId = currentPlotLine.getActIIIPlotLinePointId();
        currentPlotLine.setPlotLineProgress(selectedGoalId);
        currentPlotLine.save();
        initProgress(selectedGoalId);
      }
      if (v.getId() == actIVCheckboxID) {
        selectedGoalId = currentPlotLine.getActIVPlotLinePointId();
        currentPlotLine.setPlotLineProgress(selectedGoalId);
        currentPlotLine.save();
        initProgress(selectedGoalId);
      }
      if (v.getId() == actVCheckboxID) {
        selectedGoalId = currentPlotLine.getActVPlotLinePointId();
        currentPlotLine.setPlotLineProgress(selectedGoalId);
        initProgress(selectedGoalId);
      }
    }

    if (v instanceof ImageButton) {
      int selectedGoalCheckId = 0;
      if (v.getId() == actIBtnID) {
        selectedGoalId = currentPlotLine.getActIPlotLinePointId();
        selectedGoalCheckId = actICheckboxID;
      }
      if (v.getId() == actIIBtnID) {
        selectedGoalId = currentPlotLine.getActIIPlotLinePointId();
        selectedGoalCheckId = actIICheckboxID;
      }
      if (v.getId() == actIIIBtnID) {
        selectedGoalId = currentPlotLine.getActIIIPlotLinePointId();
        selectedGoalCheckId = actIIICheckboxID;
      }
      if (v.getId() == actIVBtnID) {
        selectedGoalId = currentPlotLine.getActIVPlotLinePointId();
        selectedGoalCheckId = actIVCheckboxID;
      }
      if (v.getId() == actVBtnID) {
        selectedGoalId = currentPlotLine.getActVPlotLinePointId();
        selectedGoalCheckId = actVCheckboxID;
      }

      if (selectedGoalId.length() > 0) {
        updateGoal(selectedGoalId);
      } else {
        createNewGoal(selectedGoalCheckId);
      }
    }
  };


  void createNewGoal(int goalViewId) {
    GoalModel newModel = goalRepository.getDraftRecord();
    showDialog(newModel, goalViewId, GoalLocale.getLocalizationByKey(GoalLocale.Keys.createGoal));
  }

  void updateGoal(String selectedGoalId) {
    if (selectedGoalId.length() != 0) {
      Intent selectedGoalIntent = new Intent(this, PageGoal.class);
      selectedGoalIntent.putExtra(INTENT_GOAL_ID, selectedGoalId);
      startActivity(selectedGoalIntent);
    }
  }

  void showDialog(GoalModel model, int goalViewId, String title) {
    String moduleId = model.id.toString();
    TextDialog dialog = new TextDialog(this, title, goalRepository.getNameLength(), model.name.get(), (result) -> {
      model.name.set(result);
      model.save();
      CheckBox act = findViewById(goalViewId);
      act.setText(result);

      switch (goalViewId) {
        case actICheckboxID:
          currentPlotLine.setActIPlotPoint(moduleId);
          break;
        case actIICheckboxID:
          currentPlotLine.setActIIPlotPoint(moduleId);
          break;
        case actIIICheckboxID:
          currentPlotLine.setActIIIPlotPoint(moduleId);
          break;
        case actIVCheckboxID:
          currentPlotLine.setActIVPlotPoint(moduleId);
          break;
        case actVCheckboxID:
          currentPlotLine.setActVPlotPoint(moduleId);
          break;
      }
      currentPlotLine.save();
    });
    dialog.show();
  }

  @Override
  protected void onAppBarMenuItemControl() {

  }
}