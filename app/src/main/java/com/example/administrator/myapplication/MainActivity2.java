package com.example.administrator.myapplication;

import android.app.FragmentTransaction;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.myapplication.utils.LogUtil;
import com.example.administrator.myapplication.utils.StatusColor;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import myFragment.BaseFragment;
import myFragment.FindFragment;
import myFragment.QQFragment;
import myFragment.WeixinFragment;
import myFragment.ZoneFragment;
import mypager.BasePager;
import mypager.FindPager;
import mypager.QQPager;
import mypager.WeiXinPager;
import mypager.ZonePager;
import view.LazyViewPager;
import view.MyLazyViewPager;
import view.Toastutils;

public class MainActivity2 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private MyLazyViewPager lazyViewPager;
    private RadioGroup radioGroup;
    private RadioButton weixin, qq, find, zone;
    private FrameLayout frame;

    public static final String WEIXIN_TAG = "w";
    public static final String QQ_TAG = "q";
    public static final String FINF_TAG = "f";
    public static final String ZONE_TAG = "z";
    ArrayList<BaseFragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置状态栏颜色
        StatusColor.setStatusColor(this, R.color.defalut);
        initview();
        initdata();
    }

    //初始化布局
    private void initview() {
        lazyViewPager = (MyLazyViewPager) findViewById(R.id.MyLazyViewpager);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        weixin = (RadioButton) findViewById(R.id.weixin);
        qq = (RadioButton) findViewById(R.id.qq);
        find = (RadioButton) findViewById(R.id.find);
        zone = (RadioButton) findViewById(R.id.zone);
        frame = (FrameLayout) findViewById(R.id.frame);
        mFragments = new ArrayList<>();
        //默认选中第一个radiobutton
        weixin.setChecked(true);
        radioGroup.setOnCheckedChangeListener(this);

        getFragmentManager().beginTransaction().replace(R.id.frame, new WeixinFragment()).commit();
    }

    //初始化数据
    private void initdata() {

    }

    //RadioGroup切换选中的Radiobutton时的监听
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        BaseFragment currentFragment = null;
        switch (checkedId) {

            case R.id.weixin:
                currentFragment = new WeixinFragment();
                break;
            case R.id.qq:

                currentFragment = new QQFragment();
                break;
            case R.id.find:

                currentFragment = new FindFragment();
                break;
            case R.id.zone:

                currentFragment = new ZoneFragment();
                break;

        }
        LogUtil.i("tao", "currentFragment:" + currentFragment);
        getFragmentManager().beginTransaction().replace(R.id.frame, currentFragment).commit();
    }

    /**
     * 根据tag创建Fragment
     *
     * @param fragmentTag
     * @return
     */
    private BaseFragment createFragment(int fragmentTag) {
        BaseFragment fragment = mFragments.get(fragmentTag);
        if (fragment == null) {
            switch (fragmentTag) {
                case 0:
                    fragment = new WeixinFragment();
                    break;
                case 1:
                    fragment = new QQFragment();
                    break;
                case 2:
                    fragment = new FindFragment();
                    break;
                case 3:
                    fragment = new ZoneFragment();
                    break;
            }
            mFragments.add(fragmentTag, fragment);
        }
        return fragment;


    }

    //从其他页面跳转到MainActivity的方法
    public static void startMain(AppCompatActivity act) {
        Intent intent = new Intent(act, MainActivity2.class);
        act.startActivity(intent);
    }

    private long mExitTime;// 上一次点击返回键的时间

    //点击返回键的监听
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (System.currentTimeMillis() - mExitTime > 2000) {
            // 如果当前点击返回键的时间减去上一次点击返回键的时间大于2秒
            Toastutils.showToast(this, "再点一次退出");
            //重新给上一次点击的时间赋值，方便下次使用
            mExitTime = System.currentTimeMillis();
        } else {
            //如果小于两秒，就直接退出
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断按下的按键是不是返回键
        if (keyCode == event.KEYCODE_BACK) {

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
