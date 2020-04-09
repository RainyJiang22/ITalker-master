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
 * 和群聊天的时候的聊天记录列表
 * 关注的内容一定是我发送给这个群的，或者是别人发送到群的信息
 *
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/16
 */
public class MessageGroupRepository extends BaseDbRepository<Message>
        implements MessageDataSource {

    //聊天的对象Id
    private String receiverId;

    public MessageGroupRepository(String receiverId) {
        super();
        this.receiverId = receiverId;
    }

    @Override
    public void load(SucceedCallback<List<Message>> callback) {
        super.load(callback);

        //TODO
       // sender_id == receiverId and group_id == null
         //or (reciver_id == receiverId)
        //我发送给你的消息
      /*  SQLite.select()
                .from(Message.class)
                .where(OperatorGroup.clause()
                        .and(Message_Table.sender_id.eq(receiverId))
                        .and(Message_Table.group_id.isNull()))
                .or(Message_Table.receiver_id.eq(receiverId))
                .orderBy(Message_Table.createAt,false)
                .limit(30)
                .async()
                .queryListResultCallback(this)
                .execute(); */
    }

    //过滤操作
    @Override
    protected boolean isRequired(Message message) {
       return false;
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Message> tResult) {


        //反转返回的集合
        Collections.reverse(tResult);
        //然后再调度
        super.onListQueryResult(transaction, tResult);
    }
}
