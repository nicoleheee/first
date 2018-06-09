package com.plane.web.vo;


/**
 * Created by su on 16/6/30. 用于返回操作结果的json对象，例如添加用户，修改书籍，添加书籍等操作结果
 */
public class JsonResult
{
    private boolean needLogin;

    private boolean success;

    private String errorMsg;

    private Object extendData;

    private int code;



    public JsonResult(boolean success)

    {
        this.success = success;
    }

    public JsonResult(boolean success, String errorMsg)
    {
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    /**
     * 其它数据
     */
    public Object getExtendData()
    {
        return extendData;
    }

    public void setExtendData(Object extendData)
    {
        this.extendData = extendData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
