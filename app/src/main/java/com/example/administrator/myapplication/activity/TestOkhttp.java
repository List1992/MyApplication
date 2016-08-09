package com.example.administrator.myapplication.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import view.Toastutils;

/**
 * Created by 李松涛 on 2016/6/20 11:04.
 * 描述：练习使用okhttp框架
 */
public class TestOkhttp extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.gethttp)
    Button gethttp;
    @BindView(R.id.posthttp)
    Button posthttp;
    @BindView(R.id.shangchuan)
    Button shangchuan;
    @BindView(R.id.download)
    Button download;
    @BindView(R.id.getimage)
    Button getimage;
    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.gettext)
    TextView gettext;
    @BindView(R.id.getpost)
    TextView posttext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testokhttp_layout);
        ButterKnife.bind(this);

        String name = Thread.currentThread().getName();

    }

    @Override
    @OnClick({R.id.gethttp, R.id.posthttp, R.id.shangchuan, R.id.download, R.id.getimage})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gethttp://get请求
                okhttpGetRequest();
                break;
            case R.id.posthttp://post请求
                okHttpPostRequest();
                break;
            case R.id.shangchuan:
                break;
            case R.id.download:
                break;
            case R.id.getimage:
                break;
        }
    }

    //okhttp联网的get请求方式
    public void okhttpGetRequest() {

        String url = "http://cg.cggewei.com/geweiapi/app_uc80.php";

        // 1.创建Okhttp对象
        OkHttpClient mClient = new OkHttpClient();
        // 2.创建一个Request
        Request request = new Request.Builder().url(url).build();
        // 3.new 一个Call
        Call call = mClient.newCall(request);
        // 4.请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                Toastutils.showToast(TestOkhttp.this, "get请求失败");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                final String result = response.body().string();
                Toastutils.showToast(TestOkhttp.this, "get请求成功");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //请求结果显示出来
                        gettext.setText(result);
                    }
                });


            }
        });
    }


    //Okhttp联网post请求方式
    public void okHttpPostRequest() {
        String url = "http://cg.cggewei.com/geweiapi/app_uc127.php";

        OkHttpClient mClient = new OkHttpClient();

        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("city", "411000");

        Request request = new Request.Builder().url(url).post(builder.build()).build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(TestOkhttp.this, "post请求失败", Toast.LENGTH_SHORT);
                Log.i("tao", "post请求失败");
            }

            @Override
            public void onResponse(final Response response) throws IOException {

                final String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        posttext.setText(string);
                    }
                });
            }
        });

    }

}
