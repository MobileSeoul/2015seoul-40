package com.seoul.hanokmania.guide;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.seoul.hanokmania.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray Choe on 2015-10-23.
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = GuideActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private ImageView mIndicator;
    private List<Fragment> mFragmentList;
    private GuideAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);


        mViewPager = (ViewPager) findViewById(R.id.guide_view_pager);
        mIndicator = (ImageView) findViewById(R.id.guide_indicator);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new GuideFragment_1());
        mFragmentList.add(new GuideFragment_2());
        mFragmentList.add(new GuideFragment_3());

        mAdapter = new GuideAdapter(getSupportFragmentManager(), mFragmentList);

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guide_next_button:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+ 1);
                break;
            case R.id.guide_close_button:
                SharedPreferences sharedPreferences = getSharedPreferences("hanokmania", MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("first", false).commit();
                finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (position) {
            case 0:
                mIndicator.setImageResource(R.drawable.indicator_1);
                break;
            case 1:
                mIndicator.setImageResource(R.drawable.indicator_2);
                break;
            case 2:
                mIndicator.setImageResource(R.drawable.indicator_3);
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
