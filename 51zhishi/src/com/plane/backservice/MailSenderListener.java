package com.plane.backservice;

/**
 * 邮件发送事件，用于需要同步监听邮件发送状态的任务
 * Created by User on 2016/8/23.
 */
public interface MailSenderListener
{
    void sendComplete(Object mailData, boolean success, String message);
}
