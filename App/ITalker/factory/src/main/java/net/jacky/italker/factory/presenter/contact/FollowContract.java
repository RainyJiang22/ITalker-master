package net.jacky.italker.factory.presenter.contact;

import net.jacky.italker.factory.model.card.UserCard;
import net.jacky.italker.factory.presenter.BaseContract;

/**
 * 关注的接口定义
 *
 * @author jacky
 * @version 1.0.0
 */
public interface FollowContract {
    // 任务调度者
    interface Presenter extends BaseContract.Presenter {
        // 关注一个人
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter> {
        // 成功的情况下返回一个用户的信息
        void onFollowSucceed(UserCard userCard);
    }
}
