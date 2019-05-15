package net.jackytallow.web.italker.push.service;


import javax.ws.rs.GET;
import javax.ws.rs.Path;

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

}
