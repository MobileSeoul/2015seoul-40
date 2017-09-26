package com.seoul.hanokmania.fragments;

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

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.models.Weather;
import com.seoul.hanokmania.views.adapters.WeatherAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by junsuk on 2015. 10. 15..
 */
public class WeatherFragment extends Fragment implements View.OnKeyListener {

    private static final String TAG = WeatherFragment.class.getSimpleName();

    // 날씨 예보 제공 URL
    private static final String URL_FORECAST = "http://api.openweathermap.org/data/2.5/forecast?q=London,us&mode=json&appid=bd82977b86bf27fb59a04b61b657fb6f";

    private ListView mWeatherListView;
    private WeatherAdapter mAdapter;

    private ProgressBar mProgressBar;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    // 클라이언트 오브젝트
    private OkHttpClient client = new OkHttpClient();

    /**
     * url 로 부터 스트림을 읽어 String 으로 반환한다
     * @param url
     * @return
     * @throws IOException
     */
    private String getResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        mWeatherListView = (ListView) view.findViewById(R.id.lv_weather);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);

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

        new WeatherInfoLoadTask().execute();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    class WeatherInfoLoadTask extends AsyncTask<Void, Void, List> {
        @Override
        protected void onPreExecute() {

            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List doInBackground(Void... params) { // 첫번째 인자
            List<Weather> weatherList = null;


            try {
                // HTTP 에서 내용을 String 으로 받아 온다
                String jsonString = getResponse(URL_FORECAST);
                Log.d(TAG, jsonString);
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("list");


                ObjectMapper objectMapper = new ObjectMapper();

                weatherList = objectMapper.readValue(jsonArray.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(
                                List.class, Weather.class
                        ));

                Log.d(TAG, weatherList.toString());

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            return weatherList;
        }

        // publishUpdate로만 호출
        @Override
        protected void onProgressUpdate(Void... values) { // 두번째 인자
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List list) { // 세번째 인자
            mAdapter = new WeatherAdapter(getActivity(), list);
            mWeatherListView.setAdapter(mAdapter);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
