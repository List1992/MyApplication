package com.example.administrator.myapplication.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;


public class ActivitiesWebViewActivity extends BaseActivity implements OnClickListener {

	private WebView wv_activities;
	private CustomWebViewClient mWebViewClient;
	private TextView tv_title;
	private LinearLayout ll_back;

	private String activityUrl;
	private String title;
	
	private ProgressDialog progressDialog;

	public static void start(Activity aty, String url, String title) {
		if (aty == null || TextUtils.isEmpty(url)) {
			new Throwable("ActivitiesWebViewActivity - params cna not be null!");
		}
		if (TextUtils.isEmpty(title)) {
			title = "最新活动";
		}
		Intent intent = new Intent(aty, ActivitiesWebViewActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		aty.startActivity(intent);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitise_web_view);
		getIntentData();
		initView();
		registeEvent();
		setData();
		setUpWebView();
		wv_activities.loadUrl(activityUrl);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setUpWebView() {
		mWebViewClient = new CustomWebViewClient();
		wv_activities.setWebViewClient(mWebViewClient);
		wv_activities.getSettings().setJavaScriptEnabled(true);
	}

	private void setData() {
		tv_title.setText(title);
	}

	private void registeEvent() {
		ll_back.setOnClickListener(this);
	}

	private void getIntentData() {
		Intent intent = getIntent();
		activityUrl = intent.getStringExtra("url");
		title = intent.getStringExtra("title");
		checkUrl();
	}

	private void initView() {
		wv_activities = (WebView) findViewById(R.id.wv_activities);
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中……");
	}

	private void checkUrl() {
		if (!activityUrl.startsWith("http")) {
			activityUrl = "http://" + activityUrl;
		}
	}

	class CustomWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Intent intent = new Intent(ActivitiesWebViewActivity.this, ExpandableListViewDemo.class);
			String[] urls = url.split("\\?");
			String pid = null;
			if (urls[0].contains("bdt_product")) {
				if (urls.length > 0) {
					String params[] = urls[1].split("\\&");
					for (String param : params) {
						if (param.startsWith("id=")) {
							String keyValue[] = param.split("\\=");
							pid = keyValue[1];
							break;
						}
					}
					intent.putExtra("productDetail2", pid);
					ActivitiesWebViewActivity.this.startActivity(intent);
					return true;
				}
			}
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			Log.e("tag", "onPageFinished");
			if(progressDialog!=null&&progressDialog.isShowing()){
				progressDialog.dismiss();
			}
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			Log.e("tag", "onPageStarted");
			if(progressDialog!=null){
				progressDialog.show();
			}
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_back:
			if (wv_activities.canGoBack()) {
				wv_activities.goBack();
			} else {
				finish();
			}
			break;
		}
	}
}
