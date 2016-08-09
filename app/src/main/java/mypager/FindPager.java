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
public class FindPager extends BasePager {
    public FindPager(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {

        TextView textView = new TextView(context);
        textView.setText("发现");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(30);

        return textView;
    }

    @Override
    public void initData() {
        Log.i("tao","新创建的发现页面");
        Toast.makeText(context, "新创建的发现页面",Toast.LENGTH_SHORT).show();
    }
}
