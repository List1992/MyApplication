package mypager;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2016/5/20.
 * <p>
 * 自定义pager的基类
 */
public abstract class BasePager {

    protected Context context;

    public BasePager(Context ctx) {

        this.context = ctx;
    }

    //初始化view,加载布局
    public abstract View initView();

    //初始化数据
    public abstract void initData();

}
