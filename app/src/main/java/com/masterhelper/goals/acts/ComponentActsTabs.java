package com.masterhelper.goals.acts;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import com.masterhelper.R;


public class ComponentActsTabs extends Fragment {
    IActsTabs mCallback;

    public ComponentActsTabs() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (IActsTabs) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
              + " must implement OnHeadlineSelectedListener");
        }
    }

    void initTabs(View view){
        TabLayout acts = view.findViewById(R.id.ACTS_TABS_ID);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_acts_tabs, container, false);
        initTabs(view);
        return view;
    }
}