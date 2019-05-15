package net.jackytallow.web.italker.push;


import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
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

        //注册Json解析器
       register(JacksonJsonProvider.class);

       //注册日志打印输出
        register(Logger.class);
    }

}
