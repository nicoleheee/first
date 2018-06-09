package com.plane.web.controller.pbs;

import com.plane.entity.pbs.PBS;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import com.plane.service.pbs.PBSService;
import com.plane.web.vo.JsonResult;
import com.plane.web.vo.UserSession;
import com.plane.web.vo.pbs.PBSFileVO;
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

@RequestMapping("/pbs")
@Controller
public class PBSListController {

    private Log log= LogFactory.getLog(PBSListController.class);

    @Autowired
    private PBSService pbsService;

    @RequestMapping("/list")
    public String list(Model model,PBSFileVO pbsFileVO){
        try {
            PBS pbs=pbsService.findPBSById(pbsFileVO.getParentId());
            List<PBS> pbsList=pbsService.findPBSByParentId(pbsFileVO);
            List<PBS> navigationPBS=pbsService.findPBSListById(pbsFileVO.getParentId());
            model.addAttribute("pbs",pbs);
            model.addAttribute("pbsList",pbsList);
            model.addAttribute("navigationPBS",navigationPBS);
            //不存在排序，设置默认排序
            if(StringUtils.isBlank(pbsFileVO.getSort())){
                pbsFileVO.setSort("create_time");
                pbsFileVO.setOrder("desc");
            }
            model.addAttribute("pbsFileVO",pbsFileVO);
        }catch (ServiceException e){
            log.debug("查询出错");
        }
        return "/tpls/pbs/list";
    }

    @RequestMapping("/add")
    @ResponseBody
    public JsonResult add(PBS pbs , UserSession userSession){
        JsonResult jsonResult=new JsonResult(false);
        pbs.setCreatorId(userSession.getUid());
        if(userSession.getUid()==null){
            pbs.setCreatorId(1L);
        }
        try {
            pbsService.addPBS(pbs);
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
    public JsonResult update(PBS pbs){
        JsonResult jsonResult=new JsonResult(false);
        try {
            pbsService.modifyPBS(pbs);
            jsonResult.setSuccess(true);
        }catch (ServiceException e){
            if(e.getErrorCode()==ServiceErrorCode.NameExists){
                jsonResult.setCode(-2);
            }
        }
        return jsonResult;
    }


}
