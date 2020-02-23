package net.jacky.italker.factory.presenter.message;

import net.jacky.italker.factory.model.db.Session;
import net.jacky.italker.factory.model.db.User;
import net.jacky.italker.factory.presenter.BaseContract;
import net.jacky.italker.factory.presenter.contact.ContactContract;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/23
 * 最近会话列表契约
 */
public interface SessionContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<SessionContract.Presenter, Session> {

    }
}
