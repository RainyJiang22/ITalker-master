package net.jacky.italker.push;

import android.content.Context;

import com.igexin.sdk.PushManager;

import net.jacky.italker.common.app.Application;
import net.jacky.italker.factory.Factory;

/**
 * @author jacky
 * @version 1.0.0
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 调用Factory进行初始化
        Factory.setup();
        // 推送进行初始化
        PushManager.getInstance().initialize(this);
    }

    @Override
    protected void showAccountView(Context context) {

        // 登录界面的显示


    }
}
