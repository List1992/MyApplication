package com.example.administrator.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myapplication.R;

import view.BitmapCache;

public class VolleyUtil {

    private volatile static RequestQueue mRequestQueue;
    private volatile static ImageLoader mImageLoader;

    public static void initialize(Context context) {
        if (mRequestQueue == null) {
            synchronized (VolleyUtil.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                    //  Log.i("tao", "mRequestQueue初始化");
                }
            }
        }

        if (mImageLoader == null) {
            synchronized (VolleyUtil.class) {

                if (mImageLoader == null) {

                    mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
                }
            }

        }


    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            throw new RuntimeException("请先初始化mRequestQueue");
        return mRequestQueue;
    }

    public static ImageLoader getmImageLoader() {
        if (mImageLoader == null) {
            throw new RuntimeException("请先初始化mImageLoader");
        }
        return mImageLoader;
    }

    /**
     * 使用ImageRequest加载图片
     */
    public static void display(String url, final ImageView image) {

        //创建一个ImageRequest对象
        ImageRequest ImageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                //网络请求成功，加载请求到的网络图片
                image.setImageBitmap(bitmap);
                Log.i("tao", "联网成功");
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //网络请求失败，加载默认图片
                image.setImageResource(R.drawable.heart_5);
                Log.i("tao", "联网失败");
            }
        });
        //添加到队列
        mRequestQueue.add(ImageRequest);
    }


    //使用ImageLoader加载图片（自定义BitmapCache）
    public static void loadImage(String url, ImageView image) {
        // ImageLoader imageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
        ImageLoader imageLoader = VolleyUtil.getmImageLoader();
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(image, R.drawable.heart_5, R.drawable.heart_1);
        int i = UIUtils.dip2px(400);
        //imageLoader.get(url, imageListener);
        //下面这个方法可以设置图片的宽高
        imageLoader.get(url, imageListener, i, i);

    }


}



