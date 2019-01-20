package com.example.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class Fragment extends android.support.v4.app.Fragment {
   protected  View mRoot;
   protected Unbinder mRootUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRoot ==null){
            int layId = getContentLayoutId();
            //初始化当前的根布局，但是不在创建时就添加到container里边
            View root = inflater.inflate(layId,container,false);
            initWidget(root);
            mRoot = root;
         }else{
            if (mRoot.getParent()!=null){
               //把当前Root从其父控件移除
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }


       return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //当View创建完成后初始化数据
        initData();
    }

    /**
     * 得到当前界面的资源文件Id
     * @return 资源文件id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     * @param root
     */
    protected void initWidget(View root){
      mRootUnbinder =  ButterKnife.bind(this,root);
    }

    /**
     * 初始化数据
     */
    protected void initData(){

    }

    /**
     * 返回按键触发时候调用
     * @return 返回true代表我已经处理返回逻辑，activity不用自己finish
     * 返回False代表我没有处理逻辑，Activity自己走自己的逻辑
     */
    public boolean onBackPressed(){
        return false;
    }
}
