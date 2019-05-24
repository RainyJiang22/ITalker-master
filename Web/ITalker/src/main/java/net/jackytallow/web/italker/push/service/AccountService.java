package net.jackytallow.web.italker.push.service;


import net.jackytallow.web.italker.push.bean.api.account.RegisterModel;
import net.jackytallow.web.italker.push.bean.card.UserCard;
import net.jackytallow.web.italker.push.bean.db.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Jacky
 */
//127.0.0.1/api/account/...
@Path("/account")
public class AccountService {


    @POST
    @Path("/register")
    //指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserCard register(RegisterModel model){

        UserCard card = new UserCard();
        card.setName(model.getName());
        card.setisFollow(true);

        return card;
    }

}
