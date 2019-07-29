package net.qiujuer.italker.factory.data.helper;


import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import net.qiujuer.italker.factory.model.db.AppDatabase;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.utils.CollectionUtil;

import java.util.Arrays;
import java.util.List;

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
    public final <Model extends BaseModel> void notifySave(final Class<Model> tClass, final Model... models){
           //TODO
    }


    /**
     * 进行通知调用
     * @param tClass   传递一个Tclass的信息
     * @param models   这个Class对应的实例的数组
     * @param <Model>  这个实例的泛型，限定条件是BaseModel
     */
    public final <Model extends BaseModel> void notifyDelete(final Class<Model> tClass, final Model... models){
        //TODO
    }

}
