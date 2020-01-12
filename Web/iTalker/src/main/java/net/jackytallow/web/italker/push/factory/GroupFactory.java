package net.jackytallow.web.italker.push.factory;

import net.jackytallow.web.italker.push.bean.db.Group;
import net.jackytallow.web.italker.push.bean.db.GroupMember;
import net.jackytallow.web.italker.push.bean.db.User;

import java.util.Set;

/**
 * 群数据处理类
 * @author Jacky
 * @version 1.0.0
 */
public class GroupFactory {

    public static Group findById(String groupId){
        // TODO 查询一个群
        return null;
    }

    public static Group findById(User user,String groupId){
        // TODO 查询一个群 同时该User必须为群成员，否则返回
        return null;
    }

    public static Set<GroupMember> getMembers(Group group) {
       //TODO 查询一个群的成员
        return null;

    }
}
