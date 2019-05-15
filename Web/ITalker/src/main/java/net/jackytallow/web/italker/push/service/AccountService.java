package net.jackytallow.web.italker.push.service;


import net.jackytallow.web.italker.push.bean.User;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Jacky
 */
//127.0.0.1/api/account/...
@Path("/account")
public class AccountService {


    //127.0.0.1/api/account/login
    @GET
    @Path("/login")
    public String get(){
        return "you get the login";
    }


    @POST
    @Path("/login")
    //指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User post(){
        User user = new User();
        user.setUsername("帅哥");
        user.setSex(1);
        return user;
    }
}
