package com.seoul.hanokmania.activities.graph; /**
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
import android.graphics.Paint;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

/**
 * Average line chart.
 */
public class HanokLineChart extends AbstractChart {

    /**
     * line chart.
     */
    public HanokLineChart() {

    }
    /**
     * Returns the chart name.
     *
     * @return the chart name
     */
    public String getName() {
        return "Average temperature";
    }

    /**
     * Returns the chart description.
     *
     * @return the chart description
     */
    public String getDesc() {
        return "The average temperature in 4 Greek islands (line chart)";
    }

    /**
     * Executes the chart.
     *
     * @param context the context
     * @return the built intent
     */
    @Override
    public GraphicalView getGraphView(Context context, List list) {

        String[] strAddr= new String[list.size()];

        // Series Set
        XYSeries xySeries = new XYSeries("년도");
        String[] parm= new String[2];
        for( int i = 0; i< list.size(); i++) {
            parm= list.get(i).toString().split(",");
            strAddr[i] = parm[0];
            xySeries.add(i, Float.parseFloat(parm[1]));
        }

        // Creating a dataset to hold series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Add series to the dataset
        dataset.addSeries(xySeries);

        // Now we create xyRenderer
        XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
        xyRenderer.setLineWidth(2);
        xyRenderer.setColor(Color.YELLOW);
        xyRenderer.setDisplayBoundingPoints(true);
        xyRenderer.setPointStyle(PointStyle.TRIANGLE);
        xyRenderer.setPointStrokeWidth(3);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.BLACK);
        multiRenderer.setShowGrid(true);
        multiRenderer.setXLabelsAlign(Paint.Align.RIGHT);
        multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitleTextSize(20);
        multiRenderer.setLabelsTextSize(16);
        multiRenderer.setLegendTextSize(16);
        multiRenderer.setChartTitle("한옥 수선 현황");
        multiRenderer.setXTitle("년도");
        multiRenderer.setYTitle("수선 횟수");
        multiRenderer.setZoomButtonsVisible(true);
        for( int i= 0;i<strAddr.length;i++){
            if (i % 2 == 0) {
                multiRenderer.addXTextLabel(i, strAddr[i]);
            }
        }

        // Adding Plottage and BuildArea renderer to multiRenderer
        multiRenderer.addSeriesRenderer(xyRenderer);

        // Create view
        return ChartFactory.getLineChartView(
                context,
                dataset,
                multiRenderer);
    }

    @Override
    public GraphicalView getGraphView(Context context) {
        return null;
    }

}
