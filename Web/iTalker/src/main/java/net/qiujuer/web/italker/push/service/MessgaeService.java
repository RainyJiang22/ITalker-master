package net.qiujuer.web.italker.push.service;


import net.qiujuer.web.italker.push.bean.api.base.ResponseModel;
import net.qiujuer.web.italker.push.bean.api.message.MessageCreateModel;
import net.qiujuer.web.italker.push.bean.card.MessageCard;
import net.qiujuer.web.italker.push.bean.db.Message;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.factory.MessageFactory;
import net.qiujuer.web.italker.push.factory.PushFactory;
import net.qiujuer.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 消息发送的接口
 * @author Jacky
 * @version 1.0.0
 */
@Path("/message")
public class MessgaeService extends BaseService {
    // 发送一条消息到服务器
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<MessageCard> pushMessage(MessageCreateModel model) {
        if(!MessageCreateModel.check(model)){
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();

        //查询是否在数据库中已经存在了
        Message message = MessageFactory.findById(model.getId());
        if (message != null){
            return ResponseModel.buildOk(new MessageCard(message));
        }

        if (model.getReceiverType() == Message.RECEVIER_TYPE_GROUP){
           return pushToGroup(self,model);
        }else{
           return pushToUser(self,model);
        }
    }

    //发送到人
    private ResponseModel<MessageCard> pushToUser(User sender, MessageCreateModel model) {
       User receiver = UserFactory.findById(model.getReceiverId());
       if (receiver == null){
           //没有找到接收者
           return ResponseModel.buildNotFoundUserError("Cont not find receiver user");
       }

       if (receiver.getId().equalsIgnoreCase(sender.getId())){
          //发送者和接收者是同一个人就返回接收信息失败
           return ResponseModel.buildCreateError(ResponseModel.ERROR_CREATE_MESSAGE);
        }
       //存储数据库
       Message message = MessageFactory.add(sender,receiver,model);

       return buildAndPushResponse(sender,message);
    }


    //发送到群
    private ResponseModel<MessageCard> pushToGroup(User sender, MessageCreateModel model) {
        //TODO Group group  = GroupFactory.findById()
        return null;
    }

    //推送并构建一个返回消息
    //TODO 先不进行推送
    private ResponseModel<MessageCard> buildAndPushResponse(User sender, Message message) {
        if (message == null){
            //存储数据库失败
            return ResponseModel.buildCreateError(ResponseModel.ERROR_CREATE_MESSAGE);
        }

        //进行推送
        PushFactory.pushNewMessage(sender, message);

        //返回
       return ResponseModel.buildOk(new MessageCard(message));
    }
}


