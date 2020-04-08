package net.jacky.italker.factory.model.db.view;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;

import net.jacky.italker.factory.model.db.AppDatabase;

/**
 * 群成员对应的用户的简单信息表
 * @author jacky
 * @version 1.0.0
 * @date 2020/4/8
 */
@QueryModel(database = AppDatabase.class)
public class MemberUserModel {
    @Column
    public String userId; //User-id或者Member-id
    @Column
    public String name; //User表中的name
    @Column
    public String alias; //备注名
    @Column
    public String portrait; //User-portrait



}
