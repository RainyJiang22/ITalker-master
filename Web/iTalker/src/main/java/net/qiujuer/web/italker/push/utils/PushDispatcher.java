package net.qiujuer.web.italker.push.utils;


import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.google.common.base.Strings;
import net.qiujuer.web.italker.push.bean.api.base.PushModel;
import net.qiujuer.web.italker.push.bean.db.User;
import org.hibernate.engine.jdbc.batch.spi.Batch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 消息推送工具类
 * @author Jacky
 * @version 1.0.0
 */
public class PushDispatcher {
    private static final String appId = "laljVfWVzP9QQxrDkF9iy2";
    private static final String appKey = "x7cyBtuhHQ9JZ6Ki8SlKT";
    private static final String masterSecret = "ad9TE7GdwiAyEzs8BZ4EO6";
    private static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    private final IGtPush pusher;

    //要收到消息的人和内容的列表
    private List<BatchBean> beans = new ArrayList<>();


    public PushDispatcher(){
      //最根本的发送者
        pusher = new IGtPush(host, appKey, masterSecret);

    }

    public boolean add(User receiver, PushModel model){
        //基础检查，必须要有接收者的设备ID号
        //消息发送失败
        if(receiver == null || model == null||
                Strings.isNullOrEmpty(receiver.getPushId()))
            return false;

        String pushString = model.getPushString();
        if (Strings.isNullOrEmpty(pushString))
            return false;


        //构建一个目标+内容
        BatchBean bean = buildMessage(receiver.getPushId(),pushString);
        beans.add(bean);
        return true;
    }


    /**
     * 对要发送的数据进行格式化封装
     * @param clientId 发送接收者设备的ID号
     * @param text 发送的信息
     * @return
     */
    private BatchBean buildMessage(String clientId, String text){
        //透传消息，不是通知栏显示，而是MessageReceiver进行显示
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(text);
        template.setTransmissionType(0); // 这个Type为int型，填写1则自动启动app


        SingleMessage message = new SingleMessage();
        message.setData(template); //把透传信息设置进去
        message.setOffline(true);//是否允许离线消息
        message.setOfflineExpireTime(24 * 3600 * 1000); //离线接收时长

        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientId);

        //返回一个封装
        return new BatchBean(message,target);
    }

    //进行消息最终发送
    public boolean submit(){
        //构建打包的工具类
        IBatch batch = pusher.getBatch();

        //是否有数据
        boolean haveData = false;

        for (BatchBean bean : beans) {
            try {
                batch.add(bean.message,bean.target);
                haveData = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //没有数据就返回
        if (!haveData)
            return false;


        IPushResult result = null;
        try {
            result = batch.submit();
        } catch (IOException e) {
            e.printStackTrace();

            //失败情况下，尝试重复发送
            try {
                batch.retry();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (result!= null){
            try {
                Logger.getLogger("PushDispatcher")
                        .log(Level.INFO, (String) result.getResponse().get("result"));
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Logger.getLogger("PushDispatcher")
                .log(Level.WARNING, "推送服务器异常");
        return false;
    }



    //给每个人发消息的Bean封装
    private static class BatchBean{
        SingleMessage message;
        Target target;


        BatchBean(SingleMessage message, Target target) {
            this.message = message;
            this.target = target;
        }
    }
}
