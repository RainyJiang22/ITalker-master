package net.qiujuer.italker.factory.presenter.search;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.data.helper.UserHelper;
import net.qiujuer.italker.factory.model.card.UserCard;
import net.qiujuer.italker.factory.presenter.BasePresenter;
import java.util.List;
import retrofit2.Call;


/**
 * 搜索人的实现
 */
public class SearchUserPresenter extends BasePresenter<SearchContract.UserView>
   implements SearchContract.Presenter , DataSource.Callback<List<UserCard>> {
   private Call searchCall;

    public SearchUserPresenter(SearchContract.UserView view) {
        super(view);
    }

    @Override
    public void search(String content) {
         start();
         //进行取消的操作
     if (searchCall != null&&!searchCall.isCanceled()){
         //如果有上一次的请求，并且没有取消，则调用取消操作
         searchCall.cancel();
     }

      searchCall =   UserHelper.search(content,this);
    }

    @Override
    public void onDataLoaded(final List<UserCard> userCards) {
        //搜索成功
          final SearchContract.UserView view = getView();
           //保证一定正确
           if (view != null){
               Run.onUiAsync(new Action() {
                   @Override
                   public void call() {
                        view.onSearchDone(userCards);
                   }
               });
           }
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        //搜索失败
        final SearchContract.UserView view = getView();
        //保证一定正确,避免出现线程阻塞
        if (view != null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(strRes);
                }
            });
        }
    }
}
