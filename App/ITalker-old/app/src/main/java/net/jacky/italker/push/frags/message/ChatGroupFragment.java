package net.jacky.italker.push.frags.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.jacky.italker.factory.model.db.Group;
import net.jacky.italker.factory.model.db.view.MemberUserModel;
import net.jacky.italker.factory.presenter.message.ChatContract;
import net.jacky.italker.factory.presenter.message.ChatGroupPresenter;
import net.jacky.italker.push.activities.PersonalActivity;
import net.qiujuer.italker.push.R;

import java.util.List;

import butterknife.BindView;

/**
 * 群聊天界面
 */
public class ChatGroupFragment extends ChatFragment<Group>
        implements ChatContract.GroupView {


    @BindView(R.id.im_header)
    ImageView mHeader;

    @BindView(R.id.lay_members)
    LinearLayout mLayMembers;

    @BindView(R.id.txt_member_more)
    TextView mMemberMore;

    public ChatGroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.lay_chat_header_group;
    }


    @Override
    protected ChatContract.Presenter initPresenter() {
        return new ChatGroupPresenter(this, mReciverId);
    }

    @Override
    public void onInit(Group group) {
        //标题
        mCollapsingLayout.setTitle(group.getName());

        //背景图片
        Glide.with(this)
                .load(group.getPicture())
                .centerCrop()
                .placeholder(R.drawable.default_banner_group)
                .into(mHeader);
    }


    @Override
    public void onInitGroupMembers(List<MemberUserModel> members, long moreCount) {

        if (members == null || members.size() == 0) {
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (final MemberUserModel member : members) {
            //添加成员头像
            ImageView p = (ImageView) inflater.inflate(R.layout.lay_chat_group_portrait, mLayMembers, false);
            mLayMembers.addView(p, 0);

            Glide.with(this)
                    .load(member.portrait)
                    .placeholder(R.drawable.default_portrait)
                    .centerCrop()
                    .dontAnimate()
                    .into(p);
            //个人信息界面查看
            p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonalActivity.show(getContext(), member.userId);
                }
            });
        }

        //更多的按钮
        if (moreCount > 0) {
            mMemberMore.setText(String.format("+%s",moreCount));
            mMemberMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 显示成员列表
                }
            });
        }else{
            //隐藏
            mMemberMore.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAdminOption(boolean isAdmin) {
        if (isAdmin) {
            mToolbar.inflateMenu(R.menu.chat_group);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.action_add) {
                        //TODO 进行群成员添加操作
                        return true;
                    }
                    return false;
                }
            });
        }
    }


}
