package com.seoul.hanokmania.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.seoul.hanokmania.models.HanokItem;
import com.seoul.hanokmania.views.HanokItemView;

import java.util.List;

/**
 * Created by Administrator on 2015-10-17.
 */
public class HanokListAdapter extends BaseAdapter{

    private final Context mContext;

    private List<HanokItem> mHanokItem;

    public HanokListAdapter(Context context, List<HanokItem> data) {
        this.mContext= context;
        this.mHanokItem = data;
    }
    @Override
    public int getCount() { return mHanokItem.size(); }
    @Override
    public Object getItem (int position) { return mHanokItem.get(position); }
    @Override
    public long getItemId(int position) { return position; }
    /**
     * Item's layout
     * @param position
     * @param convertView
     * @param parent
     * @return View
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        String ADDRFORMAT= "%s";
        String AREAFORMAT= "%s(㎡)";

        HanokItemView itemView;

        HanokItem aItem= mHanokItem.get(position);

        if(convertView== null) {
            itemView= new HanokItemView(mContext, aItem);

        } else {
            itemView= (HanokItemView)convertView;
        }

        itemView.setAddr(String.format(ADDRFORMAT, aItem.getADDR()));
        itemView.setPlottage(String.format("대지 면적 : " + AREAFORMAT, aItem.getPLOTTAGE()));
        itemView.setBuildArea(String.format("건축 면적 : " + AREAFORMAT, aItem.getBUILDAREA()));

        // Return view
        return itemView;
    }
}
