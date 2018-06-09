package com.plane.service;

/**
 * Created by liubin on 2017/2/21.
 */
public interface BasicConstant {

     int UNEXIST_ID=0x0000;

     Integer NULL_ID=null;

     int MAJAR_EBABLE=0x0000;

     int MAJAR_DISABLE=0x0001;

     interface Page{

          int PAGE_NO=0x0001;

          int PAGE_SIZE=0x000A;

          int INTEREST_SIZE=0x0005;

          int TOPIC_SIZE=0x0005;

          int PRODUCT_SIZE=0x0010;

          int URGENT_QA_SIZE=0x0010;

          int NEWEST_QA_SIZE=0x00014;

          int USER_MESSAGE_SIZE=0x0003;

          int WAIT_QA_SIZE=0x0005;

          int Group_QA_SIZE=0x0010;

          int GROUP_SIZE=0x0015;

          int INVITE_USER_SIZE=0x0005;

          int USER_QA_SIZE=0x000C;

          int COMMISSION_SIZE=0x0014;

          int Group_QAPROD_SIZE=0x00010;

          int USER_QAPROD_SIZE=0x0004;
     }


     interface ResultState{

          int SUCCESS_STATE=0x0000;

          int ERROR_STATE=-0x0001;

          int EXIST_STATE=-0x0002;

          int NULL_STATE=-0x0003;

          int NON_SELF=-0x0004;
     }


}
