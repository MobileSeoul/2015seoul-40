package com.seoul.hanokmania.views.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.events.ChartClickEvent;
import com.seoul.hanokmania.query.QueryContract;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

@SuppressWarnings("unchecked")
public class HanokGraphAdapter extends BaseExpandableListAdapter {

    public List<String> mGroupItem = new ArrayList<>();;
    public List<Object> mTempChild= new ArrayList<>();
    public List<Object> mChildItem = new ArrayList<>();
    public LayoutInflater mInflater;
    public Activity mActivity;

    public HanokGraphAdapter(List<String> grList, List<Object> childItem) {
        mGroupItem = grList;
        mChildItem = childItem;
    }

    public void setInflater(LayoutInflater inflater, Activity act) {
        mInflater = inflater;
        mActivity = act;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {return null; }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.graph_child_row, null);
        }

        mTempChild = (ArrayList<Object>)mChildItem.get(groupPosition);

        final ImageView itemView= (ImageView) convertView.findViewById(R.id.childImage);
        TextView text= (TextView) convertView.findViewById(R.id.tv_child);;

        final GraphicalView graphView= (GraphicalView)mTempChild.get(0);

        Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        graphView.draw(canvas);

        itemView.setImageBitmap(bitmap);
        itemView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        text.setText(mTempChild.get(1).toString());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartClickEvent event = new ChartClickEvent();
                event.chartView = itemView;

                //Put the chartview on static field@QueryContract
                QueryContract.mChartView= graphView;

                EventBus.getDefault().post(event);
            }

        });

        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return mGroupItem.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.graph_group_row, null);
        }
        TextView text= (TextView) convertView.findViewById(R.id.textView1);
        text.setText(mGroupItem.get(groupPosition));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
