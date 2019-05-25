package net.jackytallow.web.italker.push.service;


import net.jackytallow.web.italker.push.bean.api.account.RegisterModel;
import net.jackytallow.web.italker.push.bean.card.UserCard;
import net.jackytallow.web.italker.push.bean.db.User;
import net.jackytallow.web.italker.push.factory.UserFactory;

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

        User user = UserFactory.register(model.getAccount(),
                model.getPassword(),model.getName());


        if (user != null){
            UserCard card = new UserCard();
            card.setName(user.getName());
            card.setPhone(user.getPhone());
            card.setSex(user.getSex());
            card.setisFollow(true);
            card.setModfiyAt(user.getUpdateAt());
            return card;
        }


       return null;
    }

}
