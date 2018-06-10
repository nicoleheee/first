package com.plane.web.util;

import java.util.List;
import java.util.ArrayList;

public class PageUtil
{
    public static List<Integer> getPageList(int totCount, int pageSize){
        List<Integer> pageList=new ArrayList<>();
        if(totCount!=0){
            int pageCount = totCount/pageSize + (totCount%pageSize==0?0:1);
            //奇数偶数
            for (int i=1;i<=pageCount; i++){
                pageList.add(i);
            }
        }
        return pageList;
        //返回Pagelist
    }


}
