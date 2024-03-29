package net.jackytallow.web.italker.push;

import net.jackytallow.web.italker.push.provider.AuthRequestFilter;
import net.jackytallow.web.italker.push.provider.GsonProvider;
import net.jackytallow.web.italker.push.service.AccountService;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * @author jacky
 */
public class Application extends ResourceConfig {
    public Application() {
        // 注册逻辑处理的包名
        //packages("net.jacky.web.italker.push.service");
        packages(AccountService.class.getPackage().getName());

        // 注册我们的全局请求拦截器
        register(AuthRequestFilter.class);

        // 注册Json解析器
        // register(JacksonJsonProvider.class);
        // 替换解析器为Gson
        register(GsonProvider.class);

        // 注册日志打印输出
        register(Logger.class);

    }
}
