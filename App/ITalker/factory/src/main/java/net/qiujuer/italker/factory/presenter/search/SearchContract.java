package net.qiujuer.italker.factory.presenter.search;


import net.qiujuer.italker.factory.model.card.GroupCard;
import net.qiujuer.italker.factory.model.card.UserCard;
import net.qiujuer.italker.factory.presenter.BaseContract;

import java.util.List;

/**
 * 搜索功能的契约
 * @author jacky
 * @version 1.0
 */
public interface SearchContract {

    interface Presenter extends BaseContract.Presenter{
        //搜索内容
        void search(String content);
    }

    //搜索人的界面
    interface UserView extends BaseContract.View<Presenter>{
        void onSearchDone(List<UserCard> userCards);
    }

    //搜索群的界面
    interface GroupView extends BaseContract.View<Presenter>{
        void onSearchDone(List<GroupCard> groupCards);
    }

}
