package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import mypager.BasePager;
import mypager.FindPager;
import mypager.QQPager;
import mypager.WeiXinPager;
import mypager.ZonePager;
import view.LazyViewPager;
import view.MyLazyViewPager;
import view.Toastutils;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, LazyViewPager.OnPageChangeListener {

    private MyLazyViewPager lazyViewPager;

    private RadioGroup radioGroup;
    private RadioButton weixin, qq, find, zone;

    private static final int WEIXIN_TAG = 0X01;
    private static final int QQ_TAG = 0X02;
    private static final int ZONE_TAG = 0X03;
    private static final int FIND_TAG = 0X04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();

        initdata();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.icon1:
                break;
            case R.id.icon2:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //初始化布局
    private void initview() {
        lazyViewPager = (MyLazyViewPager) findViewById(R.id.MyLazyViewpager);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        weixin = (RadioButton) findViewById(R.id.weixin);
        qq = (RadioButton) findViewById(R.id.qq);
        find = (RadioButton) findViewById(R.id.find);
        zone = (RadioButton) findViewById(R.id.zone);
        adapterPagers = new ArrayList<BasePager>();

        //默认选中第一个radiobutton
        weixin.setChecked(true);
        radioGroup.setOnCheckedChangeListener(this);
        lazyViewPager.setOnPageChangeListener(this);
    }

    //初始化数据
    private void initdata() {
        adapterPagers.add(new WeiXinPager(MainActivity.this));
        adapterPagers.add(new QQPager(MainActivity.this));
        adapterPagers.add(new FindPager(MainActivity.this));
        adapterPagers.add(new ZonePager(MainActivity.this));
        adapter = new MyAdapter(adapterPagers);

        //设置适配器
        lazyViewPager.setAdapter(adapter);
    }


    //viewpager的所有页面的集合
    private ArrayList<BasePager> adapterPagers;

    private MyAdapter adapter;

    //RadioGroup切换选中的Radiobutton时的监听
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        int currentindex = 0;
        switch (checkedId) {

            case R.id.weixin:
                currentindex = 0;

                break;
            case R.id.qq:
                currentindex = 1;

                break;
            case R.id.find:
                currentindex = 2;

                break;
            case R.id.zone:
                currentindex = 3;

                break;

        }
        //根据选中的导航按钮，切换viewpager的页面
        lazyViewPager.setCurrentItem(currentindex);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //切换页面时
    @Override
    public void onPageSelected(int position) {

        //根据切换到的页面，设置底部导航按钮的选中状态
        switch (position) {

            case 0:
                weixin.setChecked(true);
                break;
            case 1:
                qq.setChecked(true);
                break;
            case 2:
                find.setChecked(true);
                break;
            case 3:
                zone.setChecked(true);
                break;

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //viewpager的适配器
    public class MyAdapter extends PagerAdapter {
        private ArrayList<BasePager> pagers;

        public MyAdapter(ArrayList<BasePager> pagers) {

            this.pagers = pagers;
        }

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager currentPager = pagers.get(position);
            View view = currentPager.initView();
            currentPager.initData();
            container.addView(view);
            return view;
        }
    }

    //从其他页面跳转到MainActivity的方法
    public static void startMain(AppCompatActivity act) {
        Intent intent = new Intent(act, MainActivity.class);
        act.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //点击返回键的时候，取消吐司
        Toastutils.cancelToast();
        super.onBackPressed();
    }
}
