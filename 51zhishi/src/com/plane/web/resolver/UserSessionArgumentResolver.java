package com.plane.web.resolver;

import com.plane.web.vo.UserSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 自动注入用户session对象
 * Created by suzhengkun on 2016/8/13.
 */
public class UserSessionArgumentResolver implements WebArgumentResolver
{
    private  static Log log = LogFactory.getLog(UserSessionArgumentResolver.class);

    public  UserSessionArgumentResolver()
    {
        log.info("用户在线对象处理类初始化...");
    }
    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest nativeWebRequest) throws Exception
    {
        if(UserSession.class.equals(methodParameter.getParameterType()) )
        {
            log.debug("处理UserSession类型数据");
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            UserSession  userSession =(UserSession)request.getSession().getAttribute(UserSession.SESSION_KEY);
            if(log.isDebugEnabled())
            {
                log.debug("从session处获得userSession:" + userSession);
            }
            if(userSession==null)
            {
                userSession = new UserSession();
            }
            return userSession;

        }
        return UNRESOLVED;
    }
}
