package com.seoul.hanokmania.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.models.HanokItem;

/**
 * Created by namudak on 2015-09-14.
 */
public class HanokItemView extends LinearLayout {

    private TextView mAddrTextView;
    private TextView mPlottageTextView;
    private TextView mBuildAreaTextView;

    public HanokItemView(Context context) {
        super(context);
    }

    public HanokItemView(Context context, HanokItem aItem) {
        super(context);

        LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.fragment_item_hanok, this, true);

        mAddrTextView = (TextView)findViewById(R.id.tv_addr);
        mAddrTextView.setText(aItem.getADDR());
        mPlottageTextView = (TextView)findViewById(R.id.tv_plottage);
        mPlottageTextView.setText(aItem.getPLOTTAGE());
        mBuildAreaTextView = (TextView)findViewById(R.id.tv_buildarea);
        mBuildAreaTextView.setText(aItem.getBUILDAREA());
    }

    // Set Addr textview as customed
    public void setAddr(String addr) {
        mAddrTextView.setText(addr);}
    // Set Plottage textview as customed
    public void setPlottage(String plottage) {
        mPlottageTextView.setText(plottage);}
    // set Buildarea textview as customed
    public void setBuildArea(String buildarea) {
        mBuildAreaTextView.setText(buildarea);}

}