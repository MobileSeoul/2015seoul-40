package com.seoul.hanokmania.views.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul.hanokmania.R;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class HanokTextAdapter extends BaseExpandableListAdapter {

	public ArrayList<String> mGroupItem;
	public ArrayList<String> mTempChild;
	public ArrayList<Object> mChildItem = new ArrayList<>();
	public LayoutInflater mInflater;
	public Activity mActivity;

	public HanokTextAdapter(ArrayList<String> grList, ArrayList<Object> childItem) {
		this.mGroupItem = grList;
		this.mChildItem = childItem;
	}

	public void setInflater(LayoutInflater inflater, Activity act) {
		mInflater = inflater;
		mActivity = act;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ViewHolder holder;

		mTempChild = (ArrayList<String>) mChildItem.get(groupPosition);

		ImageView imageView= null;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.text_child_row, null);

			holder = new ViewHolder();
			holder.childImage= (ImageView) convertView.findViewById(R.id.childImage);
			holder.num = (TextView) convertView.findViewById(R.id.tv_hanoknum);
			holder.plot = (TextView) convertView.findViewById(R.id.tv_plottage);
			holder.barea = (TextView) convertView.findViewById(R.id.tv_buildarea);
			holder.totar = (TextView) convertView.findViewById(R.id.tv_totar);
			holder.fratio = (TextView) convertView.findViewById(R.id.tv_floorratio);
			holder.cratio = (TextView) convertView.findViewById(R.id.tv_coverageratio);
			holder.use = (TextView) convertView.findViewById(R.id.tv_use);
			holder.stru = (TextView) convertView.findViewById(R.id.tv_structure);
			holder.addr = (TextView) convertView.findViewById(R.id.tv_addr);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder)convertView.getTag();
		}


		String[] parm= new String[mTempChild.get(childPosition).length()];
		parm= mTempChild.get(childPosition).split(",");

		holder.childImage.setImageResource(R.drawable.fewcloudscircleday);
		holder.num.setText(parm[0]);
		holder.plot.setText(parm[1]);
		holder.barea.setText(parm[2]);
		holder.totar.setText(parm[3]);
		holder.fratio.setText(parm[4]);
		holder.cratio.setText(parm[5]);
		holder.use.setText(parm[6]);
		holder.stru.setText(parm[7]);
		holder.addr.setText(parm[8]);

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(mActivity, mTempChild.get(childPosition),
						Toast.LENGTH_SHORT).show();
			}
		});

		return convertView;
	}

	private static class ViewHolder {
		public ImageView childImage;
		public TextView num;
		public TextView plot;
		public TextView barea;
		public TextView totar;
		public TextView fratio;
		public TextView cratio;
		public TextView use;
		public TextView stru;
		public TextView addr;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return ((ArrayList<String>) mChildItem.get(groupPosition)).size();
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
			convertView = mInflater.inflate(R.layout.text_group_row, null);
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
