package com.plane.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liubin on 2018/3/8.
 */
@Controller
public class IndexController {



    @RequestMapping("/")
    public String loginPage(){
        return "/tpls/index";
    }

}
