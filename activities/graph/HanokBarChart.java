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
package com.seoul.hanokmania.activities.graph;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * bar chart.
 */
public class HanokBarChart extends AbstractChart {

  /**
   * Returns the chart name.
   *
   * @return the chart name
   */
    public String getName() {
    return "Sales stacked bar chart";
  }

  /**
   * Returns the chart description.
   *
   * @return the chart description
   */
    public String getDesc() {
    return "The monthly sales for the last 2 years (stacked bar chart)";}


    @Override
    public GraphicalView getGraphView(Context context, List list) {

        String[] strLevel= new String[list.size()];

        String[] titles = new String[] { "한옥 수" };
        List<double[]> values = new ArrayList<double[]>();
        double[] item= new double[list.size()];
        String[] parm= new String[2];
        for( int i = 0; i< list.size(); i++) {
            parm= list.get(i).toString().split(",");
            strLevel[i] = parm[0];
            item[i]= Float.parseFloat(parm[1]);
        }
        values.add(item);

        int[] colors = new int[] { Color.BLUE};
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        setChartSettings(
                renderer,
                "대지 면적 구간 별 한옥 수", "대지 면적 구간", "한옥 수",
                0.5, 6.5,
                0, 200,
                Color.GRAY, Color.WHITE);
        renderer.setXLabels(1);
        renderer.setYLabels(10);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(16);
        renderer.setLegendTextSize(16);
        renderer.setShowGrid(true);
        renderer.setAxisTitleTextSize(10.0f);
        renderer.setBackgroundColor(Color.BLACK);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setBarSpacing(0.5f);
        for(int i= 0; i< strLevel.length; i++) {
            renderer.addXTextLabel(i+ 1, strLevel[i]);
        }
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
            seriesRenderer.setDisplayChartValues(true);
        }

        // Create view
        return ChartFactory.getBarChartView(
                context,
                buildBarDataset(titles, values),
                renderer,
                BarChart.Type.DEFAULT);

    }

    @Override
    public GraphicalView getGraphView(Context context) {
        return null;
    }

}
