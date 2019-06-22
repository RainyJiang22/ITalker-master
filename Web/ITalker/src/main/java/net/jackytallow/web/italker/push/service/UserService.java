package net.jackytallow.web.italker.push.service;

import com.google.common.base.Strings;
import net.jackytallow.web.italker.push.bean.api.base.ResponseModel;
import net.jackytallow.web.italker.push.bean.api.user.UpdateInfoModel;
import net.jackytallow.web.italker.push.bean.card.UserCard;
import net.jackytallow.web.italker.push.bean.db.User;
import net.jackytallow.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */
// 127.0.0.1/api/user/...
@Path("/user")
public class UserService extends BaseService {


//
//    // 用户信息修改接口
//    // 返回自己的个人信息
//    @PUT
//    //@Path("") //127.0.0.1/api/user 不需要写，就是当前目录
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public ResponseModel<UserCard> update(@HeaderParam("token") String token,
//                                          UpdateInfoModel model) {
//        if ( Strings.isNullOrEmpty(token) || !UpdateInfoModel.check(model)) {
//            return ResponseModel.buildParameterError();
//        }
//
//        User self = getSelf();
//
//        //拿到自己的个人信息
//        User user  = UserFactory.findByToken(token);
//
//        if(user != null){
//            // 更新用户信息
//            user = model.updateToUser(user);
//            user = UserFactory.update(user);
//
//            // 构架自己的用户信息
//            UserCard card = new UserCard(user, true);
//            // 返回
//            return ResponseModel.buildOk(card);
//        } else{
//            //绑定Token失效
//            return ResponseModel.buildAccountError();
//        }
//
////        User self = getSelf();
//
//    }




    // 用户信息修改接口
    // 返回自己的个人信息
    @PUT
    //@Path("") //127.0.0.1/api/user 不需要写，就是当前目录
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel model) {
        if (!UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();
        // 更新用户信息
        self = model.updateToUser(self);
        self = UserFactory.update(self);
        // 构架自己的用户信息
        UserCard card = new UserCard(self, true);
        // 返回
        return ResponseModel.buildOk(card);
    }

}
