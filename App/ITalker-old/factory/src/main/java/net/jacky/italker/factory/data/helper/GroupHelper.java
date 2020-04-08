package net.jacky.italker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.jacky.italker.factory.Factory;
import net.jacky.italker.factory.data.DataSource;
import net.jacky.italker.factory.model.api.RspModel;
import net.jacky.italker.factory.model.api.group.GroupCreateModel;
import net.jacky.italker.factory.model.card.GroupCard;
import net.jacky.italker.factory.model.card.UserCard;
import net.jacky.italker.factory.model.db.Group;
import net.jacky.italker.factory.model.db.Group_Table;
import net.jacky.italker.factory.model.db.User;
import net.jacky.italker.factory.net.Network;
import net.jacky.italker.factory.net.RemoteService;
import net.qiujuer.italker.factory.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 对群的一个简单的辅助工具类
 *
 * @author jacky
 * @version 1.0.0
 */
public class GroupHelper {
    public static Group find(String groupId) {
        //查询群的信息，先本地，后网络
        Group group = findFromLocal(groupId);
        if (group == null)
            group = findFromNet(groupId);
        return group;
    }

    public static Group findFromLocal(String groupId) {
        //本地找群信息
        return SQLite.select()
                .from(Group.class)
                .where(Group_Table.id.eq(groupId))
                .querySingle();
    }


    //从网络找群
    public static Group findFromNet(String id) {
        RemoteService remoteService = Network.remote();
        try {
            Response<RspModel<GroupCard>> response = remoteService.groupFind(id).execute();
            GroupCard card = response.body().getResult();
            if (card != null) {
                // 数据库的存储并通知
                Factory.getGroupCenter().dispatch(card);
                User user = UserHelper.search(card.getOwnerId());
                if (user != null) {
                    return card.build(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //群的创建
    public static void create(GroupCreateModel model, final DataSource.Callback<GroupCard> callback) {
        RemoteService service = Network.remote();
        service.groupCreate(model)
                .enqueue(new Callback<RspModel<GroupCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<GroupCard>> call, Response<RspModel<GroupCard>> response) {
                        RspModel<GroupCard> rspModel = response.body();
                        if (rspModel.success()) {
                            GroupCard groupCard = rspModel.getResult();
                            // 唤起进行保存的操作
                            Factory.getGroupCenter().dispatch(groupCard);
                            // 返回数据
                            callback.onDataLoaded(groupCard);
                        } else {
                            Factory.decodeRspCode(rspModel, callback);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<GroupCard>> call, Throwable throwable) {
                        callback.onDataNotAvailable(R.string.data_network_error);
                    }
                });
    }
}
