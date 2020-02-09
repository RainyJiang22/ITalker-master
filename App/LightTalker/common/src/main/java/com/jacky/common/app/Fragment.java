package com.jacky.common.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by jiangshiyu
 * date: 2020/1/14
 * 自定义Fragment
 */
public abstract class Fragment extends androidx.fragment.app.Fragment {

    protected View mRoot;
    protected Unbinder mRootUnBinder;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //初始化参数
        initArgs(getArguments());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            int layId = getContentLayoutId();
            //初始化当前的根布局，但是不是创建的时候就添加到container里面
            View root = inflater.inflate(layId, container, false);
            initWiget(root);
            mRoot = root;
        } else{
            if (mRoot.getParent() != null){
                //把当前Root从其父控件移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }

        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //当前View创建完成后初始化数据
        initData();
    }

    /**
     * 初始化控件
     */
    private void initWiget(View root) {
        mRootUnBinder = ButterKnife.bind(this,root);
    }


    /**
     * 初始化相关参数
     */
    private void initArgs(Bundle arguments) {
    }

    /**
     * 得到当前界面的资源文件Id
     *
     * @return 资源文件
     */
    @LayoutRes
    protected abstract int getContentLayoutId();


    /**
     *初始化数据
     */
    protected void initData(){

    }

    /**
     * 返回按键触发时调用
     *
     * @return 返回True代表我已处理返回逻辑，Activity不用自己finish。
     * 返回False代表我没有处理逻辑，Activity自己走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }
}
