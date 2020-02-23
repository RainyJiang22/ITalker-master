package net.jacky.italker.factory.presenter.message;

import android.support.v7.util.DiffUtil;

import net.jacky.italker.factory.data.message.SessionDataSource;
import net.jacky.italker.factory.data.message.SessionRepository;
import net.jacky.italker.factory.model.db.Session;
import net.jacky.italker.factory.presenter.BaseSourcePresenter;
import net.jacky.italker.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/23
 * 最近聊天列表的Presenter
 */
public class SessionPresenter extends BaseSourcePresenter<Session,Session,
        SessionDataSource,SessionContract.View> implements SessionContract.Presenter{
    //构造方法
    public SessionPresenter(SessionContract.View view) {
        super(new SessionRepository(), view);
    }

    @Override
    public void onDataLoaded(List<Session> sessions) {
        SessionContract.View view = getView();
        if (view==null)
            return;
        //差异对比
        List<Session> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Session> callback = new DiffUiDataCallback<>(old,sessions);
        //计算差异
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        //刷新界面
        refreshData(result,sessions);
    }
}
