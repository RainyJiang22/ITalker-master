package net.jackytallow.web.italker.push.service;

import net.jackytallow.web.italker.push.bean.api.base.ResponseModel;
import net.jackytallow.web.italker.push.bean.api.group.GroupCreateModel;
import net.jackytallow.web.italker.push.bean.api.group.GroupMemberAddModel;
import net.jackytallow.web.italker.push.bean.api.group.GroupMemberUpdateModel;
import net.jackytallow.web.italker.push.bean.card.ApplyCard;
import net.jackytallow.web.italker.push.bean.card.GroupCard;
import net.jackytallow.web.italker.push.bean.card.GroupMemberCard;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
        return null;
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
     * @param groupId 群Id，你必须是群的管理者
     * @param model 添加成员的Model
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
     * @param memberId 群成员Id
     * @param model 更改成员的Model
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
