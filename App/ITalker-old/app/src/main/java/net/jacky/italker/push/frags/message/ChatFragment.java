package net.jacky.italker.push.frags.message;

import android.os.Bundle;

import net.jacky.italker.common.app.Fragment;
import net.jacky.italker.push.activities.MessageActivity;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/9
 */
public abstract class ChatFragment extends Fragment {

    private String mReciverId;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mReciverId = bundle.getString(MessageActivity.KEY_RECEIVER_ID);
    }
}
