package net.jacky.italker.factory.data.user;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.jacky.italker.factory.data.BaseDbRepository;
import net.jacky.italker.factory.data.DataSource;
import net.jacky.italker.factory.model.db.User;
import net.jacky.italker.factory.model.db.User_Table;
import net.jacky.italker.factory.persistence.Account;

import java.util.List;

/**
 * 联系人仓库
 *
 * @author jacky
 * @version 1.0.0
 */
public class ContactRepository extends BaseDbRepository<User> implements ContactDataSource {
    @Override
    public void load(DataSource.SucceedCallback<List<User>> callback) {
        super.load(callback);

        // 加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(User user) {
        return user.isFollow() && !user.getId().equals(Account.getUserId());
    }
}
