package net.jackytallow.web.italker.push.bean.api.group;

import com.google.gson.annotations.Expose;

/**
 * 更新群成员信息的Model
 * @author Jacky
 * @version 1.0.0
 * @Date: 2020/3/1 15:50
 */
public class GroupMemberUpdateModel {

    @Expose
    private String name;

    @Expose
    private String raskName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRaskName() {
        return raskName;
    }

    public void setRaskName(String raskName) {
        this.raskName = raskName;
    }
}
