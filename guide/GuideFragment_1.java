package com.seoul.hanokmania.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seoul.hanokmania.R;

/**
 * Created by Ray Choe on 2015-10-23.
 */
public class GuideFragment_1 extends Fragment {

    private static final String TAG = GuideFragment_1.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_1, container, false);
        view.findViewById(R.id.guide_next_button).setOnClickListener((View.OnClickListener) getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
