package net.qiujuer.italker.push.activities;

import android.content.Context;
import android.os.Bundle;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.factory.model.Author;
import net.qiujuer.italker.push.R;

public class MessageActivity extends Activity {

    /**
     * 显示人的聊天记录
     * @param context 上下文
     * @param author 人的信息
     */
    public static void show(Context context, Author author){

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }

}
