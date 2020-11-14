package com.masterhelper.filesystem;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;
import com.masterhelper.global.GlobalApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AudioPlayer {

  private MediaPlayer mediaPlayer;

  private String[] mediaList = new String[]{};

  private int currentAudioIndex = 0;

  public AudioPlayer(){
    mediaPlayer = new MediaPlayer();
  }

  public void startMediaRecord(File file) {
    if(mediaPlayer.isPlaying()){
      stopMediaRecord();
    }

    try {
      mediaPlayer.setDataSource(GlobalApplication.getAppContext(), Uri.fromFile(file));
      mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
      mediaPlayer.prepare();
      mediaPlayer.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stopMediaRecord() {
    if(mediaPlayer.isPlaying()){
      mediaPlayer.stop();
      mediaPlayer.reset();
    }
  }

  public void setMediaListOfUri(String[] newMediaList) {
    ArrayList<String> filePaths = new ArrayList<>();
    for (String strUri : newMediaList) {
      Uri fileUri = Uri.parse(Uri.decode(strUri));
      filePaths.add(fileUri.getPath());
    }
    mediaList = filePaths.toArray(new String[0]);
  }

  private String[] getMediaList(){
    return mediaList;
  }

  public void startNextMediaFile(){
    if(getMediaList().length == 0){
      return;
    }
    setCurrentAudioIndex(getCurrentAudioIndex()+1);
    String filePath = getMediaList()[getCurrentAudioIndex()];
    File file = new File(filePath);
    startMediaRecord(file);
    Toast.makeText(GlobalApplication.getAppContext(), file.getName(), Toast.LENGTH_LONG).show();
  }

  private void setCurrentAudioIndex(int currentAudioIndex) {
    if(currentAudioIndex > mediaList.length - 1){
      this.currentAudioIndex = 0;
      return;
    }
    this.currentAudioIndex = currentAudioIndex;
  }

  private int getCurrentAudioIndex() {
    return currentAudioIndex;
  }



}
