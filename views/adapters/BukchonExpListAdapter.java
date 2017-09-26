package com.seoul.hanokmania.views.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.models.BukchonItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ray Choe on 2015-10-20.
 * 북촌한옥마을 어댑터
 */
public class BukchonExpListAdapter extends BaseExpandableListAdapter {

    private static final String TAG = BukchonExpListAdapter.class.getSimpleName();
    private List mGroupList;
    private List<List<BukchonItem>> mChildList;
    private LayoutInflater mInflater;
    private Context mContext;
    private ViewHolder mViewHolder;

    public BukchonExpListAdapter(Context context, List mGroupList, List<List<BukchonItem>> mChildList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mGroupList = mGroupList;
        this.mChildList = mChildList;
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.group_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.groupName = (TextView) convertView.findViewById(R.id.group_name_tv);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        String groupName = getGroup(groupPosition).toString();
        viewHolder.groupName.setText(groupName);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList.get(groupPosition).size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.child_layout, parent, false);

            mViewHolder = new ViewHolder();
            mViewHolder.itemAddress = (TextView) convertView.findViewById(R.id.item_addr_tv);
            mViewHolder.itemHomePage = (TextView) convertView.findViewById(R.id.item_homepage_tv);
            mViewHolder.itemName = (TextView) convertView.findViewById(R.id.item_name_tv);
            mViewHolder.itemOwner = (TextView) convertView.findViewById(R.id.item_owner_tv);
            mViewHolder.itemPhoneNum = (TextView) convertView.findViewById(R.id.item_phone_tv);
            mViewHolder.itemCultural = (TextView) convertView.findViewById(R.id.item_cultural_tv);
            mViewHolder.itemType = (TextView) convertView.findViewById(R.id.item_type_tv);
            mViewHolder.itemContent = (TextView) convertView.findViewById(R.id.item_content_tv);

            mViewHolder.itemImageViewLeft = (ImageView) convertView.findViewById(R.id.hanok_picture_left_iv);
            mViewHolder.itemImageViewCenter = (ImageView) convertView.findViewById(R.id.hanok_picture_center_iv);
            mViewHolder.itemImageViewRight = (ImageView) convertView.findViewById(R.id.hanok_picture_right_iv);

            convertView.setTag(mViewHolder);

        } else {

            mViewHolder = (ViewHolder) convertView.getTag();

        }

        BukchonItem bukchonHanok = (BukchonItem) getChild(groupPosition, childPosition);

        BukchonUtils.dynamicArrange(BukchonUtils.TYPE_NAME, bukchonHanok.getName(), mViewHolder.itemName);
        BukchonUtils.dynamicArrange(BukchonUtils.TYPE_ADDRESS, bukchonHanok.getAddress(), mViewHolder.itemAddress);
        BukchonUtils.dynamicArrange(BukchonUtils.TYPE_OWNER, bukchonHanok.getOwner(), mViewHolder.itemOwner);
        BukchonUtils.dynamicArrange(BukchonUtils.TYPE_PHONE_NUM, bukchonHanok.getPhoneNum(), mViewHolder.itemPhoneNum);
        BukchonUtils.dynamicArrange(BukchonUtils.TYPE_HOMEPAGE, bukchonHanok.getHomePage(), mViewHolder.itemHomePage);
        BukchonUtils.dynamicArrange(BukchonUtils.TYPE_CULTURAL, bukchonHanok.getCultural(), mViewHolder.itemCultural);
        BukchonUtils.dynamicArrange(BukchonUtils.TYPE_HOUSE_TYPE, bukchonHanok.getType(), mViewHolder.itemType);
        BukchonUtils.dynamicArrange(BukchonUtils.TYPE_CONTENT, bukchonHanok.getContent(), mViewHolder.itemContent);

        BukchonUtils.setImageFromResourceId(mContext, bukchonHanok.getHouse_id(),
                mViewHolder.itemImageViewLeft,
                mViewHolder.itemImageViewCenter,
                mViewHolder.itemImageViewRight);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class BukchonUtils {
        private static final int TYPE_NAME = 0;
        private static final int TYPE_ADDRESS = 1;
        private static final int TYPE_OWNER = 2;
        private static final int TYPE_PHONE_NUM = 3;
        private static final int TYPE_HOMEPAGE = 4;
        private static final int TYPE_HOUSE_TYPE = 5;
        private static final int TYPE_CONTENT = 6;
        private static final int TYPE_CULTURAL = 7;

        private static final Map<Integer, String> MAP_TYPE;
        static {
            MAP_TYPE = new HashMap<>();
            MAP_TYPE.put(TYPE_NAME, "명칭 : ");
            MAP_TYPE.put(TYPE_ADDRESS, "주소 : ");
            MAP_TYPE.put(TYPE_OWNER, "소유자 : ");
            MAP_TYPE.put(TYPE_PHONE_NUM, "전화 : ");
            MAP_TYPE.put(TYPE_HOMEPAGE, "홈페이지 : ");
            MAP_TYPE.put(TYPE_HOUSE_TYPE, "종류 : ");
            MAP_TYPE.put(TYPE_CONTENT, "설명 : ");
            MAP_TYPE.put(TYPE_CULTURAL, "문화재 지정 내용 : ");
        }

        private static void dynamicArrange(int type, String data, TextView target) {
            if(TextUtils.isEmpty(data)) {
                target.setVisibility(View.GONE);
            } else {
                target.setVisibility(View.VISIBLE);

                target.setText(MAP_TYPE.get(type) + data);

                switch (type) {
                    case TYPE_PHONE_NUM:
                        Linkify.addLinks(target, Linkify.PHONE_NUMBERS);
                        break;
                    case TYPE_HOMEPAGE:
                        Linkify.addLinks(target, Linkify.WEB_URLS);
                        break;
                }

            }

        }

        private static boolean checkHasImage(String house_id) {
            boolean result = false;

            String[] resource = new String[]{"2", "6", "7", "8", "9", "12", "14", "16", "17", "20", "22", "23"
                    , "24", "25", "28", "30", "32", "35", "37", "39", "42", "45", "47", "49",
                    "50", "53", "54", "97", "204", "218", "219"
                    , "221", "223", "224", "226", "227", "228", "229", "230", "233", "234"};

            for (int i = 0; i < resource.length; i++) {
                if (house_id.equals(resource[i])) {
                    result = true;
                }
            }

            return result;
        }

        private static int[] getResourceId(Context context, String house_id) {

            Resources r = context.getResources();
            int drawableId1 = r.getIdentifier("bukchon_1_" + house_id, "drawable", "com.seoul.hanokmania");
            int drawableId2 = r.getIdentifier("bukchon_2_" + house_id, "drawable", "com.seoul.hanokmania");
            int drawableId3 = r.getIdentifier("bukchon_3_" + house_id, "drawable", "com.seoul.hanokmania");

            int[] resourceId = new int[]{drawableId1, drawableId2, drawableId3};

            return resourceId;

        }

        private static void setImageFromResourceId(Context context, String house_id, ImageView... imageViews) {
            boolean hasImage = checkHasImage(house_id);

            if(hasImage) {
                int[] resourceId = getResourceId(context, house_id);
                imageViews[0].setImageResource(resourceId[0]);
                imageViews[1].setImageResource(resourceId[1]);
                imageViews[2].setImageResource(resourceId[2]);

            } else {
                imageViews[0].setImageResource(R.drawable.bukchon_1_default);
                imageViews[1].setImageResource(R.drawable.bukchon_2_default);
                imageViews[2].setImageResource(R.drawable.bukchon_3_default);
            }


        }
    }



    public void swapData(List groupData, List<List<BukchonItem>> childData) {
        mGroupList = groupData;
        mChildList = childData;
        notifyDataSetChanged();
    }


    static class ViewHolder {
        TextView groupName;

        ImageView itemImageViewLeft;
        ImageView itemImageViewCenter;
        ImageView itemImageViewRight;
        TextView itemName;
        TextView itemAddress;
        TextView itemType;
        TextView itemOwner;
        TextView itemPhoneNum;
        TextView itemHomePage;
        TextView itemCultural;
        TextView itemContent;


    }

}
