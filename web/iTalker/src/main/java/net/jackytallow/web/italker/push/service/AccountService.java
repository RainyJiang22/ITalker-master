package net.jackytallow.web.italker.push.service;


import net.jackytallow.web.italker.push.bean.api.account.RegisterModel;
import net.jackytallow.web.italker.push.bean.db.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// 127.0.0.1/api/account/...
@Path("/account")
public class AccountService {
    
    @POST
    @Path("/register")
    //指定请求与返回的相应体位JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User register(RegisterModel model){
        User user = new User();
        user.setName(model.getName());
        user.setSex(1);
        return user;
    }
}



