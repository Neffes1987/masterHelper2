package com.masterhelper.screens.loadingScreen;

import android.content.Intent;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.masterhelper.R;
import com.masterhelper.screens.journey.JourneyEmptyScreen;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen extends AppCompatActivity {
  ProgressBarTimer progressBarTimer;
  Timer mTimer;
  int LOADER_DELAY = 100;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading_screen);
    mTimer = new Timer();
    progressBarTimer = new ProgressBarTimer(
      new Intent(this, JourneyEmptyScreen.class),
      findViewById(R.id.LOADING_BAR_ID)
    );
  }

  @Override
  protected void onStart() {
    super.onStart();
    mTimer.schedule(progressBarTimer, LOADER_DELAY, LOADER_DELAY);
  }

  class ProgressBarTimer extends TimerTask {
    int PROGRESS_STEP = 10;
    int currentProgress = 0;
    ProgressBar progressBarView;
    Intent emptyJourneyScreenIntent;

    public ProgressBarTimer(Intent emptyJourneyScreenIntent, ProgressBar progressBarView) {
      this.emptyJourneyScreenIntent = emptyJourneyScreenIntent;
      this.progressBarView = progressBarView;
    }

    @Override
    public void run() {
      if (currentProgress == 100) {
        currentProgress = 0;
        this.cancel();
        startActivity(emptyJourneyScreenIntent);
      } else {
        currentProgress += PROGRESS_STEP;
        runOnUiThread(() -> progressBarView.setProgress(currentProgress));
      }
    }
  }
}