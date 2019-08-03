package net.qiujuer.italker.factory.data.message;


import net.qiujuer.italker.factory.model.card.MessageCard;
import net.qiujuer.italker.factory.model.card.UserCard;

/**
 *消息中心，进行消息卡片的消费
 */
public interface MessageCenter {
    void dispatch(MessageCard... cards);
}
