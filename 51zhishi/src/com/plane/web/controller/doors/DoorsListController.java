package com.plane.web.controller.doors;

import com.plane.entity.doors.Doors;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import com.plane.service.doors.DoorsService;
import com.plane.web.vo.JsonResult;
import com.plane.web.vo.UserSession;
import com.plane.web.vo.doors.DoorsFileVO;
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

@RequestMapping("/doors")
@Controller
public class DoorsListController {

    private Log log= LogFactory.getLog(DoorsListController.class);

    @Autowired
    private DoorsService doorsService;

    @RequestMapping("/list")
    public String list(Model model,DoorsFileVO doorsFileVO){
        try {
            Doors doors=doorsService.findDoorsById(doorsFileVO.getParentId());
            List<Doors> doorsList=doorsService.findDoorsByParentId(doorsFileVO);
            List<Doors> navigationDoors=doorsService.findDoorsListById(doorsFileVO.getParentId());
            model.addAttribute("doors",doors);
            model.addAttribute("doorsList",doorsList);
            model.addAttribute("navigationDoors",navigationDoors);
            //不存在排序，设置默认排序
            if(StringUtils.isBlank(doorsFileVO.getSort())){
                doorsFileVO.setSort("create_time");
                doorsFileVO.setOrder("desc");
            }
            model.addAttribute("doorsFileVO",doorsFileVO);
        }catch (ServiceException e){
            log.debug("查询出错");
        }
        return "/tpls/doors/list";
    }

    @RequestMapping("/add")
    @ResponseBody
    public JsonResult add(Doors doors, UserSession userSession){
        JsonResult jsonResult=new JsonResult(false);
        doors.setCreatorId(userSession.getUid());
        if(userSession.getUid()==null){
            doors.setCreatorId(1L);
        }
        try {
            doorsService.addDoors(doors);
            jsonResult.setSuccess(true);
        }catch (ServiceException e){
            if(e.getErrorCode()==ServiceErrorCode.NameExists){
                jsonResult.setCode(-2);
            }
        }
        return jsonResult;
    }

    @RequestMapping("/update")
    @ResponseBody
    public JsonResult update(Doors doors){
        JsonResult jsonResult=new JsonResult(false);
        try {
            doorsService.modifyDoors(doors);
            jsonResult.setSuccess(true);
        }catch (ServiceException e){
            if(e.getErrorCode()==ServiceErrorCode.NameExists){
                jsonResult.setCode(-2);
            }
        }
        return jsonResult;
    }


}
