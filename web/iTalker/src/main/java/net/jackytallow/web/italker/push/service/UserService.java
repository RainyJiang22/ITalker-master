package net.jackytallow.web.italker.push.service;


import com.google.common.base.Strings;
import net.jackytallow.web.italker.push.bean.api.base.ResponseModel;
import net.jackytallow.web.italker.push.bean.api.user.UpdateInfoModel;
import net.jackytallow.web.italker.push.bean.card.UserCard;
import net.jackytallow.web.italker.push.bean.db.User;
import net.jackytallow.web.italker.push.factory.UserFactory;

import javax.ws.rs.HeaderParam;

/**
 * 用户信息处理的Service
 */

//localhost/italker/api/user/...
public class UserService {


    public ResponseModel<UserCard> update(@HeaderParam("token")String token,
                                          UpdateInfoModel model){
        if (Strings.isNullOrEmpty(token)||!UpdateInfoModel.check(model)){
            return ResponseModel.buildParameterError();
        }

        //拿到自己的个人信息
        User user = UserFactory.findByToken(token);
        if (user != null){

            //更新用户信息
            user = model.updateToUser(user);
            user = UserFactory.update(user);
            //构架自己的用户信息
            UserCard card = new UserCard(user,true);
            //返回
            return ResponseModel.buildOk(card);
        }else{
            //Token失效，所有无法进行绑定
            return ResponseModel.buildAccountError();
        }
    }
}
