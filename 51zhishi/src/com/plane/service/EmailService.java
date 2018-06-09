package com.plane.service;

import java.util.Map;

/**
 * 邮件发送业务逻辑（一般由队列服务调用，不推荐主动调用）
 * Created by 苏正坤 on 2016/8/18.
 */

public interface EmailService
{

    /**
     * 发送简单的HTML邮件（含模板）
     * @param recipientName 收件人名称
     * @param recipientEmail 收件人邮箱
     * @param subject 邮件主题
     * @param templateName 模板名称 thymeleaf 模板
     * @param attributes 模板数据集合
     * @throws ServiceException 发送失败抛出异常
     */
    void sendSimpleMail(String recipientName, String recipientEmail, String subject, String templateName,
                        Map<String, Object> attributes) throws ServiceException;


    /**
     * 发送带内嵌图片的HTML邮件
     * @param recipientName
     * @param recipientEmail
     * @param subject
     * @param templateName
     * @param attributes
     * @param inlineImages 内嵌图片列表 （key:图片名 value:图片url) 对应模板中的图片名与图片url
     *                     <img  th:src=${image.imageName}  src="../../logo.png" />
     * @throws ServiceException
     */
    void sendInlineImageMail(String recipientName, String recipientEmail, String subject, String templateName, Map<String, Object> attributes,
                             Map<String, String> inlineImages) throws ServiceException;
    /**
     * 发送带多个附件的HTML邮件（含模板）
     * @param recipientName 收件人名称
     * @param recipientEmail 收件人邮箱
     * @param subject 邮件主题
     * @param templateName 模板名称 thymeleaf 模板
     * @param attributes 模板数据集合
     * @param filesUrl 附件列表
     * @throws ServiceException
     */
    void sendMailWithAttachment(String recipientName, String recipientEmail, String subject, String templateName, Map<String, Object> attributes,
                                String[] filesUrl) throws ServiceException;
}
