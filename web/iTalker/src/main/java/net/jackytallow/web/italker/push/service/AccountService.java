package net.jackytallow.web.italker.push.service;


import net.jackytallow.web.italker.push.bean.db.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// 127.0.0.1/api/account/...
@Path("/account")
public class AccountService {

    //GET 127.0.0.1/api/account/login
    @GET
    @Path("/login")
    public String get() {
        return "You get the login.";
    }


    //POST 127.0.0.1/api/account/login
    @POST
    @Path("/login")


    //指定请求与返回的相应体位JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User post(){
        User user = new User();
        user.setName("帅小伙");
        user.setSex(1);
        return user;
    }
}



