package net.jacky.italker.factory.presenter.contact;

import net.jacky.italker.factory.model.db.User;
import net.jacky.italker.factory.presenter.BaseContract;

/**
 * @author jacky
 * @version 1.0.0
 */
public interface ContactContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, User> {

    }
}
