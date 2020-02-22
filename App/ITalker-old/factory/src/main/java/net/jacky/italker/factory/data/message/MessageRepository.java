package net.jacky.italker.factory.data.message;

import net.jacky.italker.factory.data.BaseDbRepository;
import net.jacky.italker.factory.model.db.Message;

import java.util.List;

/**
 * 某人聊天的时候的聊天记录列表
 * 关注的内容一定是我发送给这个人的，或者是它发送给我的
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/16
 */
public class MessageRepository extends BaseDbRepository<Message>
        implements MessageDataSource {
    @Override
    protected boolean isRequired(Message message) {
        return false;
    }
}
