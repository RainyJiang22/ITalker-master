package net.qiujuer.italker.common.app;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.widget.BaseAdapter;

import net.qiujuer.italker.factory.presenter.BaseContract;

/**
 * @author jacky
 * @version 1.0.0
 */

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends Fragment
                   implements BaseContract.View<Presenter>{

    protected Presenter mPresenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //在界面OnAttach中就触发初始化
        initPresenter();
    }

    /**
     * 初始化Presenter
     * @return Presenter
     */
    protected abstract Presenter initPresenter();


    @Override
    public void showError(int str) {
        //显示错误
        Application.showToast(str);
    }

    @Override
    public void showLoading() {
          //Todo 显示一个LOADING
    }

    @Override
    public void setPresenter(Presenter presenter) {

        //View中赋值给presenter
        mPresenter = presenter;
    }
}
