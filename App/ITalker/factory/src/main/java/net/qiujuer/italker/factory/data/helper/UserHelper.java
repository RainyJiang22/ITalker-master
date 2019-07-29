package net.qiujuer.italker.factory.data.helper;

import android.view.Display;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.RspModel;
import net.qiujuer.italker.factory.model.api.user.UserUpdateModel;
import net.qiujuer.italker.factory.model.card.UserCard;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.model.db.User_Table;
import net.qiujuer.italker.factory.net.Network;
import net.qiujuer.italker.factory.net.RemoteService;
import net.qiujuer.italker.factory.presenter.contact.FollowPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author jacky
 * @version 1.0.0
 */
public class UserHelper {
    // 更新用户信息的操作，异步的
        public static void update(UserUpdateModel model, final DataSource.Callback<UserCard> callback) {
            // 调用Retrofit对我们的网络请求接口做代理
            RemoteService service = Network.remote();
            // 得到一个Call
            Call<RspModel<UserCard>> call = service.userUpdate(model);
            // 网络请求
            call.enqueue(new Callback<RspModel<UserCard>>() {
                @Override
                public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                    RspModel<UserCard> rspModel = response.body();
                    if (rspModel.success()) {
                        UserCard userCard = rspModel.getResult();
                        // 数据库的存储操作，需要把UserCard转换为User
                        // 保存用户信息
                        User user = userCard.build();
                        //异步统一的保存
                        DbHelper.save(User.class,user);
                        // user.save();
                        // 返回成功
                        callback.onDataLoaded(userCard);
                    } else {
                        // 错误情况下进行错误分配
                        Factory.decodeRspCode(rspModel, callback);
                    }
                }

                @Override
                public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                    //网络错误
                    callback.onDataNotAvailable(R.string.data_network_error);
                }
            });
    }


    // 搜索的方法
    public static Call search(String name, final DataSource.Callback<List<UserCard>> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        // 得到一个Call
        Call<RspModel<List<UserCard>>> call = service.userSearch(name);


        //网络请求
        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                 RspModel<List<UserCard>> rspModel = response.body();
                 if (rspModel.success()){
                    //返回数据
                     callback.onDataLoaded(rspModel.getResult());
                 }else{
                     // 错误情况下进行错误分配
                     Factory.decodeRspCode(rspModel, callback);
                 }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                //网络错误
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        //返回当前的call,把当前的调度者返回
        return call;
    }

    //关注的网络请求
    public static void follow(String id, final DataSource.Callback<UserCard> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        // 得到一个Call
        Call<RspModel<UserCard>> call = service.userFollow(id);


        //网络请求
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()){
                    //保存到本地数据库
                  UserCard userCard = rspModel.getResult();
                   User user = userCard.build();
                  //保存和通知都在里面
                   DbHelper.save(User.class,user);
                   //user.save();

                    //返回数据
                    callback.onDataLoaded(rspModel.getResult());
                }else{
                    // 错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                //网络错误
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
        }



    // 刷新联系人的操作
    public static void refreshContacts(final DataSource.Callback<List<UserCard>> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();

        //网络请求
        service.userContacts()
                .enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()){
                    //返回数据
                    callback.onDataLoaded(rspModel.getResult());
                }else{
                    // 错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                //网络错误
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

    }


    // 从本地查询一个用户的信息
    public static User findFromLocal(String id) {
        return SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(id))
                .querySingle();
    }

    //从网络查询某用户的通知
    public static User findFromNet(String id) {

        RemoteService remoteService = Network.remote();
        try {
            Response<RspModel<UserCard>> response = remoteService.userFind(id).execute();
            UserCard card = response.body().getResult();
            if (card != null) {
                //数据库的存储并且通知
                User user = card.build();
                DbHelper.save(User.class,user);

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 搜索一个用户，优先本地缓存，
     * 没有用然后再从网络拉取
     */
    public static User search(String id) {
        User user = findFromLocal(id);
        if (user == null) {
            return findFromNet(id);
        }
        return user;
    }

    /**
     * 搜索一个用户，优先网络查询
     * 没有用然后再从本地缓存拉取
     */
    public static User searchFirstOfNet(String id) {
        User user = findFromNet(id);
        if (user == null) {
            return findFromLocal(id);
        }
        return user;
    }




}
