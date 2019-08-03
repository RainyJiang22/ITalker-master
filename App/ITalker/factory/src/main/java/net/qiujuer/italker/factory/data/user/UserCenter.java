package net.qiujuer.italker.factory.data.user;


import net.qiujuer.italker.factory.model.card.UserCard;

/**
 * 用户中心的基本的定义
 * @author Jacky
 * @version 1.0.0
 */
public interface UserCenter {
    // 分发处理一推用户卡片的数据，并更新到数据库
    void dispatch(UserCard... cards);
}
