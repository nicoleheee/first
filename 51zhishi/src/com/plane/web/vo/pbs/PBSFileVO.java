package com.plane.web.vo.pbs;

import java.io.Serializable;

/**
 * Created by liubin on 2018/3/6.
 */
public class PBSFileVO implements Serializable {

    private Long parentId;

    private String queryPBSName;

    private String sort;

    private String order;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getQueryPBSName() {
        return queryPBSName;
    }

    public void setQueryPBSName(String queryPBSName) {
        this.queryPBSName = queryPBSName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
