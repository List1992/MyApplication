package mypager;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/5/20.
 */
public class QQPager extends BasePager {
    public QQPager(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {

        TextView textView = new TextView(context);
        textView.setText("QQ");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(30);

        return textView;
    }

    @Override
    public void initData() {

        Toast.makeText(context, "新创建的qq页面",Toast.LENGTH_SHORT).show();
        Log.i("tao","新创建的qq页面");
    }
}
