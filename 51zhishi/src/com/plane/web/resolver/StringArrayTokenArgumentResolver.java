package com.plane.web.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Created by suzhengkun on 2016/9/29.
 * 将页面中传输过来的以逗号分割的字符串值，转为数组类型的字符串数组对象
 */
public class StringArrayTokenArgumentResolver implements WebArgumentResolver
{


    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest nativeWebRequest) throws Exception
    {

        if(String[].class.equals(methodParameter.getParameterType()))
        {
            String paramvalue = nativeWebRequest.getParameter(methodParameter.getParameterName());
            return StringUtils.split(paramvalue,",");

        }


        return UNRESOLVED;
    }
}
