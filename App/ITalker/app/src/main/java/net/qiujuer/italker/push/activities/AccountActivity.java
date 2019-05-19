package net.qiujuer.italker.push.activities;

import android.content.Context;
import android.content.Intent;
import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.fragment.account.UpdateInfoFragment;

public class AccountActivity extends Activity {
     private Fragment mCurFragment;


    /**
     * 账户显示Activity显示的入口
     * @param context
     */
    public static void show(Context context){
        context.startActivity(new Intent(context,AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }


    @Override
    protected void initWidget() {
        super.initWidget();

         mCurFragment = new UpdateInfoFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container,mCurFragment)
                .commit();
    }


    /**
     * Activity中收到剪切成功的回调
     * @param requestCode  //回调参数代码
     * @param resultCode  //返回结果
     * @param data  //返回数据（Intent 图片处理数据）
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
         mCurFragment.onActivityResult(requestCode,resultCode,data);
    }

}
