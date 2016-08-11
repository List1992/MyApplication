package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.activity.BaiduMapActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 闪屏页面
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.splash_img)
    ImageView splash_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //发送一个延迟3秒的消息
        handler.sendEmptyMessageDelayed(Start_Main, 3000);
        startAnimation();
    }

    private int Start_Main = 100;
    /**
     * 收到消息后跳转页面
     */
    public Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            //跳转到主页面
            if (msg.what == Start_Main) {
                Log.i("tao", "收到消息后准备跳转到主页面");
                //  MainActivity2.startMain(SplashActivity.this);
                startActivity(new Intent(SplashActivity.this, BaiduMapActivity.class));
                finish();
            }
        }

        ;
    };

    /**
     * 执行动画
     */
    public void startAnimation() {

        // 动画集合
        AnimationSet set = new AnimationSet(false);
        // 旋转动画

        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(1000);// 动画时间
        rotate.setFillAfter(true);// 保持动画状态
        // 缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale.setDuration(1000);// 动画时间
        scale.setFillAfter(true);// 保持动画状态
        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation((float) 0.5, 1);
        alpha.setDuration(3000);// 动画时间
        alpha.setFillAfter(true);// 保持动画状态
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        Log.i("tao", "开始执行动画");
        //给整个布局添加一个渐变动画
        ll.startAnimation(alpha);
        //给图片添加一组动画
        splash_img.startAnimation(set);
    }
}
