package com.plane.entity.prod;

import java.io.Serializable;
import java.util.Date;


public class ProdMix implements Serializable {

    private Long id;

    private String prodMixName;

    private String content;

    private Date createTime;

    private Long creatorId;

    private Long parentId;

    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdMixName() {
        return prodMixName;
    }

    public void setProdMixName(String prodMixName) {
        this.prodMixName = prodMixName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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
}
