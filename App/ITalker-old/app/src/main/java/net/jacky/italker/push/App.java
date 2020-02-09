package net.jacky.italker.push;

import com.igexin.sdk.PushManager;

import net.jacky.italker.factory.Factory;
import net.jacky.italker.common.app.Application;

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
}
