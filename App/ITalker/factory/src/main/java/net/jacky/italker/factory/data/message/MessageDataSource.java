package net.jacky.italker.factory.data.message;

import net.jacky.italker.factory.data.DbDataSource;
import net.jacky.italker.factory.model.db.Message;

/**
 * 消息的数据源定义，他的实现是：MessageRepository, MessageGroupRepository
 * 关注的对象是Message表
 *
 * @author jacky
 * @version 1.0.0
 */
public interface MessageDataSource extends DbDataSource<Message> {
}
