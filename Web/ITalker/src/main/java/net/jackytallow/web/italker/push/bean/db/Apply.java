package net.jackytallow.web.italker.push.bean.db;


import net.jackytallow.web.italker.push.bean.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 申请记录表
 */

@Entity
@Table(name = "TB_APPLY")
public class Apply {

    public static final int TYPE_ADD_USER = 1; // 添加好友
    public static final int TYPE_ADD_GROUP = 2; // 加入群


    //设置主键的Id
    @Id
    @PrimaryKeyJoinColumn
    //设置主键的Id类型为uuid
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //不允许为空，不允许为null
    @Column(updatable = false, nullable = false)
    private String id;



    //增加描述部分
    @Column(nullable = false)
    private String description;


    //附件 可为空
    //可以附带
    @Column(columnDefinition = "Text")
    private String attach;


    //当前申请的类型
    @Column(nullable = false)
    private int type;

    //目标Id不进行强关联，不建立主外键关系
    @Column(nullable = false)
    private String tragetId;

    // 定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // 定义为更新时间戳，在创建时就已经写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();


    //申请人 可为空 为系统信息
    //一个人可以有很多个申请
    @ManyToOne
    @JoinColumn(name = "applicanId")
    private User applicant;
    @Column(updatable = false,insertable = false)
    private String applicanId;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTragetId() {
        return tragetId;
    }

    public void setTragetId(String tragetId) {
        this.tragetId = tragetId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public String getApplicanId() {
        return applicanId;
    }

    public void setApplicanId(String applicanId) {
        this.applicanId = applicanId;
    }
}
