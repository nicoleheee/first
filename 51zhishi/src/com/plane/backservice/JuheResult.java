package com.plane.backservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 聚合数据接口返回的JSON对象
 * Created by suzhengkun on 2016/8/26.
 */
public class JuheResult
{
    private Object reason;
    private int errorCode;
    private Object result;

    public Object getReason()
    {
        return reason;
    }

    public void setReason(Object reason)
    {
        this.reason = reason;
    }

    @JsonProperty("error_code")
    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public Object getResult()
    {
        return result;
    }

    public void setResult(Object result)
    {
        this.result = result;
    }
}
