package net.qiujuer.italker.factory.presenter.contact;

import android.drm.DrmStore;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.data.helper.UserHelper;
import net.qiujuer.italker.factory.model.card.UserCard;
import net.qiujuer.italker.factory.model.db.AppDatabase;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.model.db.User_Table;
import net.qiujuer.italker.factory.persistence.Account;
import net.qiujuer.italker.factory.presenter.BaseContract;
import net.qiujuer.italker.factory.presenter.BasePresenter;
import net.qiujuer.italker.factory.utils.DiffUiDataCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人列表的契约实现
 * @author Jacky
 * @verson 1.0.0
 */
public class ContactPresenter extends BasePresenter<ContactContract.View>
    implements ContactContract.Presenter{
    public ContactPresenter(ContactContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();

        //加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name,true)
                .limit(100)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<User>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction,
                                                  @NonNull List<User> tResult) {
                      //刷新界面
                        getView().getRecyclerAdapter().replace(tResult);
                        getView().onAdapterDataChanged();

                    }
                }).execute();

        //加载网络数据

        UserHelper.refreshContacts(new DataSource.Callback<List<UserCard>>() {
            @Override
            public void onDataNotAvailable(int strRes) {
                //网络失败，因为本地有数据，不管错误
            }

            @Override
            public void onDataLoaded(final List<UserCard> userCards) {

                //转换成User
                final List<User> users = new ArrayList<>();
                for(UserCard userCard: userCards){
                    users.add(userCard.build());
                }

                //丢到事务中保存数据库
                DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
                definition.beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        FlowManager.getModelAdapter(User.class)
                                .saveAll(users);
                    }
                }).build().execute();


                //网络的数据往往是新的，我们需要直接刷新界面
                List<User> old =  getView().getRecyclerAdapter().getItems();
                //会导致数据顺序全部为新的数据集合
                //getView().getRecyclerAdapter().replace(users);
                diff(old,users);
            }

            /**
             * 1. 关注后虽然存储了数据库，但是没有刷新联系人
             * 2. 如果刷新数据库，或者从网络刷新，最终刷新的时候是全局刷新
             * 3. 加载数据是异步操作，不知道什么时候进行触发
             * 4. 但是本地刷新和网络刷新在显示添加到界面的时候，会有可能冲突：导致数据显示异常j
             */

            private void diff(List<User> oldList, List<User> newList){
                //进行数据对比
                DiffUtil.Callback callback =  new DiffUiDataCallback<>(oldList,newList);
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);


                //在对比完成后进行数据进行赋值
                getView().getRecyclerAdapter().replace(newList);

                //尝试刷新界面
                result.dispatchUpdatesTo(getView().getRecyclerAdapter());
                getView().onAdapterDataChanged();


            }
        });
    }
}
