package com.seoul.hanokmania.database;

import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.events.DbUpdateEvent;
import com.seoul.hanokmania.provider.HanokUrlHelper;
import com.seoul.hanokmania.query.QueryContract;

import de.greenrobot.event.EventBus;

/**
 * Created by namudak on 2015-09-14.
 */
public class ManageDbFragment extends DialogFragment {
    private static String TAG = ManageDbFragment.class.getSimpleName();

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private static HanokUrlHelper mUrlHelper;

    private static final String UPDATEDB = "updatedb";
    private static final String MAKEDB = "makedb";

    public ManageDbFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manage_db, container, false);
        setCancelable(false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBarTextView = (TextView) view.findViewById(R.id.progressbar_text_view);

        mUrlHelper = HanokUrlHelper.getInstance(getActivity());
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.BLACK));

        // Check if new data at url site, get it and insert into db
        new RetrieveUrlTask().execute(MAKEDB);

        // Do update on menu command.
        // if new data at url site, get it and insert into db
        //new RetrieveUrlTask().execute(UPDATEDB);

        return view;
    }

    /**
     * AsyncTask for retrieving from url and insert into db apk /databases/ folder
     * if no db then create db
     */
    private class RetrieveUrlTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("서울시 한옥 자료 갱신 중 입니다. 잠시 기다려 주십시오.");

        }

        @Override
        protected Void doInBackground(String... params) {//1st parameter


            HanokUrl hanokUrl = new HanokUrl(getActivity(), mUrlHelper);

            if (params[0].equals(UPDATEDB)) {
                hanokUrl.UpdateHanokData();
            } else {
                //Delete first tables to add api url data
                SQLiteDatabase db = mUrlHelper.getReadableDatabase();

                db.rawQuery(
                        QueryContract.mQuery[QueryContract.QUERYDELETE1],
                        null
                ).moveToFirst();

                db.rawQuery(
                        QueryContract.mQuery[QueryContract.QUERYDELETE2],
                        null
                ).moveToFirst();

                db.rawQuery(
                        QueryContract.mQuery[QueryContract.QUERYDELETE3],
                        null
                ).moveToFirst();

                hanokUrl.MakeHanokData();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {//2nd parameter

        }

        @Override
        protected void onPostExecute(Void result) {//3rd parameter
            super.onPostExecute(result);

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");
            dismiss();
            EventBus.getDefault().post(new DbUpdateEvent());
        }
    }


}
