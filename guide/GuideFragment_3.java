package com.seoul.hanokmania.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seoul.hanokmania.R;

/**
 * Created by Ray Choe on 2015-10-30.
 */
public class GuideFragment_3 extends Fragment {

    private static final String TAG = GuideFragment_3.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_3, container, false);
        view.findViewById(R.id.guide_close_button).setOnClickListener((View.OnClickListener) getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
