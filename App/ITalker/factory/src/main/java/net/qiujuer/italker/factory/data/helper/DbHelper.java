package net.qiujuer.italker.factory.data.helper;


import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.Model;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import net.qiujuer.italker.factory.model.db.AppDatabase;
import net.qiujuer.italker.factory.model.db.Group;
import net.qiujuer.italker.factory.model.db.GroupMember;
import net.qiujuer.italker.factory.model.db.Group_Table;
import net.qiujuer.italker.factory.model.db.Message;
import net.qiujuer.italker.factory.model.db.Session;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据库的辅助工具类
 *
 *辅助完成: 增删改
 */
public class DbHelper {

    private static final DbHelper instance;

    static {
        instance =  new DbHelper();
    }

    private DbHelper(){
    }

    /**
     * 观察者集合
     *Class<?>  观察者的表
     * Set<ChangedListener> 每一个表对应的实例的集合
     */
    private final Map<Class<?>,Set<ChangeListener>> changedListeners = new HashMap<>();

    /**
     * 从所有的监听器中，获取一个表中的所有监听者
     * @param modelClass  表对应的Class信息
     * @param <Model>   范型
     * @return Set<ChangeListener>
     */
    private <Model extends BaseModel>  Set<ChangeListener> getListeners(Class<Model> modelClass){
        if (changedListeners.containsKey(modelClass)){
            return changedListeners.get(modelClass);
        }
        return null;
    }

    /**
     * 添加一个监听
     * @param tClass 对某个表关注
     * @param listener 监听者
     * @param <Model>  表的范型
     */
    public static <Model extends BaseModel> void addChangedListener(final Class<Model> tClass
                                                           ,ChangeListener<Model> listener){
        Set<ChangeListener> changeListeners = instance.getListeners(tClass);
        if (changeListeners == null){
            //初始化某一类的容器
            changeListeners = new HashSet<>();

            //添加到总的Map
            instance.changedListeners.put(tClass,changeListeners);
        }

        changeListeners.add(listener);
    }

    /**
     * 删除某一个表的监听器
     * @param tClass 表
     * @param listener 监听器
     * @param <Model> 表的范型
     */
    public static <Model extends BaseModel> void removeChangedListener(final Class<Model> tClass
                                                    ,ChangeListener<Model> listener){
        Set<ChangeListener> changeListeners = instance.getListeners(tClass);
        if (changeListeners == null){
            //容器本身为空，代表根本没有
            return;
        }

        //从容器中删除你这个监听者
        changeListeners.remove(listener);
    }


    //使用泛型
    //限定条件是BaseModel
    /**
     *新增或者修改的统一方法
     * @param tClass   传递一个tClass的信息
     * @param models   这个Class对应的实例的数组
     * @param <Model>  这个实例的泛型
     */
    public static<Model extends BaseModel> void save(final Class<Model> tClass, final Model... models){
        if (models == null || models.length == 0)
            return;

        //当前数据库的管理者
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        //提交一个事务
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                //执行
                ModelAdapter<Model> adapter =  FlowManager.getModelAdapter(tClass);
                //保存
                adapter.saveAll(Arrays.asList(models));
                //唤起通用
                instance.notifySave(tClass,models);
            }
        }).build().execute();
    }


    /***
     * 进行删除数据库的统一封装方法
     * @param tClass   传递一个tClass的信息
     * @param models   这个Class对应的实例的数组
     * @param <Model>  这个实例的泛型，限定条件是BaseModel
     */
    public static<Model extends BaseModel> void delete(final Class<Model> tClass, final Model... models){
        if (models == null || models.length == 0)
            return;

        //当前数据库的管理者
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        //提交一个事务
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                //执行
                ModelAdapter<Model> adapter =  FlowManager.getModelAdapter(tClass);
                //保存
                adapter.deleteAll(Arrays.asList(models));
                //唤起通知
                instance.notifyDelete(tClass,models);
            }
        }).build().execute();
    }


    /**
     * 进行通知调用
     * @param tClass   传递一个Tclass的信息
     * @param models   这个Class对应的实例的数组
     * @param <Model>  这个实例的泛型，限定条件是BaseModel
     */
    @SuppressWarnings("unchecked")
    public final <Model extends BaseModel> void notifySave(final Class<Model> tClass, final Model... models){
           //找到监听器
         final Set<ChangeListener> listeners = getListeners(tClass);
         if (listeners != null && listeners.size() > 0){
         // 通用的通知
             for (ChangeListener<Model> listener : listeners) {
                 listener.onDataSave(models);
             }
         }

         // 列外情况
        if (GroupMember.class.equals(tClass)){
            //群成员变更，需要通知对应群信息更新
           updateGroup((GroupMember[]) models);
        }else if(Message.class.equals(tClass)){
            //消息变化，应该通知会话列表更新
            updateSession((Message[]) models);
        }

    }


    /**
     * 进行通知调用
     * @param tClass   传递一个Tclass的信息
     * @param models   这个Class对应的实例的数组
     * @param <Model>  这个实例的泛型，限定条件是BaseModel
     */
    @SuppressWarnings("unchecked")
    public final <Model extends BaseModel> void notifyDelete(final Class<Model> tClass, final Model... models){

        //找到监听器
        final Set<ChangeListener> listeners = getListeners(tClass);
        if (listeners != null && listeners.size() > 0){
            // 通用的通知
            for (ChangeListener<Model> listener : listeners) {
                listener.onDataDelete(models);
            }
        }

        // 列外情况
        if (GroupMember.class.equals(tClass)){
            //群成员变更，需要通知对应群信息更新
            updateGroup((GroupMember[]) models);
        }else if(Message.class.equals(tClass)){
            //消息变化，应该通知会话列表更新
            updateSession((Message[]) models);
        }

    }


    /**
     * 从成员中找出成员对应的群，并对群进行更新
     * @param members 群成员列表
     */
    private void updateGroup(GroupMember... members){
          //不重复集合
        final  Set<String> groupIds = new HashSet<>();

        for (GroupMember member : members) {
            //添加群Id
            groupIds.add(member.getGroup().getId());
        }

        //异步的数据库查询，并异步的发起二次通知
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                //找到需要通知的群
                List<Group> groups = SQLite.select()
                        .from(Group.class)
                        .where(Group_Table.id.in(groupIds))
                        .queryList();
                //通知调用自己进行一次
                instance.notifySave(Group.class,groups.toArray(new Group[0]));
            }
        }).build().execute();
    }


    /**
     * 从消息列表中，筛选出对应的会话，并对会话进行更新
     * @param messages Message
     */
    private void updateSession(Message... messages){

        //标识一个Session的唯一性
        final Set<Session.Identify> identifies = new HashSet<>();
        for(Message message:messages){
          Session.Identify identify = Session.createSessionIdentify(message);;
          identifies.add(identify);
        }



        //异步的数据库查询，并异步的发起二次通知
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ModelAdapter<Session> adapter = FlowManager.getModelAdapter(Session.class);
                Session[] sessions = new Session[identifies.size()];
                int index = 0;

                for (Session.Identify identify : identifies) {
                    Session session = SessionHelper.findFromLocal(identify.id);

                    if (session == null){
                        //第一次聊天，创建一个你和对方的一个会话
                        session = new Session(identify);
                    }
                    //把会话，刷新到当前Message的最新状态
                    session.refreshToNow();
                    //数据存储
                    adapter.save(session);
                    //添加到集合
                    sessions[index++] = session;
                }
                //通知调用自己进行一次
                instance.notifySave(Session.class,sessions);
            }
        }).build().execute();
    }

    /**
     * 通知监听器
     */
    @SuppressWarnings({"unused", "unchecked"})
    public interface ChangeListener<Data>{
        //通知任意类型的数据保存和删除
        void onDataSave(Data... list);

        void onDataDelete(Data... list);
    }


}
