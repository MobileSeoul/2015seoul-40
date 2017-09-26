package com.seoul.hanokmania.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.models.BukchonItem;
import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.provider.HanokOpenHelper;
import com.seoul.hanokmania.views.adapters.BukchonExpListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray Choe on 2015-10-20.
 */
public class BukchonFragment extends Fragment implements SearchView.OnQueryTextListener, View.OnFocusChangeListener {

    private static final String TAG = BukchonFragment.class.getSimpleName();
    private ExpandableListView mListView;

    private List mGroupList;
    private List<List<BukchonItem>> mChildList;

    private BukchonExpListAdapter mAdapter;
    private SearchView mSearchView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_home:
                Uri uri = Uri.parse("http://bukchon.seoul.go.kr/index.jsp");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                break;
            case R.id.action_search:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bukchon, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnQueryTextFocusChangeListener(this);
    }

    public static BukchonFragment newInstance() {
        BukchonFragment fragment = new BukchonFragment();
        return fragment;
    }

    public BukchonFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bukchon, container, false);
        mListView = (ExpandableListView) view.findViewById(R.id.expandable_list_view);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mGroupList = new ArrayList();
        mChildList = new ArrayList<>();


        setData();

        super.onViewCreated(view, savedInstanceState);
    }

    private void setData() {
        SQLiteDatabase db = new HanokOpenHelper(getContext()).getReadableDatabase();

        String[] projection = new String[]
                {HanokContract.HanokBukchonCol._ID,
                        HanokContract.HanokBukchonCol.TYPE_NAME,
                        HanokContract.HanokBukchonCol.HOUSE_ID,
                        HanokContract.HanokBukchonCol.HOUSE_NAME,
                        HanokContract.HanokBukchonCol.HOUSE_ADDR,
                        HanokContract.HanokBukchonCol.HOUSE_OWNER,
                        HanokContract.HanokBukchonCol.HOUSE_TELL,
                        HanokContract.HanokBukchonCol.HOUSE_HP,
                        HanokContract.HanokBukchonCol.BOOL_CULTURE,
                        HanokContract.HanokBukchonCol.HOUSE_CONTENT};

        String orderBy = HanokContract.HanokBukchonCol.HOUSE_NAME + " desc ";

        Cursor cursor = db.query(true, HanokContract.TABLES[1], projection, null, null, null, null, orderBy, null);

        while (cursor.moveToNext()) {
            // GroupList data
            String house_name = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_NAME));

            // ChildList data
            String type_name = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.TYPE_NAME));
            String house_id = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_ID));
            String house_addr = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_ADDR));
            String house_owner = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_OWNER));
            String house_tell = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_TELL));
            String house_hp = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_HP));
            String bool_culture = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.BOOL_CULTURE));
            String house_content = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_CONTENT));



            if (house_name != null) {
                mGroupList.add(house_name);
                BukchonItem bukchonItem = new BukchonItem(house_name, house_addr, house_owner,
                        house_tell, house_hp, bool_culture, type_name, house_content, house_id);
                List<BukchonItem> childContent = new ArrayList<>();
                childContent.add(bukchonItem);
                mChildList.add(childContent);
            }
        }


        mAdapter = new BukchonExpListAdapter(getContext(), mGroupList, mChildList);

        mListView.setAdapter(mAdapter);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mGroupList = new ArrayList();
        mChildList = new ArrayList<>();

        SQLiteDatabase db = new HanokOpenHelper(getContext()).getReadableDatabase();

        String[] projection = new String[]
                {HanokContract.HanokBukchonCol._ID,
                        HanokContract.HanokBukchonCol.TYPE_NAME,
                        HanokContract.HanokBukchonCol.HOUSE_ID,
                        HanokContract.HanokBukchonCol.HOUSE_NAME,
                        HanokContract.HanokBukchonCol.HOUSE_ADDR,
                        HanokContract.HanokBukchonCol.HOUSE_OWNER,
                        HanokContract.HanokBukchonCol.HOUSE_TELL,
                        HanokContract.HanokBukchonCol.HOUSE_HP,
                        HanokContract.HanokBukchonCol.BOOL_CULTURE,
                        HanokContract.HanokBukchonCol.HOUSE_CONTENT};

        String selection = HanokContract.HanokBukchonCol.HOUSE_NAME + " LIKE ? OR " +
                HanokContract.HanokBukchonCol.HOUSE_ADDR + " LIKE ? ";


        String[] selectionArgs = {"%" + newText + "%", "%" + newText + "%"};

        String orderBy = HanokContract.HanokBukchonCol.HOUSE_NAME + " asc ";

        Cursor cursor = db.query(true, HanokContract.TABLES[7], projection, selection, selectionArgs, null, null, orderBy, null);

        while (cursor.moveToNext()) {
            // GroupList data
            String house_name = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_NAME));

            // ChildList data
            String type_name = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.TYPE_NAME));
            String house_id = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_ID));
            String house_addr = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_ADDR));
            String house_owner = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_OWNER));
            String house_tell = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_TELL));
            String house_hp = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_HP));
            String bool_culture = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.BOOL_CULTURE));
            String house_content = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_CONTENT));



            if (house_name != null) {
                mGroupList.add(house_name);
                BukchonItem bukchonItem = new BukchonItem(house_name, house_addr, house_owner,
                        house_tell, house_hp, bool_culture, type_name, house_content, house_id);
                List<BukchonItem> childContent = new ArrayList<>();
                childContent.add(bukchonItem);
                mChildList.add(childContent);
            }
        }

        mAdapter.swapData(mGroupList, mChildList);
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d(TAG, "포커스 : " + hasFocus);
        if(hasFocus != true) {
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}
