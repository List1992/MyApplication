package myFragment;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.activity.ExpandableListViewDemo;
import com.example.administrator.myapplication.activity.LunboDemo;
import com.example.administrator.myapplication.utils.LogUtil;

/**
 * Created by 李松涛 on 2016/5/25 17:28.
 * 描述：
 */
public class ZoneFragment extends BaseFragment {
    @Override
    public View inintView() {
        TextView textView = new TextView(getActivity());
        textView.setText("空间");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(30);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LunboDemo.class));
            }
        });
        return textView;
    }

    @Override
    public void initData() {
        Log.i("tao", "新创建的空间页面");
    }
}
