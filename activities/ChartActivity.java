package com.seoul.hanokmania.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.query.QueryContract;

import org.achartengine.GraphicalView;

/**
 * Created by junsuk on 2015. 10. 22..
 *
 * 차트 상세 보기
 */
public class ChartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chart);


        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.rootLayout);

        GraphicalView graphicalView= QueryContract.mChartView;

        // If the specified child already has a parent,
        // You must call removeView() on the child's parent first
        if(graphicalView.getParent()!= null) {
            ((ViewGroup) graphicalView.getParent()).removeAllViewsInLayout();
        }

        rootLayout.addView(graphicalView);
    }

}
