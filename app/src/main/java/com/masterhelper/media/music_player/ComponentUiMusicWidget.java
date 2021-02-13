package com.masterhelper.media.music_player;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

/**
 *
 */
public class ComponentUiMusicWidget extends Fragment implements SetBtnLocation {
  IMusicPlayerWidget mCallback;

  TextView trackTitle;
  ComponentUIImageButton play;
  ComponentUIImageButton next;

  public ComponentUiMusicWidget() {
    // Required empty public constructor
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    // This makes sure that the container activity has implemented
    // the callback interface. If not, it throws an exception
    try {
      mCallback = (IMusicPlayerWidget) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
        + " must implement IMusicPlayerWidget");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_component_ui_music_widget, container, false);
    trackTitle = view.findViewById(R.id.MUSIC_PLAYER_TRACK_TITLE_ID);
    play = ComponentUIImageButton.cast(getChildFragmentManager().findFragmentById(R.id.MUSIC_PLAYER_PLAY_ID));
    play.controls.setOnClick(this);
    play.controls.setId(View.generateViewId());

    next = ComponentUIImageButton.cast(getChildFragmentManager().findFragmentById(R.id.MUSIC_PLAYER_NEXT_ID));
    next.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.forward));
    next.controls.setIconColor(ResourceColors.ResourceColorType.primary);
    next.controls.setOnClick(this);
    next.controls.setId(View.generateViewId());
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    togglePlayButton(mCallback.checkIsPlaying());
  }

  void togglePlayButton(boolean isPlayed) {
    if (!isPlayed) {
      play.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.play));
      play.controls.setIconColor(ResourceColors.ResourceColorType.primary);
    } else {
      play.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.pause));
      play.controls.setIconColor(ResourceColors.ResourceColorType.attention);
    }
    trackTitle.setText(mCallback.getCurrentTrackName());
  }

  /**
   * click callback for short click event
   *
   * @param btnId - element unique id that fired event
   * @param tag   - element unique tag for searching in list
   */
  @Override
  public void onClick(int btnId, String tag) {
    if (btnId == play.controls.getId()) {
      boolean isPlayed = mCallback.checkIsPlaying();
      if (isPlayed) {
        mCallback.stop();
        togglePlayButton(false);
      } else {
        mCallback.play();
        togglePlayButton(true);
      }

    }
    if (btnId == next.controls.getId()) {
      mCallback.next();
      togglePlayButton(true);
    }
  }

  /**
   * click callback for long click event
   *
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {

  }
}