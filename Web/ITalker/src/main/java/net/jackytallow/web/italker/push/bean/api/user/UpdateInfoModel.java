package net.jackytallow.web.italker.push.bean.api.user;


import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import net.jackytallow.web.italker.push.bean.db.User;

/**
 * 用户更新信息，完善消息的Model
 */
public class UpdateInfoModel {

    @Expose
    private String name;
    @Expose
    private String portrait;

    @Expose
    private String desc;

    @Expose
    private int sex;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    /**
     *把填充的用户信息填充到Model中
     * @param user Model
     * @return userModel
     */
    public User updateToUser(User user) {
        if (!Strings.isNullOrEmpty(name)){
            user.getName();
        }
        if (!Strings.isNullOrEmpty(portrait)){
            user.getPortrait();
        }
        if (!Strings.isNullOrEmpty(desc)){
            user.getDescription();
        }
        if (sex != 0){
            user.getSex();
        }



        return user;
    }

    //校验
    public static boolean check(UpdateInfoModel model){
        // Model 不允许为null，
        // 并且只需要具有一个及其以上的参数即可
        return model != null
                && (!Strings.isNullOrEmpty(model.name) ||
                !Strings.isNullOrEmpty(model.portrait) ||
                !Strings.isNullOrEmpty(model.desc) ||
                model.sex != 0);
    }




}
