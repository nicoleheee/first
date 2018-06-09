package com.plane.web.vo.user;

import java.io.Serializable;

/**
 * Created by user on 2018/3/19.
 */
public class UserFileVO implements Serializable {

    private String queryUserName;

    private String queryEmail;

    private String sort;

    private String order;


    public String getQueryUserName() {
        return queryUserName;
    }

    public void setQueryUserName(String queryUserName) {
        this.queryUserName = queryUserName;
    }

    public String getQueryEmail() {
        return queryEmail;
    }

    public void setQueryEmail(String queryEmail) {
        this.queryEmail = queryEmail;
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
