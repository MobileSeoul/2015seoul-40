package com.seoul.hanokmania.query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.provider.HanokOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
class HanokTextTask extends AsyncTask<Void, Void, List> {

    private static final String TAG = HanokTextTask.class.getSimpleName();

    private Context mContext;

    private String[] GROUPFORMAT= {
            "한옥 대지 면적(㎡) 30  이하 [%s]",
            "한옥 대지 면적(㎡) 60  이하 [%s]",
            "한옥 대지 면적(㎡) 90  이하 [%s]",
            "한옥 대지 면적(㎡) 120 이하 [%s]",
            "한옥 대지 면적(㎡) 240 이하 [%s]",
            "한옥 대지 면적(㎡) 240 이상 [%s]"
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

    ArrayList<String> groupItem = new ArrayList<>();
    ArrayList<Object> childItem = new ArrayList<>();

    // Number of hanok along plottage
    private int[] mCountNum= new int[GROUPFORMAT.length+ 1];

    public HanokTextTask(Context context){
        mContext= context;
    }

    @Override
    protected void onPreExecute() {

//            mProgressBar.setVisibility(View.VISIBLE);
//            mProgressBarTextView.setText("Retrieving data...Please wait.");
    }

    @Override
    protected List doInBackground(Void... params) { // 첫번째 인자

        try {

            HanokOpenHelper dbHelper= new HanokOpenHelper(mContext);

            SQLiteDatabase db= dbHelper.getReadableDatabase();

            Cursor cursor= db.rawQuery(
                    QueryContract.mQuery[QueryContract.QUERYPLOTTAGE],
                    null
            );

            ArrayList<String> tempChild = new ArrayList<>();
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
                tempChild.add(
                        String.format(CHILDFORMAT[0], val[0])+ ","+
                        String.format(CHILDFORMAT[1], val[2])+ ","+
                        String.format(CHILDFORMAT[2], val[3])+ ","+
                        String.format(CHILDFORMAT[3], val[4])+ ","+
                        String.format(CHILDFORMAT[4], String.valueOf(floorareaRatio))+ ","+
                        String.format(CHILDFORMAT[5], String.valueOf(coverageRatio))+ ","+
                        String.format(CHILDFORMAT[6], val[5])+ ","+
                        String.format(CHILDFORMAT[7], val[6])+ ","+
                        String.format(CHILDFORMAT[8], val[1])
                );
            }

            // Set groupItem as formated
            for(int i= 0; i< GROUPFORMAT.length; i++) {
                groupItem.add(String .format(GROUPFORMAT[i],
                        String.valueOf(mCountNum[i+ 1])
                ));
            }
            // Sum of mCountNum array
//                int sum=0;
//                for(int num : mCountNum)
//                    sum+= num;
            // Set childItem on mCountNum
            // Todo optimized later by multi for loop
            int from, to;
            ArrayList<String> child = new ArrayList<>();
            for (int j = 0; j < mCountNum[1]; j++) {
                child.add(tempChild.get(j));
            }
            childItem.add(child);
            from= mCountNum[1]; to= from+ mCountNum[2];
            child = new ArrayList<>();
            for (int j = from; j < to; j++) {
                child.add(tempChild.get(j));
            }
            childItem.add(child);
            from= to; to= from+ mCountNum[3];
            child = new ArrayList<>();
            for (int j = from; j < to; j++) {
                child.add(tempChild.get(j));
            }
            childItem.add(child);
            from= to; to= from+ mCountNum[4];
            child = new ArrayList<>();
            for (int j = from; j < to; j++) {
                child.add(tempChild.get(j));
            }
            childItem.add(child);
            from= to; to= from+ mCountNum[5];
            child = new ArrayList<>();
            for (int j = from; j < to; j++) {
                child.add(tempChild.get(j));
            }
            childItem.add(child);
            from= to; to= from+ mCountNum[6];
            child = new ArrayList<>();
            for (int j = from; j < to; j++) {
                child.add(tempChild.get(j));
            }
            childItem.add(child);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        List<Object> list= new ArrayList<>();
        list.add(groupItem);
        list.add(childItem);

        return list;
    }

    // publishUpdate로만 호출
    @Override
    protected void onProgressUpdate(Void... values) { // 두번째 인자
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List list) { // 세번째 인자

//            mProgressBar.setVisibility(View.GONE);
//            mProgressBarTextView.setText("");

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
        }else if(plottage< 120.0) {
            mCountNum[4]+= 1;
        }else if(plottage< 240.0) {
            mCountNum[5]+= 1;
        }else {
            mCountNum[6]+= 1;
        }

    }
}

