package net.jacky.italker.factory.presenter.group;

import net.jacky.italker.factory.model.Author;
import net.jacky.italker.factory.presenter.BaseContract;

/**
 * 群创建契约
 *
 * @author jacky
 * @version 1.0.0
 * @date 2020/4/7
 */
public interface GroupCreateContract {

    interface Presenter extends BaseContract.Presenter {
        //创建
        void create(String name, String desc, String picture);

        //更改一个Model的选中状态
        void changeSelect(ViewModel model,boolean isSelected);
    }

    interface View extends BaseContract.RecyclerView<Presenter,ViewModel> {

        //创建成功
        void onCreateSucceed();
    }

    class ViewModel {
        //用户信息
      public  Author author;
        //是否选中用户的信息
      public  boolean isSelected;
    }
}
