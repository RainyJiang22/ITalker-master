package net.jacky.italker.push.frags.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.jacky.italker.factory.model.db.Group;
import net.jacky.italker.factory.model.db.view.MemberUserModel;
import net.jacky.italker.factory.presenter.message.ChatContract;
import net.jacky.italker.factory.presenter.message.ChatGroupPresenter;
import net.qiujuer.italker.push.R;

import java.util.List;

/**
 * 群聊天界面
 */
public class ChatGroupFragment extends ChatFragment<Group>
   implements ChatContract.GroupView {


    public ChatGroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.lay_chat_header_group;
    }



    @Override
    protected ChatContract.Presenter initPresenter() {
        return new ChatGroupPresenter(this,mReciverId);
    }

    @Override
    public void onInit(Group group) {

    }



    @Override
    public void onInitGroupMembers(List<MemberUserModel> members, int moreCount) {

    }

    @Override
    public void showAdminOption(boolean isAdmin) {

    }


}
