package net.jacky.italker.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.jacky.italker.factory.model.Author;
import net.qiujuer.italker.common.R;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/3/1
 * 自定义背景图片
 */
public class ImHeaderView extends ImageView {

    private CollapsingToolbarLayout collapsingToolbarLayout;

    public ImHeaderView(Context context) {
        super(context);
    }

    public ImHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setup(RequestManager manager, Author author) {
        if (author == null)
            return;
        //进行显示
        setup(manager, author.getPortrait());
    }

    public void setup(RequestManager manager, String url) {
        setup(manager, R.drawable.default_portrait, url);
    }

    public void setup(RequestManager manager, int resourceId, String url) {
        if (url == null)
            url = "";
        manager.load(url)
                .placeholder(resourceId)
                .centerCrop()
                .into(new ViewTarget<CollapsingToolbarLayout, GlideDrawable>(collapsingToolbarLayout) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setContentScrim(resource.getCurrent());
                    }
                });

    }
}
