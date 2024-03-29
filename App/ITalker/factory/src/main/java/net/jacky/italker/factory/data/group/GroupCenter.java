package net.jacky.italker.factory.data.group;

import net.jacky.italker.factory.model.card.GroupCard;
import net.jacky.italker.factory.model.card.GroupMemberCard;

/**
 * 群中心的接口定义
 *
 * @author jacky
 * @version 1.0.0
 */
public interface GroupCenter {
    // 群卡片的处理
    void dispatch(GroupCard... cards);

    // 群成员的处理
    void dispatch(GroupMemberCard... cards);
}
