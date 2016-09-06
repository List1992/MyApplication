package myFragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 李松涛 on 2016/5/25 17:28.
 * 描述：
 */
public class FindFragment extends BaseFragment {
    @BindView(R.id.btStart)
    Button mBtStart;
    @BindView(R.id.pbPic)
    ProgressBar mPbPic;
    @BindView(R.id.txtProgress)
    TextView mTxtProgress;

    Handler handler = new Handler();

    @Override
    public View inintView() {


        View view = getActivity().getLayoutInflater().inflate(R.layout.find_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
    }

    private int c;

    @OnClick(R.id.btStart)
    public void onclick() {

//        if (mBtStart.isSelected()) {
//
//            mBtStart.setSelected(false);
//            new Thread(new Runnable() {//创建并启动线程，使用线程执行模拟的任务
//                public void run() {
//                    less();
//                }
//            }).start();
//        } else {
//            mBtStart.setSelected(true);
//            new Thread(new Runnable() {//创建并启动线程，使用线程执行模拟的任务
//                public void run() {
//                    add();
//                }
//            }).start();
//        }
        add1();
        mBtStart.setEnabled(false);
    }

    private void add1() {

        mPbPic.setMax(100);
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    //休眠500ms
                    SystemClock.sleep(200);
                    //进度条每次增加一个单位
                    mPbPic.incrementProgressBy(1);

                    //达到最大值时，开始减少
                    if (mPbPic.getMax() == mPbPic.getProgress()) {


                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                for (int i = 0; i < 100; i++) {
                                        //以后每次休眠200ms
                                        SystemClock.sleep(200);

                                    //进度条每次增加一个单位
                                    mPbPic.incrementProgressBy(-1);

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (mPbPic.getProgress() > -1 && mPbPic.getProgress() <= 20) {

                                                mTxtProgress.setTextColor(Color.RED);
                                            } else if (mPbPic.getProgress() > 20 && mPbPic.getProgress() <= 70) {
                                                mTxtProgress.setTextColor(Color.BLUE);
                                            } else {

                                                mTxtProgress.setTextColor(Color.GREEN);
                                            }


                                            mTxtProgress.setText(mPbPic.getProgress() + "%");//显示完成的进度
                                        }
                                    });
                                }

                            }
                        }).start();


                    }
                    //在主线程更新ui
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mPbPic.getProgress() > -1 && mPbPic.getProgress() <= 20) {

                                mTxtProgress.setTextColor(Color.RED);
                            } else if (mPbPic.getProgress() > 20 && mPbPic.getProgress() <= 70) {
                                mTxtProgress.setTextColor(Color.BLUE);
                            } else {

                                mTxtProgress.setTextColor(Color.GREEN);
                            }


                            mTxtProgress.setText(mPbPic.getProgress() + "%");//显示完成的进度
                        }
                    });

                }
            }
        }).start();
    }

    private void less() {
        for (int i = 100; i > -1; i--) { //循环100遍
            c = i;

            try {
                handler.post(new Runnable() { //更新界面的数据
                    public void run() {
                        //mPbPic.incrementProgressBy(1);//增加进度
                        mPbPic.setProgress(c);//设置进度
                        mTxtProgress.setText(mPbPic.getProgress() + "%");//显示完成的进度
                        if (c > -1 && c <= 20) {

                            mTxtProgress.setTextColor(Color.RED);
                        } else if (c > 20 && c <= 70) {
                            mTxtProgress.setTextColor(Color.BLUE);
                        } else {

                            mTxtProgress.setTextColor(Color.GREEN);
                        }

                        //当达到最小值时，开始增加
                        if (c == 0) {

                            try {

                                Thread.sleep(500);
                                new Thread(new Runnable() {//创建并启动线程，使用线程执行模拟的任务
                                    public void run() {
                                        add();
                                    }
                                }).start();
                            } catch (InterruptedException e) {


                            }

                        }
                    }
                });
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }

    private void add() {
        for (int i = 1; i < 101; i++) { //循环100遍
            c = i;

            try {
                handler.post(new Runnable() { //更新界面的数据
                    public void run() {
                        //mPbPic.incrementProgressBy(1);//增加进度

                        mPbPic.setProgress(c);//设置进度
                        mTxtProgress.setText(mPbPic.getProgress() + "%");//显示完成的进度
                        if (c > -1 && c <= 20) {

                            mTxtProgress.setTextColor(Color.RED);
                        } else if (c > 20 && c <= 70) {
                            mTxtProgress.setTextColor(Color.BLUE);
                        } else {

                            mTxtProgress.setTextColor(Color.GREEN);
                        }

                        //当达到最大值时，开始减少
                        if (c == 100) {
                            try {
                                Thread.sleep(500);

                                new Thread(new Runnable() {//创建并启动线程，使用线程执行模拟的任务
                                    public void run() {
                                        less();
                                    }
                                }).start();
                            } catch (InterruptedException e) {


                            }


                        }
                    }
                });
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }
}
