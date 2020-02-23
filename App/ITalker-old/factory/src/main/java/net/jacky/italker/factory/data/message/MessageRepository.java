package net.jacky.italker.factory.data.message;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.jacky.italker.factory.data.BaseDbRepository;
import net.jacky.italker.factory.model.db.Message;
import net.jacky.italker.factory.model.db.Message_Table;

import java.util.Collections;
import java.util.List;

/**
 * 某人聊天的时候的聊天记录列表
 * 关注的内容一定是我发送给这个人的，或者是它发送给我的
 *
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/16
 */
public class MessageRepository extends BaseDbRepository<Message>
        implements MessageDataSource {

    //聊天的对象Id
    private String receiverId;

    public MessageRepository(String receiverId) {
        super();
        this.receiverId = receiverId;
    }

    @Override
    public void load(SucceedCallback<List<Message>> callback) {
        super.load(callback);

       // sender_id == receiverId and group_id == null
         //or (reciver_id == receiverId)
        //我发送给你的消息
        SQLite.select()
                .from(Message.class)
                .where(OperatorGroup.clause()
                        .and(Message_Table.sender_id.eq(receiverId))
                        .and(Message_Table.group_id.isNull()))
                .or(Message_Table.receiver_id.eq(receiverId))
                .orderBy(Message_Table.createAt,false)
                .limit(30)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    //过滤操作
    @Override
    protected boolean isRequired(Message message) {
        //receiverId如果是发送者，那么Group==null的情况下一定是发送给我的消息
        //如果消息的接收者不为空，那么一定发送给某个人，这个人只能是我或者是某个人
        //如果这个某个人就是reciverId，那么就是我需要关心的消息
        return (receiverId.equals(message.getSender().getId())
                && message.getGroup() == null)
                || (message.getReceiver() != null
                && receiverId.equalsIgnoreCase(message.getReceiver().getId())
        );
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Message> tResult) {


        //反转返回的集合
        Collections.reverse(tResult);
        //然后再调度
        super.onListQueryResult(transaction, tResult);
    }
}
