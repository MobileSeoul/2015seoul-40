package com.seoul.hanokmania.query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.seoul.hanokmania.activities.graph.HanokBarChart;
import com.seoul.hanokmania.activities.graph.HanokDoughnutChart;
import com.seoul.hanokmania.activities.graph.HanokLineChart;
import com.seoul.hanokmania.activities.graph.HanokMultiBarChart;
import com.seoul.hanokmania.activities.graph.HanokPieChart;
import com.seoul.hanokmania.activities.graph.HanokStackedBarChart;
import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.provider.HanokOpenHelper;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
class HanokGraphTask extends AsyncTask<Void, GraphicalView, List> {

    private static final String TAG = HanokGraphTask.class.getSimpleName();

    private Context mContext;

    private static HanokOpenHelper mDbHelper;

    private String[] GROUPFORMAT= {
            "⑴ 한옥 건립일 현황 도표",
            "⑵ 북촌 한옥 현황 도표",
            "⑶ 한옥 문화재 현황 도표",
            "⑷ 한옥 수선 현황 도표",
            "⑸ 대지 구간 별 한옥 현황 도표",
            "⑹ 대지 구간 별 평균 대지면적,\n"+
            "건축면적, 연면적 현황 도표",
            "⑺ 대지 구간 별 평균 대지면적,\n" +
            "건축면적, 건폐율 현황 도표",
            "⑻ 대지 구간 별 평균 대지면적,\n" +
            "연면적, 용적율 현황 도표",
            "⑼ 용도 별 북촌 한옥 현황 도표",
            "⑽ 구조 별 북촌 한옥 현황 도표"

    };
    private String CHILDFORMAT[]= {
            "등록 번호 : %s",
            "대지 면적(㎡) : %s",
            "건축 면적(㎡) : %s",
            "연 면적(㎡) : %s",
            "용적율(㎡) : %s",
            "건폐율(％) : %s",
            "용도 : %s",
            "구조 : %s",
            "법정동 : %s"
    };

    List<String> groupItem = new ArrayList<>();
    List<Object> childItem = new ArrayList<>();

    // Number of hanok along plottage
    private String[] mLevel= {"0~30(㎡)", "30~60(㎡)", "60~90(㎡)",
                    "90`120(㎡)", "120~240(㎡)", "240(㎡)~"};
    private int[] mCountNum= new int[mLevel.length+ 1];

    private int mHanokTotal= 0;
    private int mBukchonHanokTotal= 0;

    public HanokGraphTask(Context context){
        mContext= context;

        mDbHelper= HanokOpenHelper.getInstance(mContext);
    }

    @Override
    protected void onPreExecute() {

        try {

            List<String> childList= getPlottageQuery();

            // Set groupItem as formated
            for(int i= 0; i< GROUPFORMAT.length; i++) {
                groupItem.add(String.format(GROUPFORMAT[i]));
            }
            // Sum of hanok in seoul
            mHanokTotal= getTotalHanokQuery();
            // Sum of hanok in seoul
            mBukchonHanokTotal = getTotalBukchonHanokQuery();

            for(int i= 0; i< GROUPFORMAT.length; i++) {
                List<Object> graph = new ArrayList<>();
                switch (i) {
                    case 0://한옥 건립일@Pie
                        graph.add(getBuildQuery());
                        graph.add("※ 건립일 미상 564 가옥(94%) 제외");
                        break;
                    case 1://등록 한옥 중 북촌한옥 비율(단위:％)@Pie
                        graph.add(getHousetypeQuery());
                        graph.add(String.format("※ 서울시 등록 한옥 숫자 : %s\n",mHanokTotal)+
                                String.format("북촌 한옥 수 : %s", mBukchonHanokTotal));
                        break;
                    case 2://등록 한옥 중 문화재 비율(단위:％)@Pie
                        graph.add(getBoolcultureQuery());
                        graph.add(String.format("※ 서울시 등록 한옥 중 북촌 한옥 숫자 : %s",
                                mBukchonHanokTotal));
                        break;
                    case 3://년도 별 한옥 수선 건수@Line
                        HanokLineChart chart3= new HanokLineChart();
                        graph.add(chart3.getGraphView(mContext, getSnQuery()));
                        graph.add("※ 2001년 ~ 2015년 까지의 한옥 수선 현황");
                        break;
                    case 4://대지 면적 구간 한옥 숫자@Bar
                        HanokBarChart chart4= new HanokBarChart();
                        graph.add(chart4.getGraphView(mContext, getCountHanok()));
                        graph.add("※ -");
                        break;
                    case 5://대지 면적 구간 평균 대지/건축/연면적(단위:㎡)@Bar
                        List list5= new ArrayList();
                        list5.add(getAvg(3, childList));
                        list5.add(getAvg(2, childList));//too many 0
                        list5.add(getAvg(1, childList));
                        HanokMultiBarChart chart5= new HanokMultiBarChart();
                        graph.add(chart5.getGraphView(mContext, list5));
                        graph.add("※ -");
                    case 6://대지 면적 구간 평균 건축면적/건폐율(단위:㎡)@Stacked Bar
                        List list6= new ArrayList();
                        list6.add("건폐율(%), 건축 면적(㎡), 대지 면적(㎡)");
                        list6.add("한옥 대지/건축 면적 및 건폐율, 대지 면적, 한옥 수");
                        list6.add(getAvg(4, childList));
                        list6.add(getAvg(2, childList));//too many 0
                        list6.add(getAvg(1, childList));
                        HanokStackedBarChart chart6= new HanokStackedBarChart();
                        graph.add(chart6.getGraphView(mContext, list6));
                        graph.add("※ 건폐율 = 건축 면적 / 대지 면적");
                        break;
                    case 7://대지 면적 구간 평균 연면적/용적율(단위:％)@Stacked Bar
                        List list7= new ArrayList();
                        list7.add("용적율(%), 연 면적(㎡), 대지 면적(㎡)");
                        list7.add("한옥 대지/건축 면적 및 용적율, 대지 면적, 한옥 수");
                        list7.add(getAvg(5, childList));
                        list7.add(getAvg(3, childList));//too many 0
                        list7.add(getAvg(1, childList));
                        HanokStackedBarChart chart7= new HanokStackedBarChart();
                        graph.add(chart7.getGraphView(mContext, list7));
                        graph.add("※ 용적율 = 연 면적 / 대지 면적");
                        break;
                    case 8://등록 한옥 용도 별 현황@Pie
                        HanokPieChart chart8= new HanokPieChart();
                        graph.add(chart8.getGraphView(mContext, getUseHanok()));
                        graph.add("※ -");
                        break;
                    case 9://등록 한옥 구조 별 현황@Pie
                        HanokPieChart chart9= new HanokPieChart();
                        graph.add(chart9.getGraphView(mContext, getStructureHanok()));
                        graph.add("※ -");
                        break;
                }
                childItem.add(graph);

            }


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected List doInBackground(Void... params) { // 첫번째 인자

        List<Object> list= new ArrayList<>();
        list.add(groupItem);
        list.add(childItem);

        return list;
    }

    // publishUpdate로만 호출
    @Override
    protected void onProgressUpdate(GraphicalView... values) { // 두번째 인자

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List list) { // 세번째 인자

        groupItem= new ArrayList<>();
        childItem= new ArrayList<>();

    }

    /**
     * get Db List for 'hanok' on plottage
     */
    private List getPlottageQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYREALPLOTTAGE],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        String[] val= new String[7];
        while(cursor.moveToNext()) {
            val[0]= cursor.getString(cursor.getColumnIndexOrThrow(
                    HanokContract.HanokCol.HANOKNUM));
            val[1]= cursor.getString(cursor.getColumnIndexOrThrow(
                    HanokContract.HanokCol.ADDR));
            val[2]= cursor.getString(cursor.getColumnIndexOrThrow(
                    HanokContract.HanokCol.PLOTTAGE));
            val[3]= cursor.getString(cursor.getColumnIndexOrThrow(
                    HanokContract.HanokCol.BUILDAREA));
            val[4]= cursor.getString(cursor.getColumnIndexOrThrow(
                    HanokContract.HanokCol.TOTAR));
            val[5]= cursor.getString(cursor.getColumnIndexOrThrow(
                    HanokContract.HanokCol.USE));
            val[6]= cursor.getString(cursor.getColumnIndexOrThrow(
                    HanokContract.HanokCol.STRUCTURE));

            // Store count number to array for each level
            putCountArray(Float.parseFloat(val[2]));
            Float coverageRatio= 100.0f* Float.parseFloat(val[4])/
                    Float.parseFloat(val[2]);
            Float floorareaRatio= 100.0f* Float.parseFloat(val[3])/
                    Float.parseFloat(val[2]);
            childList.add(
                    val[0]+ ","+
                    val[2]+ ","+
                    val[3]+ ","+
                    val[4]+ ","+
                    String.valueOf(floorareaRatio)+ ","+
                    String.valueOf(coverageRatio)+ ","+
                    val[5]+ ","+
                    val[6]+ ","+
                    val[1]
            );
        }

        cursor.close();

        return  childList;
    }

    /**
     * store plottage count to array
     */
    private void putCountArray(Float plottage) {

        if(plottage< 30.0 ) {
            mCountNum[1]+= 1;
        } else if(plottage< 60.0) {
            mCountNum[2]+= 1;
        } else if(plottage< 90.0) {
            mCountNum[3] += 1;
        } else if(plottage< 120.0) {
            mCountNum[4]+= 1;
        } else if(plottage< 240.0) {
            mCountNum[5]+= 1;
        } else {
            mCountNum[6]+= 1;
        }

    }

    /**
     * get total number of enrolled hanok
     *
     */
    private int getTotalHanokQuery() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                QueryContract.mQuery[
                        QueryContract.QUERYHANOK],
                null
        );

        int totalHanok= 0;
        while (cursor.moveToNext()) {
            totalHanok= cursor.getInt(0);//count
        }

        cursor.close();

        return totalHanok;
    }

    /**
     * get total number of enrolled hanok
     *
     */
    private int getTotalBukchonHanokQuery() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                QueryContract.mQuery[
                        QueryContract.QUERYBUKCHONHANOK],
                null
        );

        int totalBukchonHanok= 0;
        while (cursor.moveToNext()) {
            totalBukchonHanok= cursor.getInt(0);//count
        }

        cursor.close();

        return totalBukchonHanok;
    }

    /**
     * get Db List for 'hanok' on builddate
     */
    private GraphicalView getBuildQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        String strQuery= String.format(QueryContract.mQuery[
                QueryContract.QUERYREALBUILDDATE], "<");

        Cursor cursor= db.rawQuery(
                strQuery,
                new String[] {"2000"}
        );

        ArrayList<String> childList = new ArrayList<>();
        String[] val= new String[2];
        while(cursor.moveToNext()) {
            val[0]= "1900년대";
            val[1]= String.valueOf(cursor.getInt(0));//count

            childList.add(
                    val[0]+ ","+ val[1]
            );
        }

        cursor.close();

        strQuery= String.format(QueryContract.mQuery[
                QueryContract.QUERYREALBUILDDATE], ">");

        cursor= db.rawQuery(
                strQuery,
                new String[] { "2000"}
        );

        while(cursor.moveToNext()) {
            val[0]= "2000년대";
            val[1]= String.valueOf(cursor.getInt(0));//count

            childList.add(
                    val[0]+ ","+ val[1]
            );
        }

        cursor.close();

        strQuery= String.format(QueryContract.mQuery[
                QueryContract.QUERYREALBUILDDATE], "=");

        cursor= db.rawQuery(
                strQuery,
                new String[] {"0"}
        );

        while(cursor.moveToNext()) {
            val[0]= "미상";
            val[1]= String.valueOf(cursor.getInt(0));//count

            childList.add(
                    val[0]+ ","+ val[1]
            );
        }

        cursor.close();

        // Prepare data for pie chart
        int[] colors = new int[]{Color.YELLOW, Color.CYAN};
        int[] values = new int[2];
        String[] name_list = new String[2];
        for(int i= 0; i< childList.size()- 1; i++) {
            String[] str= childList.get(i).split(",");
            name_list[i]= str[0];
            values[i]= Integer.parseInt(str[1]);
        }
        HanokPieChart chart= new HanokPieChart(colors, values, name_list);

        return  chart.getGraphView(mContext);
    }

    /**
     * get Db List for 'hanok' on house_type
     */
    private GraphicalView getHousetypeQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYHOUSETYPE],
                null
        );

        int bukchonNum= 0;
        while(cursor.moveToNext()) {
            bukchonNum= cursor.getInt(0);
        }
        cursor.close();

        // Prepare data for doughnutpie chart
        int[] colors = new int[]{Color.YELLOW, Color.CYAN};
        double[] values =  new double[]{(double)bukchonNum, (double)mHanokTotal};
        String[] name_list = new String[]{"북촌 한옥", "기타 한옥"};

        HanokDoughnutChart chart= new HanokDoughnutChart(
                colors, values, name_list, "서울시 등록한옥 중 북촌한옥");

        return  chart.getGraphView(mContext);
    }

    /**
     * get Db List for 'hanok_hanok_bukchon' on bool_culture
     */
    private GraphicalView getBoolcultureQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYBOOLCULTURE],
                null
        );

        int cultureNum= 0;
        while(cursor.moveToNext()) {
            cultureNum= cursor.getInt(0);
        }
        cursor.close();

        // Prepare data for doughnutpie chart
        int[] colors = new int[]{Color.YELLOW, Color.CYAN};
        double[] values =  new double[]{(double)cultureNum, (double)mBukchonHanokTotal};
        String[] name_list = new String[]{"한옥 문화재", "기타 북촌 한옥"};

        HanokDoughnutChart chart= new HanokDoughnutChart(
                colors, values, name_list, "북촌 한옥 중 문화재 지정 비율");

        return  chart.getGraphView(mContext);
    }

    /**
     * get Db List for 'repair_hanok' on sn
     */
    private List getSnQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYSN],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        String val[]= new String[2];
        while(cursor.moveToNext()) {
            val[0]= cursor.getString(0);// yyyy
            val[1]= String.valueOf(cursor.getInt(1));//count

            childList.add(
                    val[0]+ ","+ val[1]
            );
        }
        cursor.close();

        return  childList;
    }

    /**
     * get hanok count on level
     *
     */
    private List getCountHanok() {

        ArrayList<String> childList = new ArrayList<>();

        String val[]= new String[2];
        for(int i= 0; i< mCountNum.length- 1; i++){
            val[0]= mLevel[i];
            val[1]= String.valueOf(mCountNum[i+ 1]);

            childList.add(
                    val[0]+ ","+ val[1]
            );
        }

        return childList;

    }
    /**
     * get hanok posttage, buildarea, floorarea, coverageratio, floorratio
     *
     */
    private List getAvg(int num, List<String> list) {

        String[] parm = new String[9];
        // 대지 1, 건축 2, 연면적 3, 용적율 4, 건폐율 5
        float val= 0.0f;
        float avg= 0.0f;

        int from, to;
        ArrayList<String> childList = new ArrayList<>();
        for (int j = 0; j < mCountNum[1]; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[1];

        childList.add(mLevel[0]+ ","+ String.format("%d", (int)avg));

        from = mCountNum[1];
        to = from + mCountNum[2];
        val= 0.0f;
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[2];

        childList.add(mLevel[1]+ ","+ String.format("%d", (int)avg));

        from= to; to= from+ mCountNum[3];
        val= 0.0f;
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[3];

        childList.add(mLevel[2]+ ","+ String.format("%d", (int)avg));

        from= to; to= from+ mCountNum[4];
        val= 0.0f;
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[4];

        childList.add(mLevel[3]+ ","+ String.format("%d", (int)avg));

        from= to; to= from+ mCountNum[5];
        val= 0.0f;
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[5];

        childList.add(mLevel[4]+ ","+ String.format("%d", (int)avg));

        from= to; to= from+ mCountNum[6];
        val= 0.0f;
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[6];

        childList.add(mLevel[5]+ ","+ String.format("%d", (int)avg));

        return childList;

    }

    /**
     * get hanok count on use
     *
     */
    private List getUseHanok() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYUSE],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        String val[]= new String[2];
        while(cursor.moveToNext()) {
            val[0]= cursor.getString(0);// use
            if(val[0].length()== 0) {
                val[0]= "기타";
            }
            val[1]= String.valueOf(cursor.getInt(1));//count

            childList.add(
                    val[0] + "," + val[1]
            );
        }
        cursor.close();

        return  childList;

    }

    /**
     * get hanok count on structure
     *
     */
    private List getStructureHanok() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYSTRUCTURE],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        String val[]= new String[2];
        while(cursor.moveToNext()) {
            val[0]= cursor.getString(0);// structure
            if(val[0].length()== 0) {
                val[0]= "기타";
            } else {
                val[0]= val[0].replace(",", "/");
            }
            val[1]= String.valueOf(cursor.getInt(1));//count

            childList.add(
                    val[0]+ ","+ val[1]
            );
        }
        cursor.close();

        return  childList;

    }

}