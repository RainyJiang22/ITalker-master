package net.qiujuer.italker.factory.data.message;

import net.qiujuer.italker.factory.model.card.MessageCard;

/**
 * 消息中心，进行消息卡片的消费
 *
 * @author jacky
 * @version 1.0.0
 */
public interface MessageCenter {
    void dispatch(MessageCard... cards);
}
