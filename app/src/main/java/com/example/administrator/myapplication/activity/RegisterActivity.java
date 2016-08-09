package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.CheckCodeBean;
import com.example.administrator.myapplication.utils.CommonRequest;
import com.example.administrator.myapplication.utils.GlobalConstant;
import com.example.administrator.myapplication.utils.LogUtil;
import com.example.administrator.myapplication.utils.StatusColor;
import com.example.administrator.myapplication.utils.VolleyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import view.Toastutils;

/**
 * Created by 李松涛 on 2016/6/13 09:55.
 * 描述：注册页面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.telphone)
    EditText telphone;
    @BindView(R.id.msg)
    TextView msg;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.getCode)
    Button getcode;
    @BindView(R.id.repwd)
    EditText repwd;
    @BindView(R.id.doRegister)
    Button doRegister;
    @BindView(R.id.goBack)
    RelativeLayout goback;
    @BindView(R.id.start)
    Button start;

    //验证码
    int random;

    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        ButterKnife.bind(this);
        StatusColor.setStatusColor(this, R.color.defalut);

        //设置倒计时时间
        timeCount = new TimeCount(60000, 1000);
    }


    @OnClick({R.id.getCode, R.id.doRegister, R.id.goBack})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.getCode:
                //获取验证码
                String number = telphone.getText().toString().trim();
                if (!checkTelephone(number)) {
                    return;
                }
                //开始倒计时
                timeCount.start();
                //发送验证码
                getCode(number);
                break;
            case R.id.doRegister:
                //立即注册的按钮
                String coDe = code.getText().toString().trim();
                //检验验证码是否正确
                if (!isenableCode(coDe)) {

                    return;
                }
                String password = pwd.getText().toString().trim();
                String rePassWord = repwd.getText().toString().trim();
                //检验密码格式
                if (!isPassWordOk(password, rePassWord)) {

                    return;
                }
                break;

            case R.id.goBack:
                finish();
                break;

        }
    }


    /**
     * 向手机端发送短信验证码
     *
     * @param telphone 手机号码
     */
    private void getCode(String telphone) {
        String httpUrl = "http://sms.bechtech.cn/Api/send/data/json?";
        String httpArg = "accesskey=" + GlobalConstant.LAIXIN_ACCESS_KEY + "&secretkey=" + GlobalConstant.LAIXIN_SECRET_KEY + "&mobile=" + telphone + "&content=" + getMessage();
        String sendMsgUrl = httpUrl + httpArg;
        Log.i("tao", sendMsgUrl);
        //联网向手机端发送验证码
        StringRequest stringRequest = new StringRequest(sendMsgUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                //解析json
                Gson gson = new Gson();

                CheckCodeBean checkCodeBean = gson.fromJson(s, new TypeToken<CheckCodeBean>() {
                }.getType());

                if (TextUtils.isEmpty(checkCodeBean.desc)) {

                    Toastutils.showToast(RegisterActivity.this, "验证码发送成功");

                    //发送成功后，改变按钮和输入框的状态
                    msg.setVisibility(View.VISIBLE);
                    doRegister.setEnabled(true);
                    code.setEnabled(true);
                    pwd.setEnabled(true);
                    repwd.setEnabled(true);

                } else {

                    Toastutils.showToast(RegisterActivity.this, checkCodeBean.desc);
                    timeCount.cancel();
                    timeCount.onFinish();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toastutils.showToast(RegisterActivity.this, "验证码发送失败，请检查您的网络");
            }
        });
        //发送网络请求
         VolleyUtil.getRequestQueue().add(stringRequest);
        //Volley.newRequestQueue(this).add(stringRequest);
    }

    /**
     * 检验输入的手机号码
     */
    private boolean checkTelephone(String number) {
        if (TextUtils.isEmpty(number)) {

            Toastutils.showToast(RegisterActivity.this, "请输入手机号");
            return false;
        }
        if (number.trim().length() != 11 || !number.trim().startsWith("1")) {

            Toastutils.showToast(RegisterActivity.this, "不合法的手机号");
            return false;
        }
        return true;
    }

    /**
     * 检验输入的密码是否符合格式
     */

    private boolean isPassWordOk(String passWord, String rePwd) {

        if (TextUtils.isEmpty(passWord)) {
            Toastutils.showToast(RegisterActivity.this, "请设置密码");
            return false;
        }

        if (passWord.length() < 6 || passWord.length() > 16) {

            Toastutils.showToast(this, "密码只能是6-16位的字符");
            return false;
        }
        if (TextUtils.isEmpty(rePwd)) {
            Toastutils.showToast(RegisterActivity.this, "请再次输入密码");
            return false;
        }

        if (!passWord.equals(rePwd)) {
            Toastutils.showToast(RegisterActivity.this, "两次输入的密码不一致");
            return false;

        }

        return true;
    }

    /**
     * 检查输入的验证码是否正确
     */
    private boolean isenableCode(String code) {
        LogUtil.i("tao", "random:" + random + ",code:" + code);
        if (TextUtils.isEmpty(code)) {
            Toastutils.showToast(this, "请输入验证码");
            return false;
        }
        if (random != Integer.parseInt(code)) {

            Toastutils.showToast(this, "验证码错误");
            return false;
        }
        return true;
    }

    /**
     * 随机获取四位验证码
     *
     * @return
     */
    private int getRandom() {

        return (int) (Math.random() * 9000 + 1000);
    }

    /**
     * 获取短信内容
     *
     * @return
     */
    private String getMessage() {

        random = getRandom();
        String msgConten = "尊敬的用户您申请注册的验证码为：" + random
                + "（30分钟内有效，本条免费）。【葛微城市平台】";
        try {
            msgConten = URLEncoder.encode(msgConten, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        return msgConten;

    }

    public static void startRegister(Activity act) {
        Intent intent = new Intent(act, RegisterActivity.class);
        act.startActivity(intent);
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            getcode.setText("获取验证码");
            getcode.setClickable(true);
            getcode.setBackgroundResource(R.color.defalut);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            getcode.setClickable(false);//防止重复点击
            getcode.setText(millisUntilFinished / 1000 + "s后重新获取");
            getcode.setBackgroundResource(R.color.colorAccent);
        }
    }
}
