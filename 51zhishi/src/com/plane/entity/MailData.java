package com.plane.entity;

import com.plane.backservice.MailSenderListener;
import org.apache.commons.mail.EmailException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * EMail数据 用于在邮件任务中传输和缓存数据
 * Created by suzhengkun on 2016/8/18.
 */
public class MailData implements Serializable
{
    private String fromName;
    private String fromEmail;
    private String toName;
    private String toEmail;
    private List<InternetAddress> toList = new ArrayList<>();
    private String subject;
    private String htmlContent;
    private List<String> attachmentUrlList = new ArrayList<>();
    private List<String> emberedUrlLst = new ArrayList<>(); //嵌入在email中的图片等url资源列表

    private List<MailSenderListener> mailSenderListeners = new ArrayList<>();
    public MailData()
    {

    }

    /**
     * 创建html邮件内容
     * @param toName
     * @param toEmail
     * @param subject
     * @param htmlContent
     */
    public MailData(String toName, String toEmail,
                    String subject, String htmlContent)
    {

        this.toName = toName;
        this.toEmail = toEmail;

        this.subject = subject;
        this.htmlContent = htmlContent;
        if(toEmail!=null)
        {
            addTo(toName,toEmail);
        }
    }


    public MailData(String fromName, String fromEmail, String toName, String toEmail,
                    String subject)
    {
        this.fromName = fromName;
        this.fromEmail = fromEmail;
        this.toName = toName;
        this.toEmail = toEmail;
        this.subject = subject;
        if(toEmail!=null)
        {
            addTo(toName,toEmail);
        }
    }


    public void addSenderListener(MailSenderListener listener)
    {
        mailSenderListeners.add(listener);
    }

    public void removeSenderListener(MailSenderListener listener)
    {
        mailSenderListeners.remove(listener);
    }

    public List<MailSenderListener> getMailSenderListeners()
    {
        return mailSenderListeners;
    }

    public String getFromName()
    {
        return fromName;
    }

    public void setFromName(String fromName)
    {
        this.fromName = fromName;
    }

    public String getFromEmail()
    {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail)
    {
        this.fromEmail = fromEmail;
    }

    public List<InternetAddress> getToList()
    {
        return toList;
    }

   public List<InternetAddress> addTo(String name, String email)
   {
       try
       {
           InternetAddress address = new InternetAddress(email,name,"utf-8");
           this.getToList().add(address);
       } catch (UnsupportedEncodingException e)
       {

       }
       return this.getToList();
   }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getHtmlContent()
    {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent)
    {
        this.htmlContent = htmlContent;
    }

    public List<String> getAttachmentUrlList()
    {
        return attachmentUrlList;
    }



    public void setAttachmentUrlList(List<String> attachmentUrlList)
    {
        this.attachmentUrlList = attachmentUrlList;
    }

    /**
     * 获取嵌入在html中的资源（图片等）
     * @return
     */
    public List<String> getEmberedUrlLst()
    {
        return emberedUrlLst;
    }

    public void setEmberedUrlLst(List<String> emberedUrlLst)
    {
        this.emberedUrlLst = emberedUrlLst;
    }

    private InternetAddress createInternetAddress(final String email, final String name, final String charsetName)
            throws EmailException
    {
        InternetAddress address = null;

        try
        {
            address = new InternetAddress(email);

            // check name input
            if (isNotEmpty(name))
            {
                // check charset input.
                if (isEmpty(charsetName))
                {
                    address.setPersonal(name);
                }
                else
                {
                    // canonicalize the charset name and make sure
                    // the current platform supports it.
                    final Charset set = Charset.forName(charsetName);
                    address.setPersonal(name, set.name());
                }
            }

            // run sanity check on new InternetAddress object; if this fails
            // it will throw AddressException.
            address.validate();
        }
        catch (final AddressException e)
        {
            throw new EmailException(e);
        }
        catch (final UnsupportedEncodingException e)
        {
            throw new EmailException(e);
        }
        return address;
    }

    static boolean isNotEmpty(final String str)
    {
        return (str != null) && (str.length() > 0);
    }
    static boolean isEmpty(final String str)
    {
        return (str == null) || (str.length() == 0);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof MailData))
        {
            return false;
        }

        MailData mailData = (MailData) o;

        if (fromName != null ? !fromName.equals(mailData.fromName) : mailData.fromName != null)
        {
            return false;
        }
        if (!fromEmail.equals(mailData.fromEmail))
        {
            return false;
        }
        if (toName != null ? !toName.equals(mailData.toName) : mailData.toName != null)
        {
            return false;
        }
        if (!toEmail.equals(mailData.toEmail))
        {
            return false;
        }
        if (toList != null ? !toList.equals(mailData.toList) : mailData.toList != null)
        {
            return false;
        }
        if (!subject.equals(mailData.subject))
        {
            return false;
        }
        if (htmlContent != null ? !htmlContent
                .equals(mailData.htmlContent) : mailData.htmlContent != null)
        {
            return false;
        }
        return attachmentUrlList != null ? attachmentUrlList
                .equals(mailData.attachmentUrlList) : mailData.attachmentUrlList == null;

    }

    @Override
    public int hashCode()
    {
        int result = fromName != null ? fromName.hashCode() : 0;
        result = 31 * result + fromEmail.hashCode();
        result = 31 * result + (toName != null ? toName.hashCode() : 0);
        result = 31 * result + toEmail.hashCode();
        result = 31 * result + (toList != null ? toList.hashCode() : 0);
        result = 31 * result + subject.hashCode();
        result = 31 * result + (htmlContent != null ? htmlContent.hashCode() : 0);
        result = 31 * result + (attachmentUrlList != null ? attachmentUrlList.hashCode() : 0);
        return result;
    }
}
