package myFragment;

import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.TestOkhttp;
import com.example.administrator.myapplication.utils.GlideCircleTransform;
import com.example.administrator.myapplication.utils.GlobalConstant;
import com.example.administrator.myapplication.utils.LogUtil;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.utils.VolleyUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import view.Toastutils;

/**
 * Created by 李松涛 on 2016/5/25 17:28.
 * 描述：
 */
public class QQFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.volley)
    TextView volley;
    @BindView(R.id.nohttp)
    TextView nohttp;
    @BindView(R.id.glide)
    TextView glide;
    @BindView(R.id.okhttp)
    TextView okhttp;
    @BindView(R.id.volley_content)
    LinearLayout volley_content;
    @BindView(R.id.nohttp_content)
    ScrollView nohttp_content;
    @BindView(R.id.getVolley)
    Button get_volley;
    @BindView(R.id.postVolley)
    Button post_volley;
    @BindView(R.id.getnohttp)
    Button get_nohttp;
    @BindView(R.id.postnohttp)
    Button post_nohttp;
    @BindView(R.id.tv_get)
    TextView tv_get;
    @BindView(R.id.tv_post)
    TextView tv_post;
    @BindView(R.id.notv_get)
    TextView notv_get;
    @BindView(R.id.notv_post)
    TextView notv_post;
    @BindView(R.id.volleyloadImage)
    Button volleyloadimage;
    @BindView(R.id.volley_image)
    ImageView volley_image;

    @BindView(R.id.get_okhttp)
    Button get_okhttp;
    @BindView(R.id.post_okhttp)
    Button post_okhttp;
    @BindView(R.id.oktv_get)
    TextView oktv_get;
    @BindView(R.id.oktv_post)
    TextView oktv_post;
    @BindView(R.id.glideloadimage)
    Button glideloadimage;
    @BindView(R.id.glide_image)
    ImageView glide_image;

    private final int VOLLEY_FLAG = 0X001;
    private final int NOHTTP_FLAG = 0X002;
    private final int GLIDE_FLAG = 0X003;
    private final int OKHTTP_FLAG = 0X004;

    @Override
    public View inintView() {
        View view = View.inflate(getActivity(), R.layout.qq_pager, null);
        ButterKnife.bind(this, view);

        //初始化界面，默认选中volley
        Selected(VOLLEY_FLAG);
        return view;
    }

    @Override
    public void initData() {

    }


    //点击事件
    @OnClick({R.id.volley, R.id.okhttp, R.id.nohttp, R.id.glide, R.id.getVolley, R.id.postVolley, R.id.volleyloadImage, R.id.nohttploadImage, R.id.get_okhttp, R.id.post_okhttp, R.id.glideloadimage, R.id.getnohttp, R.id.postnohttp})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.volley:
                Selected(VOLLEY_FLAG);
                break;
            case R.id.nohttp:
                Selected(NOHTTP_FLAG);
                break;
            case R.id.okhttp:
                Selected(OKHTTP_FLAG);
                break;
            case R.id.glide:
                Selected(GLIDE_FLAG);
                break;
            case R.id.getVolley://volley的get请求
                if (get_volley.isSelected()) {

                    get_volley.setSelected(false);
                    tv_get.setText("");
                } else {
                    get_volley.setSelected(true);
                    //联网获取数据
                    getVolley();
                }
                break;
            case R.id.postVolley://volley的post请求
                Toastutils.showToast(getActivity(), "volley的post请求");
                postVolley();

                break;
            case R.id.volleyloadImage://volley加载图片
                Toastutils.showToast(getActivity(), "volley加载图片");
                break;
            case R.id.getnohttp://nohttp的get请求
                Toastutils.showToast(getActivity(), "nohttp的get请求");
                break;
            case R.id.postnohttp://nohttp的post的请求
                Toastutils.showToast(getActivity(), "nohttp的post的请求");
                break;
            case R.id.nohttploadImage://nohttp加载图片
                Toastutils.showToast(getActivity(), "nohttp加载图片");
                break;
            case R.id.get_okhttp://okhttp的Get请求
                Toastutils.showToast(getActivity(), "okhttp的Get请求");
                break;
            case R.id.post_okhttp://okhttp的post请求
                Toastutils.showToast(getActivity(), "okhttp的post请求");
                break;
            case R.id.glideloadimage://glide加载图片
                if (glide.isSelected()) {
                    glide_image.setVisibility(View.GONE);
                    glide.setSelected(false);
                } else {
                    glide_image.setVisibility(View.VISIBLE);
                    Toastutils.showToast(getActivity(), "glide加载图片");
                    Glide.with(getActivity()).load(R.mipmap.mine_bg).transform(new GlideCircleTransform(getActivity())).diskCacheStrategy(DiskCacheStrategy.ALL).into(glide_image);
                    glide.setSelected(true);
                }


                break;
        }
    }

    private void postVolley() {
        StringRequest StringRequest = new StringRequest(Request.Method.POST, GlobalConstant.HOMEPAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                //展示请求到的数据
                tv_post.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toastutils.showToast(getActivity(), "Post请求失败");
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("city", "411000");
                return params;
            }
        };
        VolleyUtil.getRequestQueue().add(StringRequest);
    }

    private void getVolley() {
        StringRequest StringRequest = new StringRequest(GlobalConstant.GoodsclassyUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("tao", "get请求成功");
                tv_get.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("tao", "get请求失败");
            }
        });
        VolleyUtil.getRequestQueue().add(StringRequest);
    }


    /**
     * 切换导航按钮
     */

    public void Selected(int flag) {

        switch (flag) {
            case VOLLEY_FLAG:
                volley.setSelected(true);
                nohttp.setSelected(false);
                volley_content.setVisibility(View.VISIBLE);
                nohttp_content.setVisibility(View.GONE);
                break;
            case NOHTTP_FLAG:
                volley.setSelected(false);
                nohttp.setSelected(true);
                volley_content.setVisibility(View.GONE);
                nohttp_content.setVisibility(View.VISIBLE);
                break;
            case GLIDE_FLAG:
                glide.setSelected(true);
                okhttp.setSelected(false);
                break;
            case OKHTTP_FLAG:
                glide.setSelected(false);
                okhttp.setSelected(true);
                break;

        }

    }


}
