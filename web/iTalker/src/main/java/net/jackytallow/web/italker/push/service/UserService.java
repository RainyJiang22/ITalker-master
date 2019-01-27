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
public class UserService extends BaseService {


    public ResponseModel<UserCard> update(UpdateInfoModel model) {
        if (!UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

           User self = getSelf();

        //更新用户信息
        self = model.updateToUser(self);
        self = UserFactory.update(self);

        //构架自己的用户信息
        UserCard card = new UserCard(self,true);
        //返回
        return ResponseModel.buildOk();
    }
}
