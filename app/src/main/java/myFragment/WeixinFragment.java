package myFragment;

import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.BaiduMapActivity;
import com.example.administrator.myapplication.activity.RegisterActivity;
import com.example.administrator.myapplication.utils.GlideCircleTransform;
import com.example.administrator.myapplication.utils.GlideRoundTransform;
import com.example.administrator.myapplication.utils.GlobalConstant;
import com.example.administrator.myapplication.utils.StatusColor;
import com.example.administrator.myapplication.utils.VolleyUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import view.Toastutils;


/**
 * Created by 李松涛 on 2016/5/25 17:28.
 * 描述：
 */
public class WeixinFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.heart_img)
    ImageView img;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.passwoed)
    EditText password;

    @BindView(R.id.imgtest)
    ImageView imgtest;
    @BindView(R.id.imgtest2)
    ImageView imgtest2;
    @BindView(R.id.test_image)
    ImageView test_image;
    @BindView(R.id.map)
    Button map;
    //  AlertDialog dialog;

    ProgressDialog pDialog;

    @Override
    public View inintView() {
        View view = View.inflate(getActivity(), R.layout.weixin_pager, null);
        ButterKnife.bind(this, view);
        //  dialog = new AlertDialog.Builder(getActivity()).create();
        //创建一个加载匡
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("拼命加载中...");
        //设置加载匡的样式
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        return view;
    }

    Handler handler = new Handler();

    @Override
    public void initData() {
        //显示加载匡
      //  pDialog.show();
//        一般来说在工作线程中执行耗时任务，当任务完成时，会返回UI线程，一般是更新UI。这时有两种方法可以达到目的。
//        一种是handler.sendMessage。发一个消息，再根据消息，执行相关任务代码。
//        另一种是handler.post(r)。r是要执行的任务代码。意思就是说r的代码实际是在UI线程执行的。可以写更新UI的代码。（工作线程是不能更新UI的）

//        new Thread(new Runnable() {//创建并启动线程，使用线程执行模拟的任务
//            public void run() {
//                for (int i = 1; i < 101; i++) { //循环100遍
//                    if (i == 100) {
//
//                        pDialog.dismiss();
//                    }
//                    try {
//                        handler.post(new Runnable() { //更新界面的数据
//
//                            public void run() {
//                                pDialog.incrementProgressBy(1);//增加进度
//                            }
//                        });
//                        Thread.sleep(100);
//
//                    } catch (InterruptedException e) {
//
//                    }
//                }
//            }
//        }).start();


        //开始动画
        ((AnimationDrawable) img.getBackground()).start();
        //设置背景颜色，下面两种用法都可以
        imgtest.setBackgroundColor(getResources().getColor(R.color.defalut));
        imgtest2.setBackgroundColor(Color.parseColor("#ff0000"));

        // test_image.setBackgroundColor(Color.parseColor("#bbbbbb"));

        //VolleyUtil.loadImage("http://cggewei.com/mobile/images/bdt/rihuazhipin_02.jpg", test_image);
        String url = "http://cggewei.com/mobile/images/bdt/rihuazhipin_02.jpg";
        Glide.with(getActivity()).load(R.mipmap.mine_bg).transform(new GlideCircleTransform(getActivity())).diskCacheStrategy(DiskCacheStrategy.ALL).into(test_image);
    }

    @OnClick({R.id.login, R.id.register, R.id.map})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String uname = username.getText().toString();
                //验证输入的手机号
                if (!checkTelphone(uname)) {
                    return;
                }
                String pwd = password.getText().toString().trim();
                //验证密码
                if (!checkPassWord(pwd)) {
                    return;
                }
                Toastutils.showToast(getActivity(), "登录成功");
                break;

            case R.id.register:
                //跳转到注册页面
                if (getActivity() == null) {

                    return;
                }
                RegisterActivity.startRegister(getActivity());
                break;
            case R.id.map://跳转到百度地图页面

                startActivity(new Intent(getActivity(), BaiduMapActivity.class));
                break;
        }
    }

    //验证密码
    private boolean checkPassWord(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            Toastutils.showToast(getActivity(), "请输入6-16位密码");
            return false;
        }
        if (pwd.trim().length() < 6 || pwd.trim().length() > 16) {

            Toastutils.showToast(getActivity(), "密码只能是6-16位的字符");
            return false;
        }
        return true;
    }

    //验证手机号
    private boolean checkTelphone(String uname) {
        //验证手机号
        if (TextUtils.isEmpty(uname)) {
            Toastutils.showToast(getActivity(), "请输入手机号");
            return false;
        }
        if (uname.trim().length() != 11 || !uname.trim().startsWith("1")) {
            Toastutils.showToast(getActivity(), "不合法的手机号");
            return false;
        }
        return true;
    }

}
