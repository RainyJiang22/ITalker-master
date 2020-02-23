package net.jacky.italker.factory.presenter.message;

import android.support.v7.util.DiffUtil;

import net.jacky.italker.factory.data.helper.MessageHelper;
import net.jacky.italker.factory.data.message.MessageDataSource;
import net.jacky.italker.factory.model.api.message.MsgCreateModel;
import net.jacky.italker.factory.model.db.Message;
import net.jacky.italker.factory.persistence.Account;
import net.jacky.italker.factory.presenter.BaseSourcePresenter;
import net.jacky.italker.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * 聊天Presenter的基础类
 *
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/16
 */
@SuppressWarnings("WeakerAccess")
public class ChatPresenter<View extends ChatContract.View>
        extends BaseSourcePresenter<Message, Message, MessageDataSource, View>
        implements ChatContract.Presenter {

    // 接收者ID 可能是群，也可能是人ID
    protected String mReciverId;
    //区分是人还是群
    protected int mReciverType;

    public ChatPresenter(MessageDataSource source, View view,
                         String reciverId, int reciverType) {
        super(source, view);
        this.mReciverId = reciverId;
        this.mReciverType = reciverType;
    }


    @Override
    public void pushText(String content) {
        //构建一个新的消息
        MsgCreateModel model = new MsgCreateModel.Builder()
                .receiver(mReciverId, mReciverType)
                .content(content, Message.TYPE_STR)
                .build();
        //进行网络发送
        MessageHelper.push(model);
    }

    @Override
    public void pushAudio(String path) {
        //TODO 发送语音
    }

    @Override
    public void pushImages(String[] paths) {
        //TODO 发送图片
    }

    //重新刷新
    @Override
    public boolean rePush(Message message) {
        //确定消息是可以重新发送
        //隐藏判断：Account.isLogin()
        if (Account.getUserId().equalsIgnoreCase(message.getSender().getId())
                && message.getStatus() == Message.STATUS_FAILED) {
            //变为发送状态
            message.setStatus(Message.STATUS_CREATED);
            // 构建发送model
            MsgCreateModel model = MsgCreateModel.buildWithMessage(message);
            MessageHelper.push(model);
            return true;
        }
        return false;
    }

    @Override
    public void onDataLoaded(List<Message> messages) {
        ChatContract.View view = getView();
        if (view == null)
            return;

        //拿到老的数据
        @SuppressWarnings("unchecked")
        List<Message> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Message> callback = new DiffUiDataCallback<>(old, messages);
        //计算差异
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        //进行界面刷新
        refreshData(result, messages);
    }
}
