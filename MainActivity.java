package com.seoul.hanokmania;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.seoul.hanokmania.activities.ChartActivity;
import com.seoul.hanokmania.database.ManageDbFragment;
import com.seoul.hanokmania.events.ChartClickEvent;
import com.seoul.hanokmania.events.DbUpdateEvent;
import com.seoul.hanokmania.events.Event;
import com.seoul.hanokmania.guide.GuideActivity;
import com.seoul.hanokmania.managers.Manager;

import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE = 77;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager mViewPager;
    private List<String> mTitles;

    private MenuItem activeMenuItem;
    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        // Android 6.0 권한 체크
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // 권한 체크 화면 보여주기
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // 사용자가 이전에 거부를 했을 경우
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            } else {
                // 권한이 없을 때 권한 요청
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }

        init();

        SharedPreferences sharedPreferences = getSharedPreferences("hanokmania", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("first", true)) {
            startActivity(new Intent(MainActivity.this, GuideActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 권한 승인 또는 거부 시 처리
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(MainActivity.this, "권한 승인 됨", Toast.LENGTH_SHORT).show();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "권한 거부 됨", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void init() {

        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 타이틀 목록
        mTitles = Arrays.asList(getResources().getStringArray(R.array.nav_menu_array));

        mMyAdapter = new MyAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mMyAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 페이지가 변경 되면 상단 타이틀도 변경

                toolbar.setTitle(mTitles.get(position));

                // 네비게이션 드로워도 변경
                navigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 첫 번째 아이템이 선택 된 것으로 표시
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        String title = menuItem.getTitle().toString();
        Log.d(TAG, "title : " + title);
        int index = mTitles.indexOf(title);
        Log.d(TAG, "index : " + index);

        if (index == 2) {
            ManageDbFragment dialogFragment = new ManageDbFragment();
            dialogFragment.show(getFragmentManager(), "ManageDbFragment");
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawers();
            return true;
        }

        mViewPager.setCurrentItem(index, true);

        if (activeMenuItem != null) {
            activeMenuItem.setChecked(false);
        }
        activeMenuItem = menuItem;
        menuItem.setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawers();

        return true;
    }

    // FragmentPagerAdapter
    public static class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return Manager.getInstance(position);
        }

        @Override
        public int getCount() {
            return Manager.FRAGMENTS.length;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    /**
     * EventBus 이벤트를 수신하는 콜백 메소드
     *
     * @param event ChartClickEvent 등등
     */
    public void onEvent(Event event) {
        // com.seoul.hanokmania.views.adapters.HanokGraphAdapter 에서 호출 됨
        if (event instanceof ChartClickEvent) {
            ChartClickEvent e = (ChartClickEvent) event;

            // Activity Transition 은 롤리팝 전용
            Intent intent = new Intent(this, ChartActivity.class);
            startActivity(intent);
        } else if (event instanceof DbUpdateEvent) {
            mMyAdapter.notifyDataSetChanged();
        }
    }
}
