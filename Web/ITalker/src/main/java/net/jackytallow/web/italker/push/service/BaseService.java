package net.jackytallow.web.italker.push.service;

import net.jackytallow.web.italker.push.bean.db.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

public class BaseService {

    //为上下文添加注解
    // 具体的值为我们的拦截器中所返回的SecurityContext

    @Context
    protected SecurityContext securityContext;


    /**
     * 从上下文中直接获取自己的信息
     *
     * @return User
     */
    protected User getSelf(){
        return (User) securityContext.getUserPrincipal();
    }


}
