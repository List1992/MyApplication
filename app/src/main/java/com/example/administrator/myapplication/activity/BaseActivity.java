package com.example.administrator.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class BaseActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//JPushInterface.onPause(this);
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T getViewById(@NonNull int id) {
		return (T) findViewById(id);
	}

}
