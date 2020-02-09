package net.jacky.italker.push.frags.search;


import net.jacky.italker.common.app.Fragment;
import net.qiujuer.italker.push.R;
import net.jacky.italker.push.activities.SearchActivity;

/**
 * 搜索群的界面实现
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

    @Override
    public void search(String content) {

    }
}
