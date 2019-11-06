package net.qiujuer.web.italker.push.bean.db;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_MESSAGE")
public class GroupMessage{

    public static final int TYPE_STR = 1; //文字类型
    public static final int TYPE_PIC = 2; //图片类型
    public static final int TYPE_ATUO = 3; //语音类型
    public static final int TYPE_FILE = 4; //文件类型
    public static final int TYPE_VIDEO = 5; //视频类型

    private String id;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
