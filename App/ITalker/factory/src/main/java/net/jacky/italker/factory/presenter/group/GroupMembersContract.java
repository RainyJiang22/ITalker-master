package net.jacky.italker.factory.presenter.group;

import net.jacky.italker.factory.model.db.view.MemberUserModel;
import net.jacky.italker.factory.presenter.BaseContract;

/**
 * 群成员的契约
 *
 * @author jacky
 * @version 1.0.0
 */
public interface GroupMembersContract {
    interface Presenter extends BaseContract.Presenter {
        // 具有一个刷新的方法
        void refresh();
    }

    // 界面
    interface View extends BaseContract.RecyclerView<Presenter, MemberUserModel> {
        //显示添加菜单，只有管理员显示
      //  void showAdminOptions(boolean isAdmin);

        // 获取群的ID
        String getGroupId();
    }
}
