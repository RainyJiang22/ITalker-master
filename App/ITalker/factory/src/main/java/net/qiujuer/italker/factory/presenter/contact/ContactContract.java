package net.qiujuer.italker.factory.presenter.contact;

import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.presenter.BaseContract;

import java.util.List;

public interface ContactContract {

    //什么都不需要额外定义，开始就是调用start方法即可
    interface Presenter extends BaseContract.Presenter{
    }

    //都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter,User>{

    }

}
