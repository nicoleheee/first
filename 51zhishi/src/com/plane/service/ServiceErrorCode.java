package com.plane.service;

/**
 * Created by su on 16/7/29.
 */
public interface ServiceErrorCode {
    /**
     * 未知错误
     */
    int UnkownError = 0xFFFF;
    /**
     * 数据库访问错误
     */
    int DataBaseError = 0xFFFE;
    /**
     * 查询数据为空
     */
    int NullError=0xFFEE;

    /**
     * 名称重名
     */
    int NameExists=0xFFFD;

    int EmailExists=0x10000 ;

    int UploadErrorFileEmpty = 0xFFFD;

    int UploadErrorFileIO = 0xFFFC;

    int UploadErrorFileMaxLength = 0xFFFB;

    int UploadErrorNonSelf=0xFFFA;

    interface AuthService
    {
        int AuthError = 0x0001; //验证用户名密码出错

        int CodeError=0x0002;//验证码错误

    }
    interface  CenterService
    {
        int FullNameError= 0x0010;//真实姓名相同,不需要修改

        int MobileError= 0x0011;//手机存在，不需要修改

        int EmailError=0x0012;//邮箱相同，不需要修改
    }

    interface EmailService
    {
        int QueueFull = 0xEE00; //发送队列已满
    }
}
