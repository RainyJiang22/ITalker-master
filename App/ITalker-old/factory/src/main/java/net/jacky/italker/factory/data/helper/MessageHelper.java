package net.jacky.italker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.jacky.italker.factory.Factory;
import net.jacky.italker.factory.model.api.RspModel;
import net.jacky.italker.factory.model.api.message.MsgCreateModel;
import net.jacky.italker.factory.model.card.MessageCard;
import net.jacky.italker.factory.model.card.UserCard;
import net.jacky.italker.factory.model.db.Message;
import net.jacky.italker.factory.model.db.Message_Table;
import net.jacky.italker.factory.net.Network;
import net.jacky.italker.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 消息工具类
 *
 * @author jacky
 * @version 1.0.0
 */
public class MessageHelper {
    // 从本地找消息
    public static Message findFromLocal(String id) {
        return SQLite.select()
                .from(Message.class)
                .where(Message_Table.id.eq(id))
                .querySingle();
    }

    //发送是异步进行的
    public static void push(final MsgCreateModel model) {
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                //成功状态：如果是一个已经发送过的消息，则不能重新发送
                //正在发送状态：如果是一个消息正在发送，则不能重新发送
                //消息检查
                Message message = findFromLocal(model.getId());
                if (message != null && message.getStatus() != Message.STATUS_FAILED)
                    return;

                //TODO 如果是文件类型（语音，图片，文件），需要先上传后才发送

                //在发送的时候，需要通知界面更新状态，Card
                final MessageCard card = model.buildCard();
                Factory.getMessageCenter().dispatch(card);

                //直接发送,进行网络调度
                RemoteService service = Network.remote();
                service.msgPush(model).enqueue(new Callback<RspModel<MessageCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<MessageCard>> call, Response<RspModel<MessageCard>> response) {
                        //正常成功的状态下
                        RspModel<MessageCard> rspModel = response.body();
                        if (rspModel != null && rspModel.success()) {
                            //回来的card
                            MessageCard rspCard = rspModel.getResult();
                            //如果新的卡片不能为空
                            if (rspCard != null) {
                                //成功的调度
                                Factory.getMessageCenter().dispatch(rspCard);
                            }
                        } else {
                            //检查账户是否异常
                            Factory.decodeRspCode(rspModel, null);
                            //走失败流程
                            onFailure(call, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<MessageCard>> call, Throwable throwable) {
                        //通知失败
                        card.setStatus(Message.STATUS_FAILED);
                        Factory.getMessageCenter().dispatch(card);
                    }
                });
            }
        });

    }
}
