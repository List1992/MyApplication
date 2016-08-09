package com.example.administrator.myapplication.activity;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.LunboBean;
import com.example.administrator.myapplication.lunbo.ImageCycleView;
import com.example.administrator.myapplication.utils.CommonRequest;
import com.example.administrator.myapplication.utils.Constants;
import com.example.administrator.myapplication.utils.GlideCircleTransform;
import com.example.administrator.myapplication.utils.ImageLoaderUtils;
import com.example.administrator.myapplication.utils.LogUtil;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.utils.VolleyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import view.Toastutils;

/**
 * Created by lisongtao on 2016/7/29 11:39.
 * desc：
 */
public class LunboDemo extends AppCompatActivity {

    @BindView(R.id.ad_view)
    ImageCycleView mAdView;
    private ArrayList<LunboBean.Lbean> mData1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lunbodemo_layout);
        ButterKnife.bind(this);

        //设置轮播图的宽高
        int screenWidth = UIUtils.getScreenWidth(this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(screenWidth,screenWidth/3);
        mAdView.setLayoutParams(params);
        getLunboData();
    }


    public void getLunboData() {

        Map<String, String> params = new HashMap<>();
        String url = "http://cg.cggewei.com/geweiapitest/app_index_test2.php";
        params.put("city", "411000");
        params.put("user_id", "18264");
        CommonRequest.getCommonRequest(new CommonRequest.RequestCallBack() {
            @Override
            public void onResponse(String result) {
                LogUtil.i("tao", "轮播数据：" + result);
                parseJson(result);
            }

            @Override
            public void onErrorResponse(String errorMsg) {
                Toastutils.showToast(LunboDemo.this, "轮播图联网失败");
            }
        }).requestDataByPost(url, params);
    }

    private void parseJson(String result) {
        Gson gson = new Gson();

        LunboBean frombean = gson.fromJson(result, new TypeToken<LunboBean>() {
        }.getType());

        mData1 = frombean.data1;

        mAdView.setImageResources(mData1, mAdCycleViewListener);

    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件

            if (mData1 != null && mData1.size() < 1) {
                return;
            }

            String url_html = mData1.get(position).url_html;

            if (!TextUtils.equals("#", url_html)) {

                ActivitiesWebViewActivity.start(LunboDemo.this, url_html, "");
            }

        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            // ImageLoader.getInstance().displayImage(imageURL, imageView, SyncStateContract.Constants.IM_IMAGE_OPTIONS);// 此处本人使用了ImageLoader对图片进行加装！


            //使用Imageloader加载图片

            ImageLoader.getInstance().displayImage(imageURL, imageView, Constants.IM_IMAGE_OPTIONS);
            //使用Glide加载图片
            //ImageLoaderUtils.display(LunboDemo.this, imageURL, imageView);
            //使用Volley加载图片
            //VolleyUtil.display(imageURL, imageView);


        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mAdView.pushImageCycle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.startImageCycle();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdView.pushImageCycle();
    }
}
