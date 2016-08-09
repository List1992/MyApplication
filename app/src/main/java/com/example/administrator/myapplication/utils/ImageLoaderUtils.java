package com.example.administrator.myapplication.utils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.myapplication.App;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 使用glide加载图片
 */
public class ImageLoaderUtils {

	/**
	 * 注：在某些情况下传入fragment或者Activity会出现崩掉的情况，所以这里全部用application替代 为了兼容，不改其他内容方法名不该=改
	 */
	
	/**
	 * @Description:展示网络图片到指定的imageview上
	 * @param fragment
	 * @param url
	 * @param imageView
	 *
	 */
	public static void display(Fragment fragment, String url, ImageView imageView) {
		Glide.with(App.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
	}

	/**
	 * @Description:展示网络图片到指定的imageview上
	 * @param activity
	 * @param url
	 * @param imageView
	 *
	 */
	public static void display(Activity activity, String url, ImageView imageView) {
		Glide.with(App.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
	}

	/**
	 * @Description:展示网络图片到指定的imageview上
	 * @param context
	 * @param url
	 * @param imageView
	 *
	 */
	public static void display(Context context, String url, ImageView imageView) {
		Glide.with(App.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
	}

	/**
	 * @Description:展示网络图片，当对性能要求不高时调用此方法
	 * @param url
	 * @param imageView
	 *
	 */
	public static void display(String url, ImageView imageView) {
		Glide.with(App.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
	}
	
	public static void customerDisplay(String url, final ImageView imageView,  final float width,float height) {
		Glide.with(App.getContext()).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {

			@Override
			public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
				float bitMapHeight = resource.getHeight();
				float bitMapWidth = resource.getWidth();
				float imageRate = bitMapWidth/bitMapHeight;
				float imageHeight = width/imageRate;
				float widths = width;
				if(imageRate < 0.85){
					widths = (float) (widths * 0.60);
					imageHeight = (float) (imageHeight * 0.60);
					//imageView.setScaleType(ScaleType.CENTER_INSIDE);
				}
				ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
				layoutParams.width = (int)widths;
				layoutParams.height = (int)imageHeight;
				imageView.setImageBitmap(resource);
				imageView.setLayoutParams(layoutParams);
				imageView.setMaxHeight(250);
				imageView.setMaxWidth((int)width);
				
				
			}
		});
	}
}
