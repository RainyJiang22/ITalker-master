package net.jacky.italker.factory.presenter.message;

import net.jacky.italker.factory.model.db.Group;
import net.jacky.italker.factory.model.db.Message;
import net.jacky.italker.factory.model.db.User;
import net.jacky.italker.factory.model.db.view.MemberUserModel;
import net.jacky.italker.factory.presenter.BaseContract;

import java.util.List;

/**
 * 聊天契约
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/14
 */
public interface ChatContract {
    interface Presenter extends BaseContract.Presenter{
        //发送文字
        void pushText(String content);
        //发送语音
        void pushAudio(String path);
        //发送图片
        void pushImages(String[] paths);

        //重新发送一个消息，返回是否回调成功
        boolean rePush(Message message);
    }

    interface View<InitModel> extends BaseContract.RecyclerView<Presenter,Message>{
        //初始化的Model
        void onInit(InitModel model);
    }

    //人聊天的界面
    interface  UserView extends View<User>{

    }

    //群聊天的界面
    interface GroupView extends View<Group>{
        //是否是管理员
        void showAdminOption(boolean isAdmin);

        //初始化成员
        void onInitGroupMembers(List<MemberUserModel> members,int moreCount);
    }




}
