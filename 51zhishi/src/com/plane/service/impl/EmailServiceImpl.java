package com.plane.service.impl;

import com.plane.backservice.EmailSenderThread;
import com.plane.backservice.QueueTool;
import com.plane.entity.MailData;
import com.plane.service.EmailService;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by User on 2016/8/18.
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService
{


    @Autowired
    private TemplateEngine templateEngine = null;

   /* @Autowired
    private HttpServletRequest request;*/

    /**
     * 发送简单的HTML邮件（含模板）
     *
     * @param recipientName  收件人名称
     * @param recipientEmail 收件人邮箱
     * @param subject        邮件主题
     * @param templateName   模板名称 thymeleaf 模板
     * @param attributes     模板数据集合
     * @throws ServiceException 发送失败抛出异常
     */
    @Override
    public void sendSimpleMail(String recipientName, String recipientEmail, String subject,
                               String templateName,
                               Map<String, Object> attributes) throws ServiceException
    {
        if(StringUtils.isEmpty(recipientEmail)){
            return;
        }
        sendMail0(recipientName,recipientEmail,subject,templateName,attributes,null,null);


    }

    /**
     * 发送带内嵌图片的HTML邮件
     *
     * @param inlineImages 内嵌图片列表 （key:图片名 value:图片url) 对应模板中的图片名与图片url <img
     *                     th:src=${image.imageName}  src="../../logo.png" />
     */
    @Override
    public void sendInlineImageMail(String recipientName, String recipientEmail, String subject,
                                    String templateName, Map<String, Object> attributes,
                                    Map<String, String> inlineImages) throws ServiceException
    {

        sendMail0(recipientName,recipientEmail,subject,templateName,attributes,inlineImages,null);

    }

    /**
     * 发送带多个附件的HTML邮件（含模板）
     *
     * @param recipientName  收件人名称
     * @param recipientEmail 收件人邮箱
     * @param subject        邮件主题
     * @param templateName   模板名称 thymeleaf 模板
     * @param attributes     模板数据集合
     * @param filesUrl          附件列表
     */
    @Override
    public void sendMailWithAttachment(String recipientName, String recipientEmail, String subject,
                                       String templateName, Map<String, Object> attributes,
                                      String[] filesUrl) throws ServiceException
    {

        sendMail0(recipientName,recipientEmail,subject,templateName,attributes,null,filesUrl);
    }


    private  void sendMail0(String recipientName, String recipientEmail, String subject,
                            String templateName, Map<String, Object> attributes,
                            Map<String, String> inlineImages,String[] filesUrl)throws ServiceException
    {
        Context context = new Context(Locale.getDefault());
        if (attributes != null && !attributes.isEmpty())
        {
            for (Map.Entry<String, Object> entry : attributes.entrySet())
            {
                context.setVariable(entry.getKey(), entry.getValue());

            }
        }

        if(inlineImages!=null)
        {
            context.setVariable("image",inlineImages); //将嵌入内容加入到模板环境
        }

        String htmlContent = templateEngine.process(templateName, context);

        MailData mailData = new MailData(recipientName, recipientEmail, subject, htmlContent);

        if(filesUrl !=null)
        {
            Collections.addAll(mailData.getAttachmentUrlList(), filesUrl);
        }
        if(inlineImages!=null)
        {
            mailData.setEmberedUrlLst(new ArrayList<>(inlineImages.values()));
        }


        //获取队列，插入数据
        BlockingQueue<MailData> queue = QueueTool
                .getQueue(MailData.class, EmailSenderThread.QUEUE_Mail);

        if( queue.offer(mailData)==false)
        {
            throw new ServiceException("发送邮件失败，队列已满,请稍后再试", ServiceErrorCode.EmailService.QueueFull);
        }

    }


}
