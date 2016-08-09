package myFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 李松涛 on 2016/5/25 17:00.
 * 描述：自定义fragment的基类
 */
public abstract class BaseFragment extends Fragment {


    public abstract View inintView();

    public abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inintView();

        return view;
    }

    /**
     * fragment的宿主activity创建并且fragment挂载到activity时调用
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
}
