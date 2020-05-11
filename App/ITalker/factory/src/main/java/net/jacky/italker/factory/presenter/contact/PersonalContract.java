package net.jacky.italker.factory.presenter.contact;

import net.jacky.italker.factory.model.card.UserCard;
import net.jacky.italker.factory.model.db.User;
import net.jacky.italker.factory.presenter.BaseContract;

/**
 * @author jacky
 * @version 1.0.0
 */
public interface PersonalContract {
    interface Presenter extends BaseContract.Presenter {
        // 获取用户信息
        User getUserPersonal();

        //进行关注一个人
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter> {
        String getUserId();

        // 加载数据完成
        void onLoadDone(User user);

        // 是否发起聊天
        void allowSayHello(boolean isAllow);

        // 是否退出登陆,只有用户本人才能退出登陆
        void allowLogOut(boolean isSelf);

        // 设置关注状态
        void setFollowStatus(boolean isFollow);

        // 成功的情况下返回一个用户的信息
        void onFollowSucceed(UserCard userCard);
    }
}
