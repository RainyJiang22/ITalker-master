package net.jacky.italker.factory.presenter.message;

import net.jacky.italker.factory.data.message.MessageDataSource;
import net.jacky.italker.factory.model.db.Message;
import net.jacky.italker.factory.presenter.BaseSourcePresenter;

import java.util.List;

/**
 * 聊天Presenter的基础类
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/16
 */
public class ChatPresenter<View extends ChatContract.View>
        extends BaseSourcePresenter<Message,Message, MessageDataSource,View>
        implements ChatContract.Presenter{


    public ChatPresenter(MessageDataSource source, View view) {
        super(source, view);
    }

    @Override
    public void onDataLoaded(List<Message> messages) {

    }

    @Override
    public void pushText(String content) {

    }

    @Override
    public void pushAudio(String path) {

    }

    @Override
    public void pushImages(String[] paths) {

    }

    @Override
    public boolean rePush(Message message) {
        return false;
    }
}
