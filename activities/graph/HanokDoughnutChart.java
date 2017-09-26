package com.seoul.hanokmania.activities.graph;

/**
 * Created by student on 2015-10-23.
 */
/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.renderer.DefaultRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Budget pie chart.
 */
public class HanokDoughnutChart extends AbstractChart {
//      titles.add(new String[] { "P1", "P2", "P3", "P4", "P5" });
//      titles.add(new String[] { "Project1", "Project2", "Project3", "Project4", "Project5" });
//      values.add(new double[] { 12, 14, 11, 10, 19 });
//      values.add(new double[] { 10, 9, 14, 20, 11 });
//      int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };

    private String mProject;
    private String[] mTitles;
    private double[] mValues;
    private int[] mColors;

    public HanokDoughnutChart(int[] colors, double[] values,
                                    String[] titles, String project) {
        mProject= project;
        mTitles= titles;
        mValues= values;
        mColors= colors;
    }
    /**
     * Returns the chart name.
     *
     * @return the chart name
     */
    public String getName() {
        return "Budget chart for several years";
    }

    /**
     * Returns the chart description.
     *
     * @return the chart description
     */
    public String getDesc() {
        return "The budget per project for several years (doughnut chart)";
    }

    @Override
    public GraphicalView getGraphView(Context context) {

        List<String[]> titles = new ArrayList<>();
        titles.add(mTitles);
        List<double[]> values = new ArrayList<>();
        values.add(mValues);

        DefaultRenderer renderer = buildCategoryRenderer(mColors);
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.BLACK);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(16);
        renderer.setLegendTextSize(16);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        renderer.setZoomButtonsVisible(true);
        renderer.setLabelsColor(Color.WHITE);

        return ChartFactory.getDoughnutChartView(
                context,
                buildMultipleCategoryDataset(mProject, titles, values),
                renderer
        );

    }

    @Override
    public GraphicalView getGraphView(Context context, List list) {
        return null;
    }

}
