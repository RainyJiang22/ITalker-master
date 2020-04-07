package net.jacky.italker.factory.presenter.group;

import net.jacky.italker.factory.presenter.BaseRecyclerPresenter;

/**
 * 群创建界面的Presenter
 * @author jacky
 * @version 1.0.0
 * @date 2020/4/7
 */
public class GroupCreatePresenter extends BaseRecyclerPresenter<GroupCreateContract.ViewModel, GroupCreateContract.View>
        implements GroupCreateContract.Presenter {
    public GroupCreatePresenter(GroupCreateContract.View view) {
        super(view);
    }

    @Override
    public void create(String name, String desc, String picture) {

    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {

    }
}
