package net.qiujuer.web.italker.push.factory;

import com.google.common.base.Strings;
import net.qiujuer.web.italker.push.bean.api.base.PushModel;
import net.qiujuer.web.italker.push.bean.card.MessageCard;
import net.qiujuer.web.italker.push.bean.db.Message;
import net.qiujuer.web.italker.push.bean.db.PushHistory;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.utils.Hib;
import net.qiujuer.web.italker.push.utils.PushDispatcher;
import net.qiujuer.web.italker.push.utils.TextUtil;

/**
 * 消息存储与处理的工具类
 * @author Jacky
 * @version 1.0.0
 */
public class PushFactory {

    //发送一条消息，并在当前的发送历史记录中存储记录
    public static void pushNewMessage(User sender, Message message){
       if (sender == null||message==null)
           return;

       //消息卡片用于发送
       MessageCard card = new MessageCard(message);
        //要推送的字符串
        String entity = TextUtil.toJson(card);
       //发送者
        PushDispatcher dispatcher = new PushDispatcher();


       if (message.getGroup() == null
           && Strings.isNullOrEmpty(message.getGroupId())){
           //给朋友发送消息

           User receiver = UserFactory.findById(message.getReceiverId());
           if (receiver == null)
               return;

           //历史记录表字段建立
           PushHistory history = new PushHistory();
           //普通消息类型
           history.setEntityType(PushModel.ENTITY_TYPE_MESSAGE);
           history.setEntity(entity);
           history.setReceiver(receiver);
           //接收者当前的设备推送Id
           history.setReceiverPushId(receiver.getPushId());


           //推送的真实model
           PushModel pushModel = new PushModel();
           //每一条历史记录都是独立的，可以单独的发送
           pushModel.add(history.getEntityType(),history.getEntity());

            //把需要发送的，丢给发送者进行发送
           dispatcher.add(receiver,pushModel);
           //保存到数据库
           Hib.queryOnly(session -> session.save(history));

       }else{
           //给群成员发送消息
       }


       //发送者进行真实的提交
        dispatcher.submit();
    }
}
