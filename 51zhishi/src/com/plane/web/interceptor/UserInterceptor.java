
package com.plane.web.interceptor;

import com.plane.web.vo.UserSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class UserInterceptor implements HandlerInterceptor {

    private Log log = LogFactory.getLog(UserInterceptor.class);

    public UserInterceptor()
    {
        log.debug("管理员拦截器初始化.....");
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.debug("验证登录开始....");
        if (httpServletRequest.getSession().getAttribute(UserSession.SESSION_KEY) != null) {
            return true;
        }
        String requestUrl = httpServletRequest.getRequestURI();
        String loginUrl = httpServletRequest.getContextPath() + "/auth/loginpage";
        if (httpServletRequest.getQueryString() != null) {
            requestUrl += "?" + httpServletRequest.getQueryString();
        }
        requestUrl = URLEncoder.encode(requestUrl, "utf-8");
        loginUrl += "?reqUrl=" + requestUrl;
        log.debug(loginUrl);
        httpServletResponse.sendRedirect(loginUrl);
        return false;
    }
        @Override
        public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView
        modelAndView) throws Exception {

        }

        @Override
        public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e)
        throws Exception {

        }
}


