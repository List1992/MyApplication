package com.example.administrator.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.utils.MyOrientationListener;
import com.example.administrator.myapplication.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import view.Toastutils;

/**
 * Created by lisongtao on 2016/8/2 15:25.
 * desc：百度地图
 */
public class BaiduMapActivity extends AppCompatActivity {


    @BindView(R.id.id_marker_info)
    RelativeLayout mIdMarkerInfo;
    /**
     * 地图控件
     */
    @BindView(R.id.id_bmapView)
    MapView mMapView;

    @BindView(R.id.location)
    Button mLocation;
    @BindView(R.id.map_common)
    Button mMapCommon;
    @BindView(R.id.map_traffic)
    Button mMapTraffic;
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

    private BitmapDescriptor bitmap;
    private String address = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);


        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);    //注册监听函数

        //获取地图
        mBaiduMap = mMapView.getMap();

        //设置是否显示比例尺控件（默认显示）
        mMapView.showScaleControl(false);
        //设置是否显示缩放控件（默认显示）
        mMapView.showZoomControls(false);
        // 删除百度地图LoGo（默认显示）
        mMapView.removeViewAt(1);


        //设置地图缩放比例
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);

        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        //option.setScanSpan(1000);//一秒请求一次
        mLocationClient.setLocOption(option);


        // 设置marker图标
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.maker);
        //地图点击事件
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {


            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }

            //此方法就是点击地图监听
            @Override
            public void onMapClick(LatLng latLng) {
                //获取经纬度
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                Log.i("tao", "latitude=" + latitude + ",longitude=" + longitude);
                //先清除图层
                mBaiduMap.clear();
                // 定义Maker坐标点
                LatLng point = new LatLng(latitude, longitude);
                // 构建MarkerOption，用于在地图上添加Marker
                MarkerOptions options = new MarkerOptions().position(point)
                        .icon(bitmap);
                // 在地图上添加Marker，并显示
                mBaiduMap.addOverlay(options);
                //实例化一个地理编码查询对象
                GeoCoder geoCoder = GeoCoder.newInstance();
                //设置反地理编码位置坐标
                ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                op.location(latLng);
                //发起反地理编码请求(经纬度->地址信息)
                geoCoder.reverseGeoCode(op);
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
                        //获取点击的坐标地址
                        address = arg0.getAddress();
                        Log.i("tao", "当前位置：" + address);
                        Toastutils.showToast(BaiduMapActivity.this, address);
                    }

                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult arg0) {
                    }
                });
            }
        });


    }


//    第二步，配置定位SDK参数
//    设置定位参数包括：定位模式（高精度定位模式，低功耗定位模式和仅用设备定位模式），返回坐标类型，是否打开GPS，是否返回地址信息、位置语义化信息、POI信息等等。
//    LocationClientOption类，该类用来设置定位SDK的定位方式，e.g.：

    //    高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
//    低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位（Wi-Fi和基站定位）；
//    仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位。

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private boolean isNormal = true;
    private boolean islocal = false;

    @OnClick({R.id.location, R.id.map_common, R.id.map_traffic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location:
                islocal = !islocal;
                if (islocal) {
                    mBaiduMap.setMyLocationEnabled(true);
                    mLocationClient.start();
                    mLocation.setText("不告诉你");
                } else {
                    mLocation.setText("我在哪儿");
                    mBaiduMap.setMyLocationEnabled(false);
                    mLocationClient.stop();
                }

                break;
            case R.id.map_common://卫星地图
                isNormal = !isNormal;
                if (isNormal) {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    mMapCommon.setText("卫星地图");
                } else {

                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    mMapCommon.setText("普通地图");
                }

                break;
            case R.id.map_traffic://是否开启交通路况信息
                if (!mBaiduMap.isTrafficEnabled()) {
                    mBaiduMap.setTrafficEnabled(true);
                    mMapTraffic.setText("关闭交通图");
                } else {
                    mBaiduMap.setTrafficEnabled(false);
                    mMapTraffic.setText("开启交通图");
                }
                break;
        }
    }

    /**
     * 是否是第一次定位
     */
    private boolean isFirst = true;

    //    第三步，实现BDLocationListener接口
//    BDLocationListener接口有1个方法需要实现： 1.接收异步返回的定位结果，参数是BDLocation类型参数。
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("tao", sb.toString());
            // mapview 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            // 构造定位数据
            MyLocationData data = new MyLocationData.Builder().accuracy(location.getRadius()).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(data);
            // 设置自定义图标
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.drawable.navi_map_gps_locked);
            MyLocationConfiguration config = new MyLocationConfiguration(
                    mCurrentMode, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfigeration(config);
            //根据经纬度，将地图位置移动到当前位置
//            if (isFirst) {
//                isFirst = false;
            LatLng atlng = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(atlng);
            mBaiduMap.animateMapStatus(msu);
            //mBaiduMap.setMapStatus(msu);
//            }


            //实例化一个地理编码查询对象
            GeoCoder geoCoder = GeoCoder.newInstance();
            //设置反地理编码位置坐标
            ReverseGeoCodeOption op = new ReverseGeoCodeOption();
            op.location(atlng);
            //发起反地理编码请求(经纬度->地址信息)
            geoCoder.reverseGeoCode(op);

            geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                }

                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    //根据经纬度，查询到对应的地址信息
                    Toastutils.showToast(BaiduMapActivity.this, reverseGeoCodeResult.getAddress());
                }
            });

        }

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


    @Override
    protected void onStart() {
        super.onStart();
//        //开启定位功能
//        mBaiduMap.setMyLocationEnabled(true);
//        if (!mLocationClient.isStarted()) {
//            //开始定位
//            mLocationClient.start();
//        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;

        mLocationClient.stop();
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


}


