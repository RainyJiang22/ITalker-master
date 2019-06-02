package net.jackytallow.web.italker.push.factory;

import com.google.common.base.Strings;
import net.jackytallow.web.italker.push.bean.db.User;
import net.jackytallow.web.italker.push.utils.Hib;
import net.jackytallow.web.italker.push.utils.TextUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.UUID;

public class UserFactory {

    //通过Token找到User
    //token信息只能自己查询，自己使用
    public static User findByToken(String token){
        return Hib.query(session -> (User) session.createQuery("from User where token=:token")
                .setParameter("token",token)
                .uniqueResult());
    }



    //通过Phone找到User
    public static User findByPhone(String phone){
       return Hib.query(session -> (User) session.createQuery("from User where phone=:inPhone")
                 .setParameter("inPhone",phone)
                 .uniqueResult());
    }


    //通过Name找到User
    public static User findByName(String name){
        return Hib.query(session -> (User) session.createQuery("from User where name=:name")
                .setParameter("name",name)
                .uniqueResult());
    }


    /**
     * 更新用户信息到数据库
     *
     * @param user
     * @return
     */
    public static User update(User user){
       return Hib.query(session -> {
          session.saveOrUpdate(user);
          return user;
       });
    }


    /**
     * 给当前的账户绑定PushId
     *
     * @param user 自己的User
     * @param pushId 自己设备的PushId
     * @return
     */
    public static User bindPushId(User user, String pushId){

        if (Strings.isNullOrEmpty(pushId))
            return null;

        // 第一步，查询是否有其他账户绑定了这个设备
        // 取消绑定，避免推送混乱
        // 查询的列表不能包括自己
        Hib.queryOnly(session -> {
                    @SuppressWarnings("unchecked")
                    List<User> userList = (List<User>) session
                            .createQuery("from User where lower(pushId)=:pushId and id!=:userId")
                            .setParameter("pushId", pushId.toLowerCase())
                            .setParameter("userId", user.getId())
                            .list();

                    for(User u : userList){
                        //更新为null
                        u.setPushId(null);
                        session.saveOrUpdate(u);
                    }
                });
        if (pushId.equalsIgnoreCase(user.getPushId())) {
            // 如果当前需要绑定的设备Id，之前已经绑定过了
            // 那么不需要额外绑定
            return user;
        } else {
            // 如果当前账户之前的设备Id，和需要绑定的不同
            // 那么需要单点登录，让之前的设备退出账户，
            // 给之前的设备推送一条退出消息
            if (Strings.isNullOrEmpty(user.getPushId())) {
                // TODO 推送一个退出消息
            }

            // 更新新的设备Id
            user.setPushId(pushId);
            return update(user);
        }
      }



    /**
     * 使用账户和密码进行登录
     */
    public static User login(String account, String password) {
        final String accountStr = account.trim();
        // 把原文进行同样的处理，然后才能匹配
        final String encodePassword = encodePassword(password);

        // 寻找账户
        User user = Hib.query(session -> (User) session
                .createQuery("from User where phone=:phone and password=:password")
                .setParameter("phone", accountStr)
                .setParameter("password", encodePassword)
                .uniqueResult());

        if (user != null) {
            // 对User进行登录操作，更新Token
            user = login(user);
        }
        return user;

    }



    /**
     * 用户注册
     * 注册的操作需要写入数据并返回数据库的user
     * @param account 账户
     * @param password  密码
     * @param name  用户名
     * @return
     */
    public static User register(String account, String password,String name) {
        //去除账户中首位空格
        account = account.trim();
        //处理密码
        password = encodePassword(password);



        User user = createUser(account,password,name);
        if (user != null){
            user =  login(user);
        }

        return user;

    }



    /**
     * 注册部分的新建用户逻辑
     *
     * @param account  手机号
     * @param password 加密后的密码
     * @param name     用户名
     * @return 返回一个用户
     */
    private static User createUser(String account, String password, String name) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        // 账户就是手机号
        user.setPhone(account);

        // 数据库存储
        return Hib.query(session -> {
            session.save(user);
            return user;
        });
    }



    /**
     * 把一个User进行登录操作
     * 本质上是对Token进行操作
     *
     * @param user User
     * @return User
     */
    private static User login(User user) {
        // 使用一个随机的UUID值充当Token
        String newToken = UUID.randomUUID().toString();
        // 进行一次Base64格式化
        newToken = TextUtil.encodeBase64(newToken);
        user.setToken(newToken);

        return update(user);
    }



    public static String encodePassword(String password){

        //对密码进行首位空格
        password = password.trim();

        //进行非对称MD5加密
        password = TextUtil.getMD5(password);

        //再一次进行对称Base64加密，可以进行加盐的操作，让之更安全
        return TextUtil.encodeBase64(password);
    }
}
