package com.masterhelper.locations.tabs;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import com.masterhelper.R;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class LocationTabs extends Fragment {
  ITabs mCallback;

  public LocationTabs() {
    // Required empty public constructor
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onAttach(@NotNull Activity activity) {
    super.onAttach(activity);

    // This makes sure that the container activity has implemented
    // the callback interface. If not, it throws an exception
    try {
      mCallback = (ITabs) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
        + " must implement OnHeadlineSelectedListener");
    }
  }

  void initTabs(View view) {
    TabLayout acts = view.findViewById(R.id.LOCATION_FRAGMENT_TABS_ID);
    acts.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        mCallback.updateSelectedTab(acts.getSelectedTabPosition() + 1);
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_part_location_tabs, container, false);
    initTabs(view);
    return view;
  }
}