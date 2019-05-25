package net.jackytallow.web.italker.push.factory;

import net.jackytallow.web.italker.push.bean.db.User;
import net.jackytallow.web.italker.push.utils.Hib;
import net.jackytallow.web.italker.push.utils.TextUtil;
import org.hibernate.Session;

public class UserFactory {

    public static User findByPhone(String phone){
       return Hib.query(session -> (User) session.createQuery("from User where phone=:inPhone")
                 .setParameter("inPhone",phone)
                 .uniqueResult());
    }


    public static User findByName(String name){
        return Hib.query(session -> (User) session.createQuery("from User where name=:name")
                .setParameter("name",name)
                .uniqueResult());
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



        User user = new User();

        user.setName(name);
        user.setPassword(password);
        //账户就是手机号
        user.setPhone(account);


        //进行数据库操作
        //首先创建一个会话
        Session session = Hib.session();
        //开启一个事务
        session.beginTransaction();
        try {
            //保存操作
            session.save(user);
            //提交我们的事务
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {

            //失败情况下需要回滚事务
            session.getTransaction().rollback();
            return null;
        }
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
