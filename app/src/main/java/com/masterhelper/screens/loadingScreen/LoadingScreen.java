package com.masterhelper.screens.loadingScreen;

import android.content.Intent;
import android.widget.ProgressBar;
import android.os.Bundle;
import com.masterhelper.R;
import com.masterhelper.screens.CommonScreen;
import com.masterhelper.screens.journey.CurrentScreen;
import com.masterhelper.screens.journey.EmptyCurrentScreen;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen extends CommonScreen {
  ProgressBarTimer progressBarTimer;
  Timer mTimer;
  int LOADER_DELAY = 100;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading_screen);
    mTimer = new Timer();
    Intent intent;

    if (getSetting(Setting.JourneyId).length() > 0) {
      intent = CurrentScreen.getScreenIntent(this, getSetting(Setting.JourneyId));
    } else {
      intent = new Intent(this, EmptyCurrentScreen.class);
    }

    progressBarTimer = new ProgressBarTimer(
      intent,
      findViewById(R.id.LOADING_BAR_ID)
    );
  }

  @Override
  protected void onStart() {
    super.onStart();
    mTimer.schedule(progressBarTimer, LOADER_DELAY, LOADER_DELAY);
  }

  @Override
  protected void onInitScreen() {

  }

  class ProgressBarTimer extends TimerTask {
    int PROGRESS_STEP = 10;
    int currentProgress = 0;
    ProgressBar progressBarView;
    Intent intent;

    public ProgressBarTimer(Intent intent, ProgressBar progressBarView) {
      this.intent = intent;
      this.progressBarView = progressBarView;
    }

    @Override
    public void run() {
      if (currentProgress == 100) {
        currentProgress = 0;
        this.cancel();

        startActivity(intent);
      } else {
        currentProgress += PROGRESS_STEP;
        runOnUiThread(() -> progressBarView.setProgress(currentProgress));
      }
    }
  }
}