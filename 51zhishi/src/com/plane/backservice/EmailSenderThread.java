package com.plane.backservice;

import com.plane.entity.MailData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PreDestroy;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

/**
 * 邮件发送线程 Created by user on 2016/8/19.
 */

public class EmailSenderThread implements InitializingBean, Runnable
{
    public static String QUEUE_Mail = "email.queue";
    private static int TIMEOUT = 5 * 60;//5分钟
    private static int BACTH_COUNT = 10; //一次会话发送10封邮件
    private Log log = LogFactory.getLog(EmailSenderThread.class);
    private boolean breaking = false;

    private String smtpHost;
    private int smtpPort = 465; //tls端口
    private String userName;
    private String password;
    private boolean enableSSL = true;

    private String mailFromName;

    private String mailFromEmail;


    @Autowired
    private ThreadPoolTaskExecutor mailTaskExecutor = null;


    public boolean isBreaking()
    {
        return breaking;
    }

    public void setBreaking(boolean breaking)
    {
        this.breaking = breaking;
    }

    public String getSmtpHost()
    {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost)
    {
        this.smtpHost = smtpHost;
    }

    public int getSmtpPort()
    {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort)
    {
        this.smtpPort = smtpPort;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isEnableSSL()
    {
        return enableSSL;
    }

    public void setEnableSSL(boolean enableSSL)
    {
        this.enableSSL = enableSSL;
    }


    public String getMailFromName()
    {
        return mailFromName;
    }

    public void setMailFromName(String mailFromName)
    {
        this.mailFromName = mailFromName;
    }

    public String getMailFromEmail()
    {
        return mailFromEmail;
    }

    public void setMailFromEmail(String mailFromEmail)
    {
        this.mailFromEmail = mailFromEmail;
    }

    /**
     * 启动服务线程
     */
    public void start()
    {
        if (smtpHost == null || smtpPort <= 0)
        {
            log.error("smtp服务器地址未配置或端口配置错误");
            return;
        }

        //启动一个线程
        for(int i=0;i<5;i++)
        {
            mailTaskExecutor.execute(this);
        }
    }


    @Override
    public void run()
    {
        BlockingQueue<MailData> queue = QueueTool.getQueue(MailData.class, QUEUE_Mail);
        int count = 0;
        Session session = null;
        long lastSend = System.currentTimeMillis();
        log.info("邮件发送线程[" + Thread.currentThread().getName() + "]启动...");
        while (true)
        {
            try
            {
                MailData data = queue.take();
                int distance = (int) (System.currentTimeMillis() - lastSend) / 1000;
                if (distance > TIMEOUT || count > BACTH_COUNT)
                {
                    if (session != null)
                    {
                        try
                        {
                            session.getTransport().close();
                        } catch (MessagingException e)
                        {
                            log.warn("关闭邮件Transport出错");
                        }
                        session = null;
                        count = 0;
                    }
                }

                try
                {
                    HtmlEmail email = createEmail(data, session);

                    if (session == null)
                    {
                        session = email.getMailSession();
                        session.getTransport().connect();
                    }
                    //发送邮件，不关闭连接
                    Transport transport = session.getTransport();
                    transport.connect();
                    transport.sendMessage(email.getMimeMessage(),
                                                       email.getMimeMessage().getAllRecipients());
                    //
                    count++;
                    lastSend = System.currentTimeMillis();
                    notifyListeners(data, true, null);
                } catch (EmailException e)
                {
                    log.error("发送邮件失败", e);
                    notifyListeners(data, false, e.getMessage());

                } catch (NoSuchProviderException e)
                {
                    log.error("发送邮件失败", e);
                    notifyListeners(data, false, e.getMessage());
                } catch (MessagingException e)
                {
                    log.error("发送邮件失败", e);
                    notifyListeners(data, false, e.getMessage());
                }


            } catch (InterruptedException e)
            {

                break;
            }


        }

        log.info("邮件发送线程[" + Thread.currentThread().getName() + "]退出...");
    }


    private void notifyListeners(MailData data, boolean result, String message)
    {
        if (data.getMailSenderListeners().isEmpty())
        {
            return;
        }

        for (MailSenderListener listener : data.getMailSenderListeners())
        {
            try
            {
                listener.sendComplete(data, result, message);

            } catch (Exception e)
            {

            }
        }


    }


    @Override
    public void afterPropertiesSet() throws Exception
    {
        log.info("启动邮件发送线程....");
        start();
    }


    private HtmlEmail createEmail(MailData data, Session session) throws EmailException
    {
        HtmlEmail email = new HtmlEmail();
        email.setCharset("utf-8");
        if (session == null)
        {
            email.setHostName(smtpHost);
            if (smtpPort != 465)
            {
                email.setSslSmtpPort(smtpPort + "");
            }
            if (userName != null)
            {
                email.setAuthentication(userName, password);
            }
            email.setSSLOnConnect(enableSSL);
        }
        else
        {
            email.setMailSession(session);
        }

        email.setSubject(data.getSubject());//邮件主题
        //使用默认配置
        if (data.getFromEmail() == null)
        {
            data.setFromEmail(getMailFromEmail());
            data.setFromName(getMailFromName());
        }
        email.setFrom(data.getFromEmail(), data.getFromName(), "utf-8");

        email.setTo(data.getToList());


        //处理嵌入内容，如图片等
        if (data.getEmberedUrlLst() != null && !data.getEmberedUrlLst().isEmpty())
        {

            for (String url : data.getEmberedUrlLst())
            {

                try
                {
                    String cid = email.embed(url, url.substring(url.lastIndexOf("/")));
                    data.setHtmlContent(
                            data.getHtmlContent().replace(url, "cid:" + cid)); //替换html中的url
                } catch (EmailException e)
                {
                    log.error("创建嵌入资源出错！", e);
                    throw e;
                }

            }

        }

        email.setHtmlMsg(data.getHtmlContent());
        email.setTextMsg("您的客户端不支持html邮件浏览");

        //处理邮件附件
        if (data.getAttachmentUrlList() != null && !data.getAttachmentUrlList().isEmpty())
        {
            for (String fileUrl : data.getAttachmentUrlList())
            {
                EmailAttachment attachment = new EmailAttachment();
                try
                {
                    attachment.setURL(new URL(fileUrl));
                } catch (MalformedURLException e)
                {
                    log.error("附件url不合法", e);
                    throw new EmailException("追加附件失败，附件url不合法", e);
                }
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));
                attachment.setDescription(fileName);
                attachment.setName(fileName);
                email.attach(attachment); //追加一份附件

            }


        }

        //构建email
        email.buildMimeMessage();

        return email;
    }


    @PreDestroy
    public void destroyThread()
    {
        log.info("邮件发送线程销毁...");
    }
}
