package net.jackytallow.web.italker.push.factory;

import net.jackytallow.web.italker.push.bean.db.User;
import net.jackytallow.web.italker.push.utils.Hib;
import net.jackytallow.web.italker.push.utils.TextUtil;
import org.hibernate.Session;

public class UserFactory {

    public static User findByPhone(String phone) {
        return Hib.query(session -> (User) session
                .createQuery("from User where phone=:inPhone")
                .setParameter("inPhone", phone)
                .uniqueResult());
    }

    public static User findByName(String name) {
        return Hib.query(session -> (User) session
                .createQuery("from User where name=:name")
                .setParameter("name", name)
                .uniqueResult());
    }



    public static User register(String account,String password,String name){
        //去除账户中的首位空格
        account = account.trim();
        //处理密码
        password = encodePassword(password);

        User user = new User();

        user.setName(name);
        user.setPassword(password);
        //账户就是手机号
        user.setPhone(account);

        //进行数据库操作
        //首先建立一个会话
        Session session = Hib.session();
        //开启一个事物
        session.beginTransaction();
        try{
            //保存操作
            session.save(user);
            //提交我们的事物
            session.getTransaction().commit();
            return user;
        }catch (Exception e){
            //失败情况下需要回滚事物
            session.getTransaction().rollback();
            return null;
        }
    }

    private static String encodePassword(String password) {
        //密码去除首位空格
        password = password.trim();
        //进行MDS非对称加密，加盐会更安全，盐也需要存储
        password = TextUtil.getMD5(password);
        //再进行一次对称的Base64加密，当然可以采取加盐的方案
        return TextUtil.encodeBase64(password);
    }

}
