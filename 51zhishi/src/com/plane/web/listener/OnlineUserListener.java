package com.plane.web.listener;

import com.plane.web.vo.UserSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liubin on 2017/4/25.
 */
/*监听在线用户*/

public class OnlineUserListener implements HttpSessionListener,HttpSessionAttributeListener {
    private Log log= LogFactory.getLog(OnlineUserListener.class);

    private Map<String, HttpSession> map = new HashMap<String, HttpSession>();

    public OnlineUserListener() {
        super();
    }


    /*创建session时触发*/
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        log.info("session创建");
    }

    /*销毁session时触发*/
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        log.info("session销毁");
    }

    /*添加属性时触发*/
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
       /* log.info("sessionid"+httpSessionBindingEvent.getSession().getId());
        if(httpSessionBindingEvent.getName().equals(UserSession.SESSION_KEY)){
            map.put(httpSessionBindingEvent.getSession().getId(),
                    (UserSession)httpSessionBindingEvent.getSession().getAttribute(UserSession.SESSION_KEY));
            OnlineUserList.getInstance().setUserSessions(map);
            log.info("用户属性创建");
            return;
        }*/
        log.info("新的属性创建");
        if (httpSessionBindingEvent.getName().equals(UserSession.SESSION_KEY)) {
            UserSession user = (UserSession) httpSessionBindingEvent.getValue();
            log.debug(user.getLoginName());
            if (map.get(user.getLoginName()) != null) {
                HttpSession session = map.get(user.getLoginName());
                session.removeAttribute(user.getLoginName());
                session.invalidate();
            }
            map.put(user.getLoginName(), httpSessionBindingEvent.getSession());
        }
    }
    //js 1每隔五秒发送一个修改时间戳的请求
    //js 2每隔五秒请求在线人数
    //java 3超过十秒 判定用户不在线
    /*移除属性时触发*/
    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        log.info("sessionid"+httpSessionBindingEvent.getSession().getId());
        log.info("属性移除");
        if (httpSessionBindingEvent.getName().equals(UserSession.SESSION_KEY)) {
            UserSession user = (UserSession) httpSessionBindingEvent.getValue();
            map.remove(user.getLoginName());
        }
    }

    /*替换属性时触发*/
    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        log.info("属性替换");
    }


}
