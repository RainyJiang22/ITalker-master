package com.jacky.lighttalker;

import android.text.TextUtils;

/**
 * created by jiangshiyu
 * date: 2020/1/14
 */
public class Presenter implements IPresenter {
    private IView mView;


    public Presenter(IView view) {
        mView = view;
    }



    @Override
    public void search() {
        //开启界面Loading
        String inputString = mView.getInputString();
        if (TextUtils.isEmpty(inputString)){
            //为空直接返回
            return;
        }

        int hashCode = inputString.hashCode();

        IUserService service = new UserService();
        String serviceResult = service.search(hashCode);

        String result = "Result:" + inputString + "-" + serviceResult;

        //关闭界面Loading
        mView.setResultString(result);
    }
}
