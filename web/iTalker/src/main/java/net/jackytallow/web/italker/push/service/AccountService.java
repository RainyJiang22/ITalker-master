package net.jackytallow.web.italker.push.service;


import net.jackytallow.web.italker.push.bean.api.account.AccountRspModel;
import net.jackytallow.web.italker.push.bean.api.account.LoginModel;
import net.jackytallow.web.italker.push.bean.api.account.RegisterModel;
import net.jackytallow.web.italker.push.bean.api.base.ResponseModel;
import net.jackytallow.web.italker.push.bean.db.User;
import net.jackytallow.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// 127.0.0.1/italker/api/account/...
@Path("/account")
public class AccountService {
    //登录
    @POST
    @Path("/login")
    //指定请求与返回的相应体位JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel model){
        if (!LoginModel.check(model)){
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        User user = UserFactory.login(model.getAccount(),model.getPassword());
        if (user != null){
            //返回当前的账户
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        }else{
            //登录失败
            return ResponseModel.buildLoginError();
        }
    }


    //注册
    @POST
    @Path("/register")
    //指定请求与返回的相应体位JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

 //   public UserCard register(RegisterModel model){
   //     User user = UserFactory.findByPhone(model.getAccount().trim());
     public ResponseModel<AccountRspModel> register(RegisterModel model){
        if (!RegisterModel.check(model)){
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        User user  = UserFactory.findByPhone(model.getAccount().trim());
        if (user != null){
//            UserCard card = new UserCard();
//            card.setName("已有了phone");
//            return card;
            //已有账号
            return ResponseModel.buildHaveAccountError();
        }



        user = UserFactory.findByName(model.getName().trim());
        if (user != null){
//            UserCard card = new UserCard();
//            card.setName("已有了Name");
//            return card;
            //已有用户名
            return ResponseModel.buildHaveNameError();
        }

        //开始注册逻辑
        user = UserFactory.register((model.getAccount()),
               model.getPassword(),
               model.getName());

        if (user != null){
          AccountRspModel rspModel = new AccountRspModel(user);
          return ResponseModel.buildOk(rspModel);
        }else{
            return ResponseModel.buildRegisterError();
        }
    }
}



