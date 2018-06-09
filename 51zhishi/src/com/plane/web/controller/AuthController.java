package com.plane.web.controller;

import com.plane.entity.User;
import com.plane.service.ServiceException;
import com.plane.service.doors.DoorsService;
import com.plane.web.vo.UserSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by liubin on 2018/3/8.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private DoorsService doorsService;

    @RequestMapping("/login")
    public String login(HttpSession session, User user, Model model, String url){
        try {
            Long id=doorsService.checkUser(user);
            if(id!=null){
                //登录成功
                UserSession userSession=new UserSession(id,user.getUserName());
                session.setAttribute(UserSession.SESSION_KEY,userSession);
                if(!StringUtils.isBlank(url)){
                    return "redirect:"+url;
                }
                return "redirect:/";
            }
            model.addAttribute("error", "用户名和密码不匹配");
            return "/tpls/login";
        }catch (ServiceException e) {
            model.addAttribute("error", "用户名和密码不匹配");
            return "/tpls/login";
        }
    }

    @RequestMapping("/loginpage")
    public String loginPage(String reqUrl,Model model){
        model.addAttribute("url",reqUrl);
        return "/tpls/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(UserSession.SESSION_KEY);
        return "/tpls/login";
    }

}
