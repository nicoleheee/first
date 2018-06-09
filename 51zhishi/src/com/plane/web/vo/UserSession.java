package com.plane.web.vo;

import java.io.Serializable;


public class UserSession implements Serializable
{
    public static String SESSION_KEY = "session.user";

    private Long uid;

    private String loginName;

    private Long userLogoId;

    public UserSession() {

    }
    public UserSession(Long uid, String loginName){
        this.uid=uid;
        this.loginName=loginName;
    }

    public Long getUid()
    {
        return uid;
    }

    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public Long getUserLogoId() {
        return userLogoId;
    }

    public void setUserLogoId(Long userLogoId) {
        this.userLogoId = userLogoId;
    }
}
