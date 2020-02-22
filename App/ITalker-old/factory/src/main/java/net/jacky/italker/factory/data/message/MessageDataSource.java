package net.jacky.italker.factory.data.message;

import net.jacky.italker.factory.data.DbDataSource;
import net.jacky.italker.factory.model.db.Message;

/**
 * 消息数据源,它的实现是；MessageRepository
 * 关注的是Message表
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/16
 */
public interface MessageDataSource extends DbDataSource<Message> {
}
