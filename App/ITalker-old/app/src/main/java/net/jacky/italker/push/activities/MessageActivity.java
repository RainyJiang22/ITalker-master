package net.jacky.italker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import net.jacky.italker.common.app.Activity;
import net.jacky.italker.common.app.Fragment;
import net.jacky.italker.factory.model.Author;
import net.jacky.italker.factory.model.db.Group;
import net.jacky.italker.push.frags.message.ChatGroupFragment;
import net.jacky.italker.push.frags.message.ChatUserFragment;
import net.qiujuer.italker.push.R;

public class MessageActivity extends Activity {
    //接收者ID，可以是群，也可以是人的id
     public static final String KEY_RECEIVER_ID = "KEY_RECEIVER_ID";
     //标识是否是群
     public static final String KEY_RECEIVER_IS_GROUP = "KEY_RECEIVER_IS_GROUP";

     private String mReceiverId;
     private boolean mIsGroup;


    /**
     * 显示人的聊天界面
     *
     * @param context 上下文
     * @param author  人的信息
     */
    public static void show(Context context, Author author) {
        if (author == null || context ==null || TextUtils.isEmpty(author.getId()))
            return;
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID,author.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP,false);
        context.startActivity(intent);
    }

    /**
     * 发起一个群的聊天界面
     * @param context 上下文
     * @param group 群的Model
     */
    public static void show(Context context, Group group) {

        if (group == null || context ==null || TextUtils.isEmpty(group.getId()))
            return;
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID,group.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP,true);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }


    @Override
    protected boolean initArgs(Bundle bundle) {
        mReceiverId = bundle.getString(KEY_RECEIVER_ID);
        mIsGroup = bundle.getBoolean(KEY_RECEIVER_IS_GROUP);
        return !TextUtils.isEmpty(mReceiverId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
        Fragment fragment;
        if (mIsGroup)
            fragment = new ChatGroupFragment();
        else
            fragment = new ChatUserFragment();

        //从Activity传递参数到Fragment中去
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RECEIVER_ID,mReceiverId);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container,fragment)
                .commit();
    }
}
