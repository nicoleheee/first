package com.plane.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具库
 * Created by suzhengkun on 2016/8/17.
 */
public class CommonTools
{
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public synchronized static String  keyByTimes()
    {
        SimpleDateFormat df = new SimpleDateFormat("MMddHHmmssS");
        return  df.format(new Date());
    }

    public static  long getFileLength(String fileLenStr)
    {
        Pattern pattern=  Pattern.compile("(\\d+)([m|M])");

        Matcher mather = pattern.matcher(fileLenStr);
        if(mather.find()){

            String length =  mather.group(1);


            return Long.valueOf(length)*1024*1024L;
        }
        return -1L;

    }

    public  static  void main(String[] args)
    {
        System.out.println(getFileLength("2000K"));
    }
}
