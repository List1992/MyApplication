package com.example.administrator.myapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

/**
 * Created by lisongtao on 2016/7/26 14:03.
 * desc：
 */
public class ExpandableListViewDemo extends AppCompatActivity {
    ExpandableListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zone_layout);
        list=(ExpandableListView)findViewById(R.id.list);
        //创建一个BaseExpandableListAdapter对象
        final ExpandableListAdapter adapter=new BaseExpandableListAdapter() {
            //设置组视图的图片
            int[] logos = new int[] { R.drawable.weixin_normal, R.drawable.qq_press,R.drawable.find_press};
            //设置组视图的显示文字
            private String[] category = new String[] { "僵尸  ", "魔法植物", "远程植物" };
            //子视图显示文字
            private String[][] subcategory = new String[][] {
                    {"旗帜僵尸", "铠甲僵尸", "书生见识", "铁桶僵尸", "尸娃僵尸","舞蹈僵尸" },
                    { "黄金蘑菇", "贪睡蘑菇", "大头蘑菇", "诱惑植物", "多嘴蘑菇","七彩蘑菇" },
                    { "满天星", "风车植物", "带刺植物", "贪睡植物","双子植物","胆怯蘑菇" }

            };
            //子视图图片
            public int[][] sublogos = new int[][] {
                    { R.drawable.heart_1,R.drawable.heart_2,R.drawable.heart_3,
                            R.drawable.heart_4,R.drawable.heart_5,R.drawable.find_normal},
                    { R.drawable.heart_5,R.drawable.heart_4,R.drawable.heart_3,
                            R.drawable.heart_2,R.drawable.heart_1,R.drawable.zone_normal},

                    { R.drawable.weixin_normal,R.drawable.qq_normal,R.drawable.find_normal,
                            R.drawable.weixin_press,R.drawable.qq_press,R.drawable.find_press } };
            //定义一个显示文字信息的方法
            TextView getTextView(){

                AbsListView.LayoutParams lp=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,64);
                TextView textView=new TextView(ExpandableListViewDemo.this);
                //设置 textView控件的布局
                textView.setLayoutParams(lp);
                //设置该textView中的内容相对于textView的位置
                textView.setGravity(Gravity.CENTER_VERTICAL);
                //设置txtView的内边距
                textView.setPadding(36, 0, 0, 0);
                //设置文本颜色
                textView.setTextColor(Color.BLACK);
                return textView;

            }
            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public boolean hasStableIds() {
                // TODO Auto-generated method stub
                return true;
            }
            //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                                     View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                //定义一个LinearLayout用于存放ImageView、TextView
                LinearLayout ll=new LinearLayout(ExpandableListViewDemo.this);
                //设置子控件的显示方式为水平
                ll.setOrientation(LinearLayout.HORIZONTAL);
                //定义一个ImageView用于显示列表图片
                ImageView logo=new ImageView(ExpandableListViewDemo.this);
                logo.setPadding(50, 0, 0, 0);
                //设置logo的大小(50（padding）+46=96)
                AbsListView.LayoutParams lparParams=new AbsListView.LayoutParams(96,46);
                logo.setLayoutParams(lparParams);
                logo.setImageResource(logos[groupPosition]);
                ll.addView(logo);
                TextView textView=getTextView();
                textView.setTextSize(20);
                textView.setText(category[groupPosition]);
                ll.addView(textView);
                return ll;
            }
            //取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
            @Override
            public long getGroupId(int groupPosition) {
                // TODO Auto-generated method stub
                return groupPosition;
            }
            //取得分组数
            @Override
            public int getGroupCount() {
                // TODO Auto-generated method stub
                return category.length;
            }
            //取得与给定分组关联的数据
            @Override
            public Object getGroup(int groupPosition) {
                // TODO Auto-generated method stub
                return category[groupPosition];
            }
            //取得指定分组的子元素数.
            @Override
            public int getChildrenCount(int groupPosition) {
                // TODO Auto-generated method stub
                return subcategory[groupPosition].length;
            }
            //取得显示给定分组给定子位置的数据用的视图
            @Override
            public View getChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                //定义一个LinearLayout用于存放ImageView、TextView
                LinearLayout ll=new LinearLayout(ExpandableListViewDemo.this);
                //设置子控件的显示方式为水平
                ll.setOrientation(LinearLayout.HORIZONTAL);
                //定义一个ImageView用于显示列表图片
                ImageView logo=new ImageView(ExpandableListViewDemo.this);
                logo.setPadding(0, 0, 0, 0);
                //设置logo的大小
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(40, 40);
                logo.setLayoutParams(lp);
                logo.setImageResource(sublogos[groupPosition][childPosition]);
                ll.addView(logo);
                TextView textView=getTextView();
                textView.setText(subcategory[groupPosition][childPosition]);
                ll.addView(textView);
                return ll;
            }
            //取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
            @Override
            public long getChildId(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return childPosition;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return subcategory[groupPosition][childPosition];
            }
        };
        list.setAdapter(adapter);
        //为ExpandableListView的子列表单击事件设置监听器
        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(ExpandableListViewDemo.this, "你单击了："
                        +adapter.getChild(groupPosition, childPosition), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.expandable_list_view, menu);
        return true;
    }

}
