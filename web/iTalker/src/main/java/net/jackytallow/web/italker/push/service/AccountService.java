package net.jackytallow.web.italker.push.service;


import com.google.common.base.Strings;
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
    public ResponseModel<AccountRspModel> login(LoginModel model) {
        if (!LoginModel.check(model)) {
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        User user = UserFactory.login(model.getAccount(), model.getPassword());
        if (user != null) {

            //如果有携带PushId
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                return bind(user, model.getPushId());
            }


            //返回当前的账户
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            //登录失败
            return ResponseModel.buildLoginError();
        }
    }

    // 绑定设备Id
    @POST
    @Path("/bind/{pushId}")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 从请求头中获取token字段
    // pushId从url地址中获取
    public ResponseModel<AccountRspModel> bind(@HeaderParam("token") String token,
                                               @PathParam("pushId") String pushId) {
        if (Strings.isNullOrEmpty(token) ||
                Strings.isNullOrEmpty(pushId)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }

        // 拿到自己的个人信息
        User user = UserFactory.findByToken(token);
        if (user != null) {
            return bind(user, pushId);
        } else {
            // Token 失效，所有无法进行绑定
            return ResponseModel.buildAccountError();
        }
    }


    /**
     * 绑定的操作
     *
     * @param self   自己
     * @param pushId PushId
     * @return User
     */
    private ResponseModel<AccountRspModel> bind(User self, String pushId) {
        // 进行设备Id绑定的操作
        User user = UserFactory.bindPushId(self, pushId);

        if (user == null) {
            // 绑定失败则是服务器异常
            return ResponseModel.buildServiceError();

        }

        // 返回当前的账户, 并且已经绑定了
        AccountRspModel rspModel = new AccountRspModel(user, true);
        return ResponseModel.buildOk(rspModel);

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

