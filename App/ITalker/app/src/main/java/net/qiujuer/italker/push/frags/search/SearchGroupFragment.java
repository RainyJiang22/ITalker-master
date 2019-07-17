package net.qiujuer.italker.push.frags.search;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.SearchActivity;

/**
 *搜索群的界面
 */
public class SearchGroupFragment extends Fragment
   implements SearchActivity.SearchFragment {


    public SearchGroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    /**
     * 搜索的方法
     * @param content
     */
    @Override
    public void search(String content) {


    }
}
