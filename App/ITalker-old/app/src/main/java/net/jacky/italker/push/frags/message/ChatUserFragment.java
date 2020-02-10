package net.jacky.italker.push.frags.message;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.jacky.italker.common.widget.PortraitView;
import net.qiujuer.italker.push.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatUserFragment extends ChatFragment {

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    public ChatUserFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_user;
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        super.onOffsetChanged(appBarLayout, verticalOffset);
        View view = mPortrait;


        if (verticalOffset == 0) {
            //完全展开
            mPortrait.setVisibility(View.VISIBLE);
            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);
        } else {
            //abs运算
            verticalOffset = Math.abs(verticalOffset);
            final int totalScrollRange = appBarLayout.getTotalScrollRange();
            if (verticalOffset >= totalScrollRange) {
                //关闭状态
                mPortrait.setVisibility(View.INVISIBLE);
                view.setScaleX(0);
                view.setScaleY(0);
                view.setAlpha(0);
            }else{
                //中间状态
                float progress =1 - verticalOffset / (float)totalScrollRange;
                mPortrait.setVisibility(View.VISIBLE);
                view.setScaleX(progress);
                view.setScaleY(progress);
                view.setAlpha(progress);
            }
        }
    }
}
