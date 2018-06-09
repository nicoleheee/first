package com.plane.backservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送短信线程 Created by suzhengkun on 2016/8/25.
 */
public class JuheSmsSender implements Runnable
{
    //验证码的短信模板
    public static String TID_VALIDATECODE = "19219";
    //提问提示的短信模板
    public static String TID_VALIDATECODE_ADMIN="43290";

    /**
     * 聚合数据短信API KEY
     */
    private static String APP_KEY = "9b421a6636302f68b4262d7b09589734";
    private static HttpClientParams connParams = new HttpClientParams();
    private static HttpConnectionManager connectionManager = null;

    static
    {

        //初始化连接
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setDefaultMaxConnectionsPerHost(50);
        params.setMaxTotalConnections(100);

        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.setParams(params);
        connParams.setContentCharset("utf-8");


    }

    private Log log = LogFactory.getLog(JuheSmsSender.class);
    private String recvMobile;
    private String smsTemplateID = TID_VALIDATECODE; //第三方平台的短信模板ID
    private Map<String, String> tplParams = new HashMap<>();//短信平台的模板内容参数
    private String requestUrl = "http://v.juhe.cn/sms/send";
    public JuheSmsSender()
    {

    }

    public JuheSmsSender(String requestUrl)
    {
        this.requestUrl = requestUrl;
    }

    public void addParam(String name, String value)
    {
        tplParams.put(name, value);
    }

    public Map<String, String> getTplParams()
    {
        return tplParams;
    }

    public String getRecvMobile()
    {
        return recvMobile;
    }

    public void setRecvMobile(String recvMobile)
    {
        this.recvMobile = recvMobile;
    }

    public String getSmsTemplateID()
    {
        return smsTemplateID;
    }

    public void setSmsTemplateID(String smsTemplateID)
    {
        this.smsTemplateID = smsTemplateID;
    }

    @Override
    public void run()
    {

        if(log.isDebugEnabled())
        {
            log.debug("发送短信线程【"+Thread.currentThread().getName()+"】执行....");
        }
        HttpMethod httpMethod = new GetMethod(requestUrl);
        List<NameValuePair> queryList = new ArrayList<>();
        HttpParams params = new DefaultHttpParams();
        //追加参数
        queryList.add(new NameValuePair("mobile", recvMobile));
        queryList.add(new NameValuePair("tpl_id", smsTemplateID));

        if (!tplParams.isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            try
            {
                for (Map.Entry<String, String> entry : tplParams.entrySet())
                {
                    sb.append("#").append(entry.getKey()).append("#");

                    sb.append("=").append(entry.getValue()).append("&");

                }


                sb.deleteCharAt(sb.length() - 1);

                queryList.add(new NameValuePair("tpl_value", sb.toString()));

                if (log.isDebugEnabled())
                {
                    log.debug("tpl_value" + sb.toString());
                }
            } catch (Exception e)
            {

            }
        }
        queryList.add(new NameValuePair("key", APP_KEY));
        NameValuePair[] arrParam = new NameValuePair[queryList.size()];
        queryList.toArray(arrParam);
        httpMethod.setQueryString(arrParam);
        if(log.isDebugEnabled())
        {
            log.debug("开始发送短信：api参数"+httpMethod.getQueryString());
        }
        HttpClient client = new HttpClient(connParams, connectionManager);
        client.getParams().setContentCharset("utf-8");
        try
        {
            int statusCode = client.executeMethod(httpMethod);
            if (statusCode != HttpStatus.SC_OK)
            {
                log.error("发送验证码短信失败,原因：" + httpMethod.getStatusText());
                return;
            }

            String respText = httpMethod.getResponseBodyAsString();
            if(log.isDebugEnabled())
            {
                log.debug("发送短信回应数据："+respText);
            }
            ObjectMapper jsonMapper = new ObjectMapper();

            JuheResult result = jsonMapper.readValue(respText,JuheResult.class);

            if(result.getErrorCode()==0)
            {
                if(log.isDebugEnabled())
                {
                    log.debug("发送短信成功！");

                }
            }
            else
            {
                log.error("发送短信失败，错误码："+result.getErrorCode());
                log.error("错误原因:"+result.getReason());
            }



        } catch (IOException e)
        {
            log.error("发送短信失败", e);
        }
        finally
        {
            httpMethod.releaseConnection();
        }


    }


}
