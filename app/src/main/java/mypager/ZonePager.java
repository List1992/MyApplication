package mypager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.activity.ExpandableListViewDemo;

import view.Toastutils;

/**
 * Created by Administrator on 2016/5/20.
 */
public class ZonePager extends BasePager {
    public ZonePager(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {

        TextView textView = new TextView(context);
        textView.setText("空间");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(30);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        return textView;
    }

    @Override
    public void initData() {
        Log.i("tao", "新创建的空间页面");
        Toast.makeText(context, "新创建的空间页面", Toast.LENGTH_SHORT).show();
    }
}
