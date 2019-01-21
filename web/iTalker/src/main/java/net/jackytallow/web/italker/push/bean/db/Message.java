package net.jackytallow.web.italker.push.bean.db;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 消息的Model,对应数据库
 */
@Entity
@Table(name = "TB_MESSAGE")
public class Message {
   public static final int TYPE_STR = 1; //字符串类型
    public static final int TYPE_PIC = 2; //图片类型
    public static final int TYPE_FILE = 3; //文件类型
    public static final int TYPE_AUDIO = 4;  //语音类型


    // 这是一个主键
    @Id
    @PrimaryKeyJoinColumn
    // 主键生成存储的类型为UUID
    //这里不自动生成UUID，Id由代码写入，由客户端负责生成
    //避免复杂的服务器和客户端的映射关系
    //    @GeneratedValue(generator = "uuid")
    // 把uuid的生成器定义为uuid2，uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    // 不允许更改，不允许为null
    @Column(updatable = false, nullable = false)
    private String id;


    //内容不允许为空,类型为空
    @Column(nullable = false,columnDefinition = "TEXT")
    private String content;

    //可以为空
    @Column
    private String attach;

    //类型不允许为空
    @Column(nullable = false)
    private String type;

    // 定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // 定义为更新时间戳，在创建时就已经写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();


    //发送者
    //多个消息对应一个发送者
    @JoinColumn(name = "senderId")
    //多对1 ->多条消息对应当前用户ID
    @ManyToOne(optional = false)
    private User sender;
    //这个字段仅仅知识为了对应sender的数据库字段senderId
    //不允许手动的更新或者插入
    @Column(nullable = false,updatable = false,insertable = false)
    private String senderId;

    //接收者 可以为空
    //多个消息对应一个接收者
    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User receiver;
    @Column(updatable = false,insertable = false)
    private String recevierId;


     //一个群可以接收多个消息
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;
    @Column(updatable = false,insertable = false)
    private String groupId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getRecevierId() {
        return recevierId;
    }

    public void setRecevierId(String recevierId) {
        this.recevierId = recevierId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
