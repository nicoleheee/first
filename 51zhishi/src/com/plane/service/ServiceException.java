package com.plane.service;

/**
 * Created by su on 16/7/29.
 * 自定义服务器异常
 */
public class ServiceException extends Exception {
    private  int errorCode = ServiceErrorCode.UnkownError;

    public ServiceException(int errorCode)
    {

        this.errorCode = errorCode;
    }
    public ServiceException(String message,int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode= errorCode;
    }


    public int getErrorCode() {

        return errorCode;
    }

    @Override
    public String toString()
    {
       return super.toString() +"errorCode:"+errorCode;
    }
}
