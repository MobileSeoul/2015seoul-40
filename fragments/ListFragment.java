package com.seoul.hanokmania.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.models.HanokItem;
import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.views.adapters.HanokListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-17.
 */

/**
 * Created by namudak on 2015. 10. 17.
 */
public class ListFragment extends Fragment implements View.OnKeyListener {

    private static final String TAG = ListFragment.class.getSimpleName();

    private List<HanokItem> mHanokList = null;
    
    private ListView mhanokListView;
    private HanokListAdapter mAdapter;

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hanok, container, false);
        mhanokListView = (ListView) view.findViewById(R.id.lv_hanok);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBarTextView= (TextView) view.findViewById(R.id.progressbar_text_view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: Pull to refresh 동작시 처리 구현

                // refresh 애니메이션 종료
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHanokList= new ArrayList<HanokItem>();

        new HanokTask().execute();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    class HanokTask extends AsyncTask<Void, Void, List> {
        @Override
        protected void onPreExecute() {

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");
        }

        @Override
        protected List doInBackground(Void... params) { // 첫번째 인자

            try {
                HanokContract.setHanokContract("hanok");
                Uri uri = HanokContract.CONTENT_URI;
                String[] projection= new String[] {
                        HanokContract.HanokCol.ADDR,
                        HanokContract.HanokCol.PLOTTAGE,
                        HanokContract.HanokCol.BUILDAREA
                };

                Cursor cursor= getContext().getContentResolver().query(
                        uri,
                        projection,
                        null,
                        null,
                        null
                );

                String[] val= new String[6];
                while(cursor.moveToNext()) {
                    val[1]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.ADDR));
                    val[2]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.PLOTTAGE));
                    val[3]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.BUILDAREA));
                    val[0]= "";val[4]= ""; val[5]= "";
                    mHanokList.add(new HanokItem(val));
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            return mHanokList;
        }

        // publishUpdate로만 호출
        @Override
        protected void onProgressUpdate(Void... values) { // 두번째 인자
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List list) { // 세번째 인자
            
            mAdapter = new HanokListAdapter(getActivity(), list);
            mhanokListView.setAdapter(mAdapter);

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");
            
        }
    }
}
