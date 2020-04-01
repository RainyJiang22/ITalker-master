package net.jackytallow.web.italker.push.service;

import net.jackytallow.web.italker.push.bean.api.base.ResponseModel;
import net.jackytallow.web.italker.push.bean.api.group.GroupCreateModel;
import net.jackytallow.web.italker.push.bean.api.group.GroupMemberAddModel;
import net.jackytallow.web.italker.push.bean.api.group.GroupMemberUpdateModel;
import net.jackytallow.web.italker.push.bean.card.ApplyCard;
import net.jackytallow.web.italker.push.bean.card.GroupCard;
import net.jackytallow.web.italker.push.bean.card.GroupMemberCard;
import net.jackytallow.web.italker.push.bean.db.Group;
import net.jackytallow.web.italker.push.bean.db.GroupMember;
import net.jackytallow.web.italker.push.bean.db.User;
import net.jackytallow.web.italker.push.factory.GroupFactory;
import net.jackytallow.web.italker.push.factory.PushFactory;
import net.jackytallow.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jacky
 * @version 1.0.0
 * @Date: 2020/2/24 22:03
 * 群组的接口的入口
 */
@Path("/group")
public class GroupService extends BaseService {


    @POST
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //创建一个群
    public ResponseModel<GroupCard> create(GroupCreateModel model) {
        if (!GroupCreateModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        //创建者
        User creator = getSelf();
        //创建者并不在列表中
        model.getUsers().remove(creator.getId());
        if (model.getUsers().size() == 0)
            return ResponseModel.buildParameterError();

        //检查是否已有
        if (GroupFactory.findByName(model.getName()) != null) {
            return ResponseModel.buildParameterError();
        }

        List<User> users = new ArrayList<>();
        for (String s : model.getUsers()) {
            User user = UserFactory.findById(s);
            if (user == null)
                continue;
            users.add(user);
        }
        //没有一个成员
        if (users.size() == 0) {
            return ResponseModel.buildParameterError();
        }

        Group group = GroupFactory.create(creator, model, users);
        if (group == null) {
            //服务器异常
            return ResponseModel.buildServiceError();
        }


        //拿管理员的信息，自己的信息
        GroupMember creatorMember = GroupFactory.getMember(creator.getId(), group.getId());
        if (creatorMember == null) {
            //服务器异常
            return ResponseModel.buildParameterError();
        }

        //拿到群的成员，给所有的群成员发送信息，已经被添加到群的信息
        Set<GroupMember> members = GroupFactory.getMembers(group);
        if (members == null) {
            //服务器异常
            return ResponseModel.buildParameterError();
        }
        members = members.stream()
                .filter(groupMember -> groupMember.getId().equalsIgnoreCase(creatorMember.getId()))
                .collect(Collectors.toSet());

        //开始发起推送
        PushFactory.pushGroupAdd(members);

        return ResponseModel.buildOk(new GroupCard(creatorMember));
    }

    //搜索一个群

    /**
     * 搜索当前群的一个列表
     *
     * @param name 群的名字
     * @return 当前群信息列表
     */
    @GET
    @Path("/search/{name:(.*)?}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<GroupCard>> search(@PathParam("name") @DefaultValue("") String name) {
        return null;
    }

    //查看群的列表
    @GET
    @Path("/search/{date:(.*)?}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<GroupCard>> list(@DefaultValue("") @PathParam("date") String dateStr) {
        return null;
    }

    //获取一个群的消息
    @GET
    @Path("{groupId}")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<GroupCard> getGroup(@PathParam("groupId") String id) {
        return null;
    }


    /**
     * 拉取一个群的所有成员，你必须是成员之一
     *
     * @param groupId 群Id
     * @return 成员列表
     */
    @GET
    @Path("{groupId}/members")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //获取群组成员的信息
    public ResponseModel<List<GroupMemberCard>> members(@PathParam("groupId") String groupId) {
        return null;
    }

    /**
     * 给群添加成员的接口，请求的人要么是管理员，要么是群成员本人
     *
     * @param groupId 群Id，你必须是群的管理者
     * @param model   添加成员的Model
     * @return 添加的成员列表
     */
    @GET
    @Path("{groupId}/member")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //添加一个群成员
    public ResponseModel<List<GroupMemberCard>> memberAdd(@PathParam("groupId") String groupId, GroupMemberAddModel model) {

        return null;
    }

    /**
     * 更改成员的信息接口
     *
     * @param memberId 群成员Id
     * @param model    更改成员的Model
     * @return 群成员的信息
     */
    @PUT
    @Path("member/{memberId}")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //修改群成员的信息（备注）
    public ResponseModel<GroupMemberCard> modifyMember(@PathParam("memberId") String memberId, GroupMemberUpdateModel model) {

        return null;
    }

    @POST
    @Path("applyJoin/{groupId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //加入一个群
    public ResponseModel<ApplyCard> join(@PathParam("groupId") String groupId) {

        return null;
    }
}
