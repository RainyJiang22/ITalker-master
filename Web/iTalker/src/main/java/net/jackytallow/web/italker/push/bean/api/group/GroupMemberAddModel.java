package net.jackytallow.web.italker.push.bean.api.group;

import com.google.gson.annotations.Expose;

import java.util.HashSet;
import java.util.Set;

/**
 * 群成员添加的Model
 *
 * @author Jacky
 * @version 1.0.0
 * @Date: 2020/3/1 15:46
 */
public class GroupMemberAddModel {


    @Expose
    private Set<String> users = new HashSet<>();

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }


    public static boolean check(GroupMemberAddModel model) {
        return !(model.users == null
                || model.users.size() == 0);
    }
}
