package net.jacky.italker.factory.presenter.message;

import net.jacky.italker.factory.data.helper.GroupHelper;
import net.jacky.italker.factory.data.helper.UserHelper;
import net.jacky.italker.factory.data.message.MessageGroupRepository;
import net.jacky.italker.factory.model.db.Group;
import net.jacky.italker.factory.model.db.Message;
import net.jacky.italker.factory.model.db.view.MemberUserModel;
import net.jacky.italker.factory.persistence.Account;

import java.util.List;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/16
 */
public class ChatGroupPresenter extends ChatPresenter<ChatContract.GroupView>
        implements ChatContract.Presenter {

    public ChatGroupPresenter(ChatContract.GroupView view, String reciverId) {
        //数据源，View，接收者，接收者的类型
        super(new MessageGroupRepository(reciverId), view, reciverId, Message.RECEIVER_TYPE_GROUP);
    }

    @Override
    public void start() {
        super.start();

        //查找群的信息
        Group group = GroupHelper.findFromLocal(mReciverId);
        if (group != null) {
            //初始化操作
            ChatContract.GroupView view = getView();

            boolean isAdmin = Account.getUserId().equalsIgnoreCase(group.getOwner().getId());
            view.showAdminOption(isAdmin);

            //基础信息初始化
            view.onInit(group);

            //成员初始化
            List<MemberUserModel> models = group.getLatelyGroupMembers();
            final long memberCount = group.getGroupMemberCount();

            //没有显示成员的数量
            long moreCount = memberCount - models.size();
            view.onInitGroupMembers(models, moreCount);
        }
    }

}
