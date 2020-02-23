package net.jacky.italker.factory.data.message;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.jacky.italker.factory.data.BaseDbRepository;
import net.jacky.italker.factory.model.db.Session;
import net.jacky.italker.factory.model.db.Session_Table;

import java.util.Collections;
import java.util.List;

/**
 * 最近聊天仓库，是对SessionDataSource的实现
 *
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/23
 */
public class SessionRepository extends BaseDbRepository<Session>
        implements SessionDataSource {

    @Override
    public void load(SucceedCallback<List<Session>> callback) {
        super.load(callback);
        //数据库查询
        SQLite.select()
                .from(Session.class)
                .orderBy(Session_Table.modifyAt, false)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();

        //复写insert之后会导致问题
    }

    @Override
    protected boolean isRequired(Session session) {
        //所有的会话都需要，不需要过滤
        return true;
    }

    @Override
    protected void insert(Session session) {
       //复写方法，让新的数据加载到头部
        dataList.addFirst(session);
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Session> tResult) {
        //复写数据库回来的方法
        //进行一次反转
        Collections.reverse(tResult);

        super.onListQueryResult(transaction, tResult);
    }
}
