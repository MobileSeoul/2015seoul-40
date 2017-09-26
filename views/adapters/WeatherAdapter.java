
package com.seoul.hanokmania.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.models.Weather;

import java.util.List;

/**
 * Created by junsuk on 15. 9. 8.. 날씨 표시용 어댑터
 */
public class WeatherAdapter extends BaseAdapter {

    private static final String TAG = WeatherAdapter.class.getSimpleName();
    private List<Weather> mList;
    private Context mContext;

    public WeatherAdapter(Context context, List<Weather> data) {
        mContext = context;
        mList = data;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 아이템이 화면에 보일 때 호출 됨
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Layout 을 완성하고 ViewHolder와 연결
        // 이유 : findViewById를 한번만 하려고
        if (convertView == null) {
            holder = new ViewHolder();

            // 처음 로드
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_item_weather, parent,
                    false);

            holder.timeTextView = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tempTextView = (TextView) convertView.findViewById(R.id.tv_temp);
            holder.descTextView = (TextView) convertView.findViewById(R.id.tv_desc);

            convertView.setTag(holder);
        } else {
            // 재사용
            holder = (ViewHolder) convertView.getTag();
        }

        // Data 를 Layout 에 설정
        Weather weather = mList.get(position);

        holder.timeTextView.setText(weather.time);
        holder.tempTextView.setText(weather.temp + "℃");
        holder.descTextView.setText(weather.description);

        return convertView;
    }

    static class ViewHolder {
        TextView timeTextView;
        TextView tempTextView;
        TextView descTextView;
    }
}
