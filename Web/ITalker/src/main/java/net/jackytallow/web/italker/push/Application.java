package net.jackytallow.web.italker.push;


import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import net.jackytallow.web.italker.push.provider.AuthRequestFilte;
import net.jackytallow.web.italker.push.provider.GsonProvider;
import net.jackytallow.web.italker.push.service.AccountService;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * @author Jacky
 */
public class Application extends ResourceConfig {

    public Application(){
       //所有逻辑测试
      //  packages("net.jackytallow.web.italker.push.service");
        //注册逻辑处的包
        packages(AccountService.class.getPackage().getName());


        //注册全局请求拦截器
        register(AuthRequestFilte.class);

        //注册Json解析器
//       register(JacksonJsonProvider.class);
       //替换解析器为GSON
        register(GsonProvider.class);

       //注册日志打印输出
        register(Logger.class);
    }

}
