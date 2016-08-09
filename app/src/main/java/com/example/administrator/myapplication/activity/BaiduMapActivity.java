package com.example.administrator.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.utils.MyOrientationListener;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import view.Toastutils;

/**
 * Created by lisongtao on 2016/8/2 15:25.
 * desc：百度地图
 */
public class BaiduMapActivity extends AppCompatActivity {


    @BindView(R.id.location)
    Button mLocation;
    @BindView(R.id.map_common)
    Button mMapCommon;
    @BindView(R.id.map_traffic)
    Button maptraffic;

    @BindView(R.id.id_marker_info)
    RelativeLayout mIdMarkerInfo;
    /**
     * 地图控件
     */
    @BindView(R.id.id_bmapView)
    MapView mMapView;
    /**
     * 地图实例
     */
    private BaiduMap mBaiduMap;
    /**
     * 定位的客户端
     */
    private LocationClient mLocationClient;
    /**
     * 定位的监听器
     */
    public MyLocationListener mMyLocationListener;
    /**
     * 当前定位的模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    /***
     * 是否是第一次定位
     */
    private volatile boolean isFristLocation = true;

    /**
     * 最新一次的经纬度
     */
    private double mCurrentLantitude;
    private double mCurrentLongitude;
    /**
     * 当前的精度
     */
    private float mCurrentAccracy;
    /**
     * 方向传感器的监听器
     */
    private MyOrientationListener myOrientationListener;
    /**
     * 方向传感器X方向的值
     */
    private int mXDirection;

    /**
     * 地图定位的模式
     */
    private String[] mStyles = new String[]{"地图模式【正常】", "地图模式【跟随】",
            "地图模式【罗盘】"};
    private int mCurrentStyle = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);


    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView=null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    /**
     * 初始化方向传感器
     */
    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(
                getApplicationContext());
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
                    @Override
                    public void onOrientationChanged(float x) {
                        mXDirection = (int) x;

                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccracy)
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mXDirection)
                                .latitude(mCurrentLantitude)
                                .longitude(mCurrentLongitude).build();
                        // 设置定位数据
                        mBaiduMap.setMyLocationData(locData);
                        // 设置自定义图标
                        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                                .fromResource(R.drawable.navi_map_gps_locked);
                        MyLocationConfiguration config = new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker);
                        mBaiduMap.setMyLocationConfigeration(config);

                    }
                });
    }

    /**
     * 初始化定位相关代码
     */
    private void initMyLocation() {

        // 定位初始化
 

        mLocationClient = new LocationClient(getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    private boolean flag;

    @OnClick({R.id.location, R.id.map_common, R.id.map_traffic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location://我的位置
                center2myLoc();
                break;
            case R.id.map_common:// 卫星地图/普通地图

                if (flag) {
                    //切换到卫星地图
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    mMapCommon.setText("卫星地图");
                } else {
                    //切换到普通地图
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    mMapCommon.setText("普通地图");
                }
                //点击后切换状态
                flag = !flag;
                break;
            case R.id.map_traffic:
                // 开启交通图
                if (mBaiduMap.isTrafficEnabled()) {
                    maptraffic.setText("开启实时交通");
                    mBaiduMap.setTrafficEnabled(false);
                } else {
                    maptraffic.setText("关闭实时交通");
                    mBaiduMap.setTrafficEnabled(true);
                }
                break;
        }
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mXDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mCurrentAccracy = location.getRadius();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            mCurrentLantitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
            // 设置自定义图标
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.drawable.navi_map_gps_locked);
            MyLocationConfiguration config = new MyLocationConfiguration(
                    mCurrentMode, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfigeration(config);
            // 第一次定位时，将地图位置移动到当前位置
            if (isFristLocation) {
                isFristLocation = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }


    }

    /**
     * 手动请求定位的方法
     */
    public void requestLocation() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            Toastutils.showToast(this, "正在定位......");
            mLocationClient.requestLocation();
        } else {
            Log.i("tao", "locClient is null or not started");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 默认点击menu菜单，菜单项不现实图标，反射强制其显示
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (featureId == Window.FEATURE_OPTIONS_PANEL && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }


        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.id_menu_map_common:
                // 普通地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.id_menu_map_site:// 卫星地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.id_menu_map_traffic:
                // 开启交通图

                if (mBaiduMap.isTrafficEnabled()) {
                    item.setTitle("开启实时交通");
                    mBaiduMap.setTrafficEnabled(false);
                } else {
                    item.setTitle("关闭实时交通");
                    mBaiduMap.setTrafficEnabled(true);
                }
                break;
            case R.id.id_menu_map_myLoc:
                center2myLoc();
                break;
            case R.id.id_menu_map_style:
                mCurrentStyle = (++mCurrentStyle) % mStyles.length;
                item.setTitle(mStyles[mCurrentStyle]);
                // 设置自定义图标
                switch (mCurrentStyle) {
                    case 0:
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        break;
                    case 1:
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        break;
                    case 2:
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        break;
                }
                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                        .fromResource(R.drawable.navi_map_gps_locked);
                MyLocationConfiguration config = new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker);
                mBaiduMap.setMyLocationConfigeration(config);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 地图移动到我的位置,此处可以重新发定位请求，然后定位；
     * 直接拿最近一次经纬度，如果长时间没有定位成功，可能会显示效果不好
     */
    private void center2myLoc() {
        LatLng ll = new LatLng(mCurrentLantitude, mCurrentLongitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }


}


