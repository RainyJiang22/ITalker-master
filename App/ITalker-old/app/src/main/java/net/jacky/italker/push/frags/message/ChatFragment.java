package net.jacky.italker.push.frags.message;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.jacky.italker.common.app.Fragment;
import net.jacky.italker.push.activities.MessageActivity;
import net.qiujuer.italker.push.R;

import butterknife.BindView;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/9
 */
public abstract class ChatFragment extends Fragment
        implements AppBarLayout.OnOffsetChangedListener {

    private String mReciverId;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;


    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mReciverId = bundle.getString(MessageActivity.KEY_RECEIVER_ID);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        initToolbar();
        initAppBar();
        //RecyclerView基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void initToolbar() {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    //给界面的Appbar设置一个监听，得到关闭与打开时候的进度
    private void initAppBar() {
        //设置偏移量
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {

        }
    }


}
