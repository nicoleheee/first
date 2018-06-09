package com.plane.web.vo.doors;

import java.io.Serializable;

/**
 * Created by liubin on 2018/3/6.
 */
public class DoorsFileVO implements Serializable {

    private Long parentId;

    private String queryTitle;

    private String sort;

    private String order;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getQueryTitle() {
        return queryTitle;
    }

    public void setQueryTitle(String queryTitle) {
        this.queryTitle = queryTitle;
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
