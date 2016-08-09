package mypager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.ExpandableListViewDemo;
import com.example.administrator.myapplication.utils.LogUtil;

import android.view.View.OnClickListener;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/5/20.
 */
public class WeiXinPager extends BasePager implements OnClickListener {
    public WeiXinPager(Context ctx) {
        super(ctx);
    }

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

    //初始化布局
    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.weixin_pager, null);
        ButterKnife.bind(this, view);

        //开始动画
        ((AnimationDrawable) img.getBackground()).start();
        return view;
    }

    //初始化数据
    @Override
    public void initData() {
    }

    //点击事件
    @OnClick({R.id.login, R.id.register})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login:


                String name = username.getText().toString();
                LogUtil.i("tao", "name:" + name);
                String pword = password.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    LogUtil.i("tao", "name是空的");
                    LogUtil.i("tao", Thread.currentThread().getName());
                    LogUtil.i("tao", "context:" + context);
                    Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (name.length() != 11) {
                    LogUtil.i("tao", "非法的手机号");


                    return;
                }
                if (TextUtils.isEmpty(pword)) {
                    LogUtil.i("tao", "密码是空的");

                    return;
                }


                LogUtil.i("tao", "登陆成功");

                break;
            case R.id.register:
                Toast.makeText(context, "请注册", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}

