package com.seoul.hanokmania.activities.graph;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
public class HanokPieChart extends AbstractChart {
    /**
     * pie chart.
     */
//        int[] COLORS = new int[]{Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN};
//        double[] VALUES = new double[]{10, 11, 12, 13};
//        String[] NAME_LIST = new String[]{"A", "B", "C", "D"};
    private int[] mColors;
    private int[] mValues;
    private String[] mName_List;

    public HanokPieChart() {}

    public HanokPieChart(int[] colors, int[] values, String[] name_list) {
        mColors= colors;
        mValues= values;
        mName_List= name_list;
    }
    /**
     * Returns the chart name.
     *
     * @return the chart name
     */
    public String getName() {
        return "Sales stacked pie chart";
    }

    /**
     * Returns the chart description.
     *
     * @return the chart description
     */
    public String getDesc() {
        return "The monthly sales for the last 2 years (stacked pie chart)";
    }

    @Override
    public GraphicalView getGraphView(Context context) {

        CategorySeries mSeries = new CategorySeries("");

        DefaultRenderer mRenderer = new DefaultRenderer();

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.BLACK);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(16);
        mRenderer.setLegendTextSize(16);
        mRenderer.setMargins(new int[]{20, 30, 15, 0});
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);

        for (int i = 0; i < mValues.length; i++) {
            mSeries.add(mName_List[i] + " " + mValues[i], mValues[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(mColors[(mSeries.getItemCount() - 1) % mColors.length]);
            mRenderer.addSeriesRenderer(renderer);
        }

        return ChartFactory.getPieChartView(
                context,
                mSeries,
                mRenderer);
    }

    @Override
    public GraphicalView getGraphView(Context context, List list) {
        int[] colors = new int[]{Color.GREEN, Color.YELLOW, Color.BLUE,
                            Color.MAGENTA, Color.CYAN};
        String[] name_list = new String[9];
        int[] values = new int[9];

        String[] parm;
        for( int i = 0; i< list.size(); i++) {
            parm = list.get(i).toString().split(",");
            name_list[i] = parm[0];
            values[i] = Integer.parseInt(parm[1]);
        }

        CategorySeries mSeries = new CategorySeries("");

        DefaultRenderer mRenderer = new DefaultRenderer();

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.BLACK);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setMargins(new int[]{20, 30, 15, 0});
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);

        for (int i = 0; i < list.size(); i++) {
            mSeries.add(name_list[i] + " " + values[i], values[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(colors[(mSeries.getItemCount() - 1) % colors.length]);
            mRenderer.addSeriesRenderer(renderer);
        }

        return ChartFactory.getPieChartView(
                context,
                mSeries,
                mRenderer);
    }
}