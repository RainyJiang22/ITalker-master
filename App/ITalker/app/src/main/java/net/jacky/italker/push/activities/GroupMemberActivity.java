package net.jacky.italker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.jacky.italker.common.app.PresenterToolbarActivity;
import net.jacky.italker.common.widget.PortraitView;
import net.jacky.italker.common.widget.recycler.RecyclerAdapter;
import net.jacky.italker.factory.model.db.view.MemberUserModel;
import net.jacky.italker.factory.presenter.group.GroupMembersContract;
import net.jacky.italker.factory.presenter.group.GroupMembersPresenter;
import net.jacky.italker.push.R;
import net.jacky.italker.push.frags.group.GroupMemberAddFragment;


import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * @author jacky
 * @version 1.0.0
 */
public class GroupMemberActivity extends PresenterToolbarActivity<GroupMembersContract.Presenter>
        implements GroupMembersContract.View, GroupMemberAddFragment.Callback {
    private static final String KEY_GROUP_ID = "KEY_GROUP_ID";
    private static final String KEY_GROUP_ADMIN = "KEY_GROUP_ADMIN";


    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private String mGroupId;
    private boolean mIsAdmin;
    private RecyclerAdapter<MemberUserModel> mAdapter;


    public static void show(Context context, String groupId) {
        show(context, groupId, false);
    }

    public static void showAdmin(Context context, String groupId) {
        show(context, groupId, true);
    }

    private static void show(Context context, String groupId, boolean isAdmin) {
        if (TextUtils.isEmpty(groupId))
            return;

        Intent intent = new Intent(context, GroupMemberActivity.class);
        intent.putExtra(KEY_GROUP_ID, groupId);
        intent.putExtra(KEY_GROUP_ADMIN, isAdmin);
        context.startActivity(intent);

    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_group_member;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        mGroupId = bundle.getString(KEY_GROUP_ID);
        mIsAdmin = bundle.getBoolean(KEY_GROUP_ADMIN);
        return !TextUtils.isEmpty(mGroupId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        setTitle(R.string.title_member_list);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<MemberUserModel>() {
            @Override
            protected int getItemViewType(int position, MemberUserModel memberUserModel) {
                return R.layout.cell_group_create_contact;
            }

            @Override
            protected ViewHolder<MemberUserModel> onCreateViewHolder(View root, int viewType) {
                return new GroupMemberActivity.ViewHolder(root);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        // 开始数据刷新
        mPresenter.refresh();

        // 显示成员界面，添加成员
        if (mIsAdmin) {
            new GroupMemberAddFragment()
                    .show(getSupportFragmentManager(), GroupMemberAddFragment.class.getName());
          mToolbar.inflateMenu(R.menu.chat_group);
        }
    }

    @Override
    public RecyclerAdapter<MemberUserModel> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        // 隐藏Loading就好
        hideLoading();
    }

    @Override
    protected GroupMembersContract.Presenter initPresenter() {
        return new GroupMembersPresenter(this);
    }

//    @Override
//    public void showAdminOptions(boolean isAdmin) {
//        if (isAdmin) {
//            mToolbar.inflateMenu(R.menu.chat_group);
//            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    if (item.getItemId() == R.id.action_add) {
//                        new GroupMemberAddFragment()
//                                .show(getSupportFragmentManager(), GroupMemberAddFragment.class.getName());
//                        return true;
//                    }
//                    return false;
//                }
//            });
//        }
//    }

    @Override
    public String getGroupId() {
        return mGroupId;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void refreshMembers() {
        // 重新加载成员信息
        if (mPresenter != null)
            mPresenter.refresh();
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<MemberUserModel> {
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;
        @BindView(R.id.txt_name)
        TextView mName;


        ViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.cb_select).setVisibility(View.GONE);
        }

        @Override
        protected void onBind(MemberUserModel model) {
            mPortrait.setup(Glide.with(GroupMemberActivity.this), model.portrait);
            mName.setText(model.name);
        }

        @OnClick(R.id.im_portrait)
        void onPortraitClick() {
            PersonalActivity.show(GroupMemberActivity.this, mData.userId);
        }
    }
}
