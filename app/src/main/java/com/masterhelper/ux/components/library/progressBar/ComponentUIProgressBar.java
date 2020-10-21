package com.masterhelper.ux.components.library.progressBar;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;
import com.masterhelper.ux.components.core.ComponentUIFragment;
import com.masterhelper.ux.components.library.text.input.InputTextField;

/** */
public class ComponentUIProgressBar extends ComponentUIFragment {
    public static final int ID = R.id.COMPONENT_PROGRESS_BAR_ID;
    public ProgressBarControl controls;

    public ComponentUIProgressBar() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_component_ui_progress_bar, container, false);
        controls = new ProgressBarControl(fragmentView.findViewById(ID));
        initControls(controls);
        return fragmentView;
    }
}