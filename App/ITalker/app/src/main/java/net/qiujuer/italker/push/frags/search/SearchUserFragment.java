package net.qiujuer.italker.push.frags.search;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.EmptyView;
import net.qiujuer.italker.common.widget.PortraitView;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.card.UserCard;
import net.qiujuer.italker.factory.presenter.search.SearchContract;
import net.qiujuer.italker.factory.presenter.search.SearchUserPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.SearchActivity;

import java.util.List;

import butterknife.BindView;

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
    class ViewHolder extends RecyclerAdapter.ViewHolder<UserCard>{
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.im_follow)
        ImageView mFollow;



        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(UserCard userCard) {
            Glide.with(SearchUserFragment.this)
                    .load(userCard.getPortrait())
                    .centerCrop()
                    .into(mPortraitView);
            mName.setText(userCard.getName());
            mFollow.setEnabled(!userCard.isFollow());
        }
    }
}
