package net.jackytallow.web.italker.push.bean.card;


import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class UserCard {


    @Expose
    private String id;

    @Expose
    private String name;

    @Expose
    private String phone;
    @Expose
    private String portrait;
    @Expose
    private String desc;
    @Expose
    private int sex = 0;



    //用户关注人的数量
    @Expose
    private int follows;


    //用户粉丝的数量
    @Expose
    private int following;


    //我与当前User的关系状态，是否已经关注了这个用户
    @Expose
    private boolean isFollow;

    //用户信息最后的更新时间
    @Expose
    private LocalDateTime modfiyAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setisFollow(boolean follow) {
        isFollow = follow;
    }

    public LocalDateTime getModfiyAt() {
        return modfiyAt;
    }

    public void setModfiyAt(LocalDateTime modfiyAt) {
        this.modfiyAt = modfiyAt;
    }
}
