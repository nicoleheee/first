package com.plane.web.resolver;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by suzhengkun on 2016/11/30.
 */
public class GlobalExceptionHandler extends SimpleMappingExceptionResolver
{
    public GlobalExceptionHandler()
    {
        setWarnLogCategory("[51zhishi系统异常]");
    }


    @Override
    protected String buildLogMessage(Exception ex, HttpServletRequest request)
    {
        return super.buildLogMessage(ex, request);
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    {
        return super.doResolveException(request, response, handler, ex);
    }
}
