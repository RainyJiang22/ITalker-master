package net.qiujuer.italker.factory.presenter.search;

import net.qiujuer.italker.factory.presenter.BasePresenter;


/**
 * 搜索群的界面逻辑实现
 * @author jacky
 * @version 1.0.0
 */

public class SearchGroupPresenter extends BasePresenter<SearchContract.GroupView>
   implements SearchContract.Presenter{
    public SearchGroupPresenter(SearchContract.GroupView view) {
        super(view);
    }

    @Override
    public void search(String content) {

    }
}