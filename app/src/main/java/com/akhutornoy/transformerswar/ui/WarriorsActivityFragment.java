package com.akhutornoy.transformerswar.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.transformerswar.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class WarriorsActivityFragment extends Fragment {

    public WarriorsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_warriors, container, false);
    }
}