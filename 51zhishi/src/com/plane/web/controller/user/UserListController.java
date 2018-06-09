package com.plane.web.controller.user;

import com.plane.entity.User;
import com.plane.service.BasicConstant;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import com.plane.service.user.UserService;
import com.plane.web.vo.JsonResult;
import com.plane.web.vo.UserSession;
import com.plane.web.vo.user.UserFileVO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liubin on 2018/3/1.
 */

@RequestMapping("/user")
@Controller
public class UserListController {

    private Log log = LogFactory.getLog(UserListController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public String list(Model model, UserFileVO userFileVO){
        try {
            List<User> userList=userService.findAllUser(userFileVO);
            model.addAttribute("userList",userList);
            if(StringUtils.isBlank(userFileVO.getSort())){
                userFileVO.setSort("create_time");
                userFileVO.setOrder("desc");
            }
            model.addAttribute("userFileVO",userFileVO);

        }catch (ServiceException e){
            log.debug("查询出错");
        }
        return "/tpls/user/list";
    }

    @RequestMapping("/add")
    @ResponseBody
    public JsonResult add(User user, UserSession userSession){
        JsonResult jsonResult=new JsonResult(false);
        user.setId(userSession.getUid());
        try {
            userService.addUser(user);
            jsonResult.setSuccess(true);
        }catch (ServiceException e){
            if(e.getErrorCode()==ServiceErrorCode.NameExists){
                jsonResult.setCode(-2);
            }
            if(e.getErrorCode()== ServiceErrorCode.EmailExists){
                jsonResult.setCode(-3);
            }
        }
        return jsonResult;
    }


    @RequestMapping("/update")
    @ResponseBody
    public JsonResult update(User user){
        JsonResult jsonResult=new JsonResult(false);
        try {
            userService.modifyUser(user);
            jsonResult.setSuccess(true);
        }catch (ServiceException e){
            if(e.getErrorCode()==ServiceErrorCode.NameExists){
                jsonResult.setCode(-2);
            }
            if(e.getErrorCode()==ServiceErrorCode.EmailExists){
                jsonResult.setCode(-3);
            }
        }
        return jsonResult;
    }


    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(Long id){
        JsonResult jsonResult=new JsonResult(false);

        try {
            userService.deleteUserById(id);
            jsonResult.setSuccess(true);

        } catch (ServiceException e) {
            if (e.getErrorCode() == ServiceErrorCode.DataBaseError) {
                jsonResult.setCode(-1);
            }
        }
        return jsonResult;

    }

    @RequestMapping("/reset")
    @ResponseBody
    public JsonResult reset(Long id){
        JsonResult jsonResult=new JsonResult(false);
        try{
            userService.resetPassWordById(id);
            jsonResult.setSuccess(true);

        }catch (ServiceException e) {
            if (e.getErrorCode() == ServiceErrorCode.DataBaseError) {
                jsonResult.setCode(-1);
            }
        }
        return jsonResult;

    }


}
