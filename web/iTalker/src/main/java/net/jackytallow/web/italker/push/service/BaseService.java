package net.jackytallow.web.italker.push.service;

import net.jackytallow.web.italker.push.bean.db.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

public class BaseService {

    /**
     * 添加一个上下文注释，该注解会给SecurityContext赋值
     * 具体的值为我们的拦截器中所返回的SecuriyContext
     */
    @Context
    protected SecurityContext securityContext;


    /**
     * 从上下文中直接获取自己的信息
     *
     * @return
     */
    protected User getSelf(){
        return (User) securityContext.getUserPrincipal();
    }
}
