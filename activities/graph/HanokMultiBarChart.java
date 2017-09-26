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
 * Created by namudak on 2015-10-24.
 */
public class HanokMultiBarChart extends AbstractChart {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDesc() {
        return null;
    }

    @Override
    public GraphicalView getGraphView(Context context) {
        return null;
    }

    @Override
    public GraphicalView getGraphView(Context context, List list) {
        String[] titles = new String[] { "대지 면적(㎡)", "건축 면적(㎡)", "연 면적(㎡)"};

        String[] strLevel= new String[6];

        List<double[]> values = new ArrayList<>();

        double[] item= new double[6];
        String[] parm= new String[2];
        for( int i = 0; i< list.size(); i++) {
            List nList= (List)list.get(i);
            parm= nList.get(0).toString().split(",");
            strLevel[0] = parm[0];
            item[0]= Float.parseFloat(parm[1]);
            parm= nList.get(1).toString().split(",");
            strLevel[1] = parm[0];
            item[1]= Float.parseFloat(parm[1]);
            parm= nList.get(2).toString().split(",");
            strLevel[2] = parm[0];
            item[2]= Float.parseFloat(parm[1]);
            parm= nList.get(3).toString().split(",");
            strLevel[3] = parm[0];
            item[3]= Float.parseFloat(parm[1]);
            parm= nList.get(4).toString().split(",");
            strLevel[4] = parm[0];
            item[4]= Float.parseFloat(parm[1]);
            parm= nList.get(5).toString().split(",");
            strLevel[5] = parm[0];
            item[5]= Float.parseFloat(parm[1]);
            values.add(item);
            item= new double[6];
        }

        int[] colors = new int[] { Color.CYAN, Color.MAGENTA, Color.GREEN };
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        setChartSettings(renderer, "구간 별 평균 대지/건축/연 면적(㎡)", "대지 면적 구간", "평균 면적(㎡)",
                0.5, 6.5,
                0, 250,
                Color.GRAY, Color.LTGRAY
        );
        renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
        renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
        renderer.getSeriesRendererAt(2).setDisplayChartValues(true);
        renderer.setXLabels(6);
        renderer.setYLabels(10);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(16);
        renderer.setLegendTextSize(16);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setPanEnabled(true, false);
        renderer.setZoomEnabled(false);
        renderer.setZoomRate(1.1f);
        renderer.setBarSpacing(0.5f);
        for(int i= 0; i< strLevel.length; i++) {
            renderer.addXTextLabel(i+ 1, strLevel[i]);
        }
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
            seriesRenderer.setDisplayChartValues(true);
        }


        return ChartFactory.getBarChartView(
                context,
                buildBarDataset(titles, values),
                renderer,
                BarChart.Type.DEFAULT);
    }

}
