package com.plane.web.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
    private static Log log = LogFactory.getLog(MD5.class);
    private static String SALT = "jaa.323347*()AADB";

    /**
     * MD5 +Salt 编码工具
     * @param source 源数据
     * @param salt  盐数据（固定值，用于防止md5库搜索）
     */
    public static String md5EncodeWithSalt(String source,String salt)
    {
        source=source+salt;

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(source.getBytes());

            return CommonTools.bytesToHex(digest.digest());

        } catch (NoSuchAlgorithmException e) {
            log.error("密码转密失败",e);
        }
        return null;
    }
    public static String getMD5Password(String source) {

        return  md5EncodeWithSalt(source,SALT);
    }
    public static void main(String[] args)
    {
        System.out.println(MD5.getMD5Password("111"));
    }
}
