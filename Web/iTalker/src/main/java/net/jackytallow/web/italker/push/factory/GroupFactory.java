package net.jackytallow.web.italker.push.factory;

import com.google.common.base.Strings;
import net.jackytallow.web.italker.push.bean.api.group.GroupCreateModel;
import net.jackytallow.web.italker.push.bean.db.Group;
import net.jackytallow.web.italker.push.bean.db.GroupMember;
import net.jackytallow.web.italker.push.bean.db.User;
import net.jackytallow.web.italker.push.utils.Hib;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 群数据处理类
 *
 * @author Jacky
 * @version 1.0.0
 */
public class GroupFactory {

    //通过Id拿取群Model
    public static Group findById(String groupId) {
        return Hib.query(session -> session.get(Group.class, groupId));
    }


    //查询一个群 同时该User必须为群成员，否则返回
    public static Group findById(User user, String groupId) {
        GroupMember member = getMember(user.getId(), groupId);
        if (member != null) {
            return member.getGroup();
        }
        return null;
    }


    //获取一个群的所有成员
    public static Set<GroupMember> getMembers(Group group) {
        return Hib.query(session -> {
            @SuppressWarnings("unchecked")
            List<GroupMember> members = session.createQuery("from GroupMember where group=:group")
                    .setParameter("group", group)
                    .list();
            return new HashSet<>(members);
        });
    }


    //获取一个人加入的所有群
    public static Set<GroupMember> getMembers(User user) {
        return Hib.query(session -> {
            @SuppressWarnings("unchecked")
            List<GroupMember> members = session.createQuery("from GroupMember where userId=:userId")
                    .setParameter("userId", user)
                    .list();
            return new HashSet<>(members);
        });
    }


    //通过名字查找群
    public static Group findByName(String name) {
        return Hib.query(session -> (Group) session
                .createQuery("from Group where lower(name)=:name")
                .setParameter("name", name.toLowerCase())
                .uniqueResult());
    }





    //创建群
    public static Group create(User creator, GroupCreateModel model, List<User> users) {
        Hib.query(session -> {
            Group group = new Group(creator,model);
            session.save(group);

            GroupMember ownerMember = new GroupMember(creator,group);
            //设置超级权限，创建者
            ownerMember.setPermissionType(GroupMember.PERMISSION_TYPE_ADMIN_SU);
            //保存，并没有提交到数据库

            for(User user : users){
                GroupMember member = new GroupMember(user,group);
                //保存，并没有提交到数据库
                session.save(member);
            }

//            session.flush();
//            session.load(group,group.getId());

            return group;
        });



        return null;
    }

    //获取一个群的成员
    public static GroupMember getMember(String userId, String groupId) {
        return Hib.query(session -> (GroupMember) session
                .createQuery("from GroupMember where userId=:userId and groupId=:groupId")
                .setParameter("userId", userId)
                .setParameter("groupId", groupId)
                .setMaxResults(1)
                .uniqueResult()
        );
    }


    // 查询
    @SuppressWarnings("unchecked")
    public static List<Group> search(String name) {
        if (Strings.isNullOrEmpty(name))
            name = ""; // 保证不能为null的情况，减少后面的一下判断和额外的错误
        final String searchName = "%" + name + "%"; // 模糊匹配

        return Hib.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            return (List<Group>) session.createQuery("from Group where lower(name) like :name")
                    .setParameter("name", searchName)
                    .setMaxResults(20) // 至多20条
                    .list();

        });
    }

    //给群添加用户
    public static Set<GroupMember> addMembers(Group group, List<User> insertUsers) {
        Hib.query(session -> {

            Set<GroupMember> members = new HashSet<>();

            for(User user : insertUsers){
                GroupMember member = new GroupMember(user,group);
                //保存，并没有提交到数据库
                session.save(member);
                members.add(member);
            }

            return members;
        });


        return null;
    }
}
