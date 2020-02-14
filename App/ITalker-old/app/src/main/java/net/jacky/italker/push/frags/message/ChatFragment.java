package net.jacky.italker.push.frags.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.jacky.italker.common.app.Fragment;
import net.jacky.italker.common.widget.PortraitView;
import net.jacky.italker.common.widget.adapter.TextWatcherAdapter;
import net.jacky.italker.common.widget.recycler.RecyclerAdapter;
import net.jacky.italker.factory.model.db.Message;
import net.jacky.italker.factory.model.db.User;
import net.jacky.italker.factory.persistence.Account;
import net.jacky.italker.push.activities.MessageActivity;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.italker.push.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/9
 */
public abstract class ChatFragment extends Fragment
        implements AppBarLayout.OnOffsetChangedListener {

    protected String mReciverId;
    protected Adapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.edit_content)
    EditText mContent;

    @BindView(R.id.btn_submit)
    View mSubmit;


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
        initEditContent();

        //RecyclerView基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
    }


    //初始化Toolbar
    protected void initToolbar() {
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

    private void initEditContent() {
        mContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                boolean needSendMsg = !TextUtils.isEmpty(content);
                //设置状态，改变对应的Icon
                mSubmit.setActivated(needSendMsg);
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {

        }
    }

    @OnClick(R.id.btn_face)
    void onFaceClick() {
        //TODO
    }

    @OnClick(R.id.btn_record)
    void onRecordClick() {
        //TODO
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        if (mSubmit.isActivated()) {
            //发送
        } else {
            onMoreClick();
        }
    }

    private void onMoreClick() {
        //TODO
    }


    //内容的适配器
    private class Adapter extends RecyclerAdapter<Message> {

        @Override
        protected int getItemViewType(int position, Message message) {
            //我发送在右边，收到在左边
            boolean isRight = Objects.equals(message.getSender().getId(), Account.getUserId());
            switch (message.getType()) {

                //文字内容
                case Message.TYPE_STR:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;

                //语音内容
                case Message.TYPE_AUDIO:
                    return isRight ? R.layout.cell_chat_audio_right : R.layout.cell_chat_audio_left;

                //图片内容
                case Message.TYPE_PIC:
                    return isRight ? R.layout.cell_chat_pic_right : R.layout.cell_chat_pic_left;

                //其他内容：文件
                default:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;

            }

        }

        @Override
        protected ViewHolder<Message> onCreateViewHolder(View root, int viewType) {
            switch (viewType) {
                case R.layout.cell_chat_text_right:
                case R.layout.cell_chat_text_left:
                    return new TextHolder(root);

                case R.layout.cell_chat_audio_right:
                case R.layout.cell_chat_audio_left:
                    return new AudioHolder(root);

                case R.layout.cell_chat_pic_right:
                case R.layout.cell_chat_pic_left:
                    return new PicHolder(root);

                //默认情况下返回的就是Text类型的Holder进行处理
                //文件类型进行处理
                default:
                    return new TextHolder(root);
            }
        }
    }

    //Holder的基类
    class BaseHolder extends RecyclerAdapter.ViewHolder<Message> {
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;

        //允许为空，左边没有，右边有
        @Nullable
        @BindView(R.id.loading)
        Loading mLoading;


        public BaseHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            User sender = message.getSender();
            //进行数据加载，DBFlow框架自带
            sender.load();
            //头像加载
            mPortrait.setup(Glide.with(ChatFragment.this), sender);


            if (mLoading != null) {
                //当前布局应该是在右边
                int status = message.getStatus();
                if (status == Message.STATUS_DONE) {
                    //正常状态，隐藏loading
                    mLoading.stop();
                    mLoading.setVisibility(View.GONE);
                } else if (status == Message.STATUS_CREATED) {
                    //正在发送中的状态
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.setProgress(0);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(), R.color.colorAccent));
                    mLoading.start();
                } else if (status == Message.STATUS_FAILED) {
                    //发送失败状态,允许重新发送
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.stop();
                    mLoading.setProgress(1);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(), R.color.alertImportant));
                }

                //当状态是错误状态的时候才允许点击
                mPortrait.setEnabled(status == Message.STATUS_FAILED);
            }
        }

        @OnClick(R.id.im_portrait)
        void onRePushClick() {
            //重新发送
            if (mLoading != null) {
                //必须是右边的才有可能需要重新发送
                //TODO 重新发送
            }
        }
    }

    //文字的Holder
    class TextHolder extends BaseHolder {

        @BindView(R.id.txt_content)
        TextView mContent;

        public TextHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);

            //把内容设置到布局上
            mContent.setText(message.getContent());
        }
    }

    //语音的Holder
    class AudioHolder extends BaseHolder {

        public AudioHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);

            //TODO
        }
    }

    //图片的Holder
    class PicHolder extends BaseHolder {

        public PicHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);
            //TODO
        }
    }

}
