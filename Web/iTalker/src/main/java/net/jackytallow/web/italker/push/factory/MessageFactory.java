package net.jackytallow.web.italker.push.factory;


import net.jackytallow.web.italker.push.bean.api.message.MessageCreateModel;
import net.jackytallow.web.italker.push.bean.db.Group;
import net.jackytallow.web.italker.push.bean.db.Message;
import net.jackytallow.web.italker.push.bean.db.User;
import net.jackytallow.web.italker.push.utils.Hib;

/**
 *  消息数据存储的类
 * @author Jacky
 * @version 1.0.0
 */
public class MessageFactory {

    //查询某一个消息
    public static Message findById(String id){
     return Hib.query(session ->
             session.get(Message.class,id));
    }


    //添加一条普通消息.
    public static Message add(User sender, User receiver, MessageCreateModel model){
        Message message = new Message(sender,receiver,model);

        return save(message);
    }

    //添加到一条群消息
    public static Message add(User sender, Group group, MessageCreateModel model){
        Message message = new Message(sender,group,model);

        return save(message);
    }


    public static Message save(Message message){
       return Hib.query(session -> {
            session.save(message);

            //写入到数据库中
            session.flush();

            //紧接着从数据库中查询出来
            session.refresh(message);
            return message;
        });
    }
}
