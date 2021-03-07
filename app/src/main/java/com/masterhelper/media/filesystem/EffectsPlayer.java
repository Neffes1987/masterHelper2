package com.masterhelper.media.filesystem;

import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.widget.Toast;
import com.masterhelper.global.GlobalApplication;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class EffectsPlayer {
  private Timer effectsTimer;

  int MAX_EFFECTS_STREAMS = 5;

  int MIN_EFFECTS_RANDOM_TIME = 10000;
  int MAX_EFFECTS_RANDOM_TIME = 120000;

  private SoundPool effectsPlayer;

  private String[] mediaList = new String[]{};

  public EffectsPlayer() {

  }

  void initSoundPool() {
    effectsTimer = new Timer();
    effectsPlayer = new SoundPool(MAX_EFFECTS_STREAMS, AudioManager.STREAM_MUSIC, 0);
    effectsPlayer.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
      if (status == 0) {
        int timerDelay = getRandomTimeSec();
        Toast.makeText(GlobalApplication.getAppContext(), "Время до эффекта " + (timerDelay / 1000) + " сек.", Toast.LENGTH_SHORT).show();
        effectsTimer.schedule(new TimerTask() {
          @Override
          public void run() {
            soundPool.play(sampleId, 1, 1, getRandomListKey(), 0, 1);
            start();
          }
        }, timerDelay);
      }
    });
  }


  public void start() {
    if (mediaList.length == 0) {
      return;
    }

    if (effectsPlayer == null) {
      initSoundPool();
    }

    int randomKey = getRandomListKey();
    String filePath = mediaList[randomKey];
    effectsPlayer.load(filePath, randomKey);
  }

  public void stop() {
    if (effectsPlayer == null) {
      return;
    }
    effectsPlayer.release();
    effectsTimer.cancel();
    initSoundPool();
  }

  public void next() {
    stop();
    start();
  }

  public void setMediaListOfUri(String[] newMediaList) {
    stop();
    ArrayList<String> filePaths = new ArrayList<>();
    for (String strUri : newMediaList) {
      Uri fileUri = Uri.parse(Uri.decode(strUri));
      filePaths.add(fileUri.getPath());
    }
    mediaList = filePaths.toArray(new String[0]);
  }

  int getRandomListKey() {
    double randomNumber = Math.random();
    int lastIndexInList = mediaList.length - 1;

    int newRandomIndex = (int) (randomNumber * lastIndexInList);

    return Math.min(newRandomIndex, lastIndexInList);
  }

  int getRandomTimeSec() {
    double randomNumber = Math.random();

    return (int) ((randomNumber * ((MAX_EFFECTS_RANDOM_TIME - MIN_EFFECTS_RANDOM_TIME) + 1)) + MIN_EFFECTS_RANDOM_TIME);
  }


}
