package net.qiujuer.italker.push.frags.search;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;
import net.qiujuer.genius.ui.drawable.LoadingDrawable;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.EmptyView;
import net.qiujuer.italker.common.widget.PortraitView;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.card.UserCard;
import net.qiujuer.italker.factory.presenter.contact.FollowContract;
import net.qiujuer.italker.factory.presenter.contact.FollowPresenter;
import net.qiujuer.italker.factory.presenter.search.SearchContract;
import net.qiujuer.italker.factory.presenter.search.SearchUserPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.SearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索人的界面
 */
public class SearchUserFragment extends PresenterFragment<SearchContract.Presenter>
   implements SearchActivity.SearchFragment,SearchContract.UserView {


    @BindView(R.id.empty)
    EmptyView emptyView;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private RecyclerAdapter<UserCard> mAdapter;

    public SearchUserFragment() {
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        //初始化recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter = new RecyclerAdapter<UserCard>(){

            @Override
            protected int getItemViewType(int position, UserCard userCard) {
                //返回cell的布局ID
                return R.layout.cell_search_list;
            }

            @Override
            protected ViewHolder<UserCard> onCreateViewHolder(View root, int viewType) {
                return new SearchUserFragment.ViewHolder(root);
            }
        });

        //初始化占位布局
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
    }

    @Override
    protected void initData() {
        super.initData();
        //发起首次搜素
        search("");
    }

    @Override
    public void search(String content) {
        //Activity->Fragment->Presenter->Net
        mPresenter.search(content);
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        //初始化Presenter
        return new SearchUserPresenter(this);
    }

    @Override
    public void onSearchDone(List<UserCard> userCards) {
          //数据加载成功的情况下返回数据
         mAdapter.replace(userCards);
         //如果有数据，就是OK，没有数据显示空布局
         mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount()>0);
    }

    /**
     * 每一个Cell的布局操作
     */
    class ViewHolder extends RecyclerAdapter.ViewHolder<UserCard>
    implements FollowContract.View {
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.im_follow)
        ImageView mFollow;

        private FollowContract.Presenter mPresenter;

      //初始化数据
        public ViewHolder(View itemView) {
            super(itemView);
            //当前View和Presenter进行绑定
            new FollowPresenter(this);
        }


        //绑定操作
        @Override
        protected void onBind(UserCard userCard) {
            mPortraitView.setup(Glide.with(SearchUserFragment.this),userCard);
            mName.setText(userCard.getName());
            mFollow.setEnabled(!userCard.isFollow());
        }


        //添加关注的功能
        @OnClick(R.id.im_follow)
        void onFollowClick(){
            //发起关注
            mPresenter.follow(mData.getId());
        }



        @Override
        public void showError(int str) {
            //更改Drawable状态
            if (mFollow.getDrawable() instanceof LoadingDrawable){
               //失败则停止动画，并且显示一个圆圈
                LoadingDrawable drawable = ((LoadingDrawable) mFollow.getDrawable());
               drawable.setProgress(1);
               drawable.stop();
                //设置为默认的
                mFollow.setImageResource(R.drawable.sel_opt_done_add);
            }

        }

        @Override
        public void showLoading() {
          int minSize = (int) Ui.dipToPx(getResources(),22);
          int maxSize = (int) Ui.dipToPx(getResources(),30);
          //初始化一个圆形的动画的Drawable
            LoadingDrawable drawable = new LoadingCircleDrawable(minSize,maxSize);
            //设置背景色为透明
            drawable.setBackgroundColor(0);

            int[] color = new int[]{UiCompat.getColor(getResources(),R.color.white_alpha_208)};
            drawable.setForegroundColor(color);
            mFollow.setImageDrawable(drawable);
            //启动动画
            drawable.start();
        }

        @Override
        public void setPresenter(FollowContract.Presenter presenter) {
            mPresenter = presenter;
        }


        @Override
        public void onFollowSucceed(UserCard userCard) {
            //更改Drawable状态
            if (mFollow.getDrawable() instanceof LoadingDrawable){
                ((LoadingDrawable) mFollow.getDrawable()).stop();
                //设置为默认的
                mFollow.setImageResource(R.drawable.sel_opt_done_add);
            }

            //发起更新
            updateData(userCard);
        }
    }
}
