package net.qiujuer.italker.common.app;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import net.qiujuer.italker.common.R;

/**
 * @author jacky
 */
public abstract class ToolBarActivity  extends Activity{

    protected Toolbar mToolbar;

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar((Toolbar) findViewById(R.id.toolbar));
    }

    /**
     * 初始化Toolbar
     * @param toolbar
     */
    public void initToolbar(Toolbar toolbar){
        mToolbar = toolbar;
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

       initTitleNeedBack();
    }

    protected void initTitleNeedBack(){
        //设置左上角为实际的返回效果
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
