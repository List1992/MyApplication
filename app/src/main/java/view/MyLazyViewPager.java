package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/5/20.
 */
public class MyLazyViewPager extends LazyViewPager {


    public MyLazyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLazyViewPager(Context context) {
        super(context);
    }

    //屏蔽viewpager的左右滑动

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        //return super.onInterceptTouchEvent(ev);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        //return super.onTouchEvent(ev);
        return false;
    }
}
