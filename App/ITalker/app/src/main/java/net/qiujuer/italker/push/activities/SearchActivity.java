package net.qiujuer.italker.push.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.qiujuer.italker.common.app.ToolBarActivity;
import net.qiujuer.italker.push.R;

public class SearchActivity extends ToolBarActivity {
    public static final String EXTRE_TYPE = "EXTRE_TYPE";
    public static final int TYPE_USER =1; //搜索人
    public static final int TYPE_GROUP =2;//搜索群

    //具体需要显示的类型
    private int type;

    /**
     * 显示搜索界面
     * @param context 上下文
     * @param type  用户还是群
     */
    public static void show(Context context, int type){
        Intent intent = new Intent(context,SearchActivity.class);
        intent.putExtra(EXTRE_TYPE, type);
        context.startActivity(intent);
    }


    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt(EXTRE_TYPE);
        //是显示搜素人或者是搜索群的一种
        return type == TYPE_USER || type == TYPE_GROUP;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //初始化菜单
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
       //找到搜索菜单
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView != null){

            //拿到一个搜索管理器
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            //添加搜索按钮的监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                //当点击提交搜索文本的时候
                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(query);
                    return false;

                }

                //当搜索文本改变的时候
                @Override
                public boolean onQueryTextChange(String newText) {
                    //当文字进行改变的时候，只为null的情况下进行搜索
                    if (TextUtils.isEmpty(newText)){
                        search("");
                        return true;
                    }
                    return false;
                }
            });

        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 搜索的发起点
     * @param query 搜索的文本
     */
    private void search(String query) {
    }
}
