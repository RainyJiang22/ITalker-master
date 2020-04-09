package net.jacky.italker.factory.data.group;

import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.jacky.italker.factory.data.BaseDbRepository;
import net.jacky.italker.factory.data.helper.GroupHelper;
import net.jacky.italker.factory.model.db.Group;
import net.jacky.italker.factory.model.db.Group_Table;
import net.jacky.italker.factory.model.db.view.MemberUserModel;

import java.util.List;

/**
 * Group数据仓库
 *
 * @author jacky
 * @version 1.0.0
 * @date 2020/4/8
 */
public class GroupsRepository extends BaseDbRepository<Group> implements GroupsDataSource {


    @Override
    public void load(SucceedCallback<List<Group>> callback) {
        super.load(callback);

        SQLite.select()
                .from(Group.class)
                .orderBy(Group_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(Group group) {
        //一个群的信息两种情况出现
        //一个是你被别人加入群
        //另一个是自己建立一个群，无论什么情况，拿到的都只是群的信息，没有成员的信息
        //需要进行成员信息初始化

        if (group.getGroupMemberCount() > 0) {
            //已经初始化成员的信息
            group.holder = buildGroupHolder(group);
        } else {
            //待初始化的
            group.holder = null;
            GroupHelper.refreshGroupMember(group);
        }

        //所有的群组我都关注
        return true;
    }

    //初始化界面显示的成员信息
    private String buildGroupHolder(Group group) {
        List<MemberUserModel> userModels = group.getLatelyGroupMembers();
        if (userModels == null || userModels.size() == 0)
            return null;

        StringBuilder builder = new StringBuilder();
        for (MemberUserModel userModel : userModels) {
            builder.append(TextUtils.isEmpty(userModel.alias)?userModel.name:userModel.alias);
            builder.append(", ");
        }
        builder.delete(builder.lastIndexOf(", "),builder.length());

        return builder.toString();
    }
}
