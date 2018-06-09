package com.plane.web.vo.prod;

import java.io.Serializable;


public class ProdMixFileVO implements Serializable {

    private Long parentId;

    private String queryProdMixName;

    private String sort;

    private String order;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getQueryProdMixName() {
        return queryProdMixName;
    }

    public void setQueryProdMixName(String queryProdMixName) {
        this.queryProdMixName = queryProdMixName;
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
