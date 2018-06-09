package com.plane.entity.pbs;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liubin on 2018/3/14.
 */
public class PBS implements Serializable{

    private Long id;

    private String pbsName;

    private String content;

    private String version;

    private Long creatorId;

    private Date createTime;

    private Long parentId;

    private String userName;

    private int state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPbsName() {
        return pbsName;
    }

    public void setPbsName(String pbsName) {
        this.pbsName = pbsName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
