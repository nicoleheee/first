package com.plane.web.controller.prod;

import com.plane.entity.doors.Doors;
import com.plane.entity.prod.ProdMix;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import com.plane.service.doors.DoorsService;
import com.plane.service.prod.ProdMixService;
import com.plane.web.vo.JsonResult;
import com.plane.web.vo.UserSession;
import com.plane.web.vo.doors.DoorsFileVO;
import com.plane.web.vo.prod.ProdMixFileVO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@RequestMapping("/prod")
@Controller
public class ProdMixListController {

    private Log log= LogFactory.getLog(ProdMixListController.class);

    @Autowired
    private ProdMixService prodMixService;

    @RequestMapping("/list")
    public String list(Model model,ProdMixFileVO prodMixFileVO){
        try {
            ProdMix prodMix=prodMixService.findProdMixById(prodMixFileVO.getParentId());
            List<ProdMix> prodMixList=prodMixService.findProdMixByParentId(prodMixFileVO);
            List<ProdMix> navigationProdMix=prodMixService.findProdMixListById(prodMixFileVO.getParentId());
            model.addAttribute("prodMix",prodMix);
            model.addAttribute("prodMixList",prodMixList);
            model.addAttribute("navigationProdMix",navigationProdMix);
            //不存在排序，设置默认排序
            if(StringUtils.isBlank(prodMixFileVO.getSort())){
                prodMixFileVO.setSort("create_time");
                prodMixFileVO.setOrder("desc");
            }
            model.addAttribute("prodMixFileVO",prodMixFileVO);
        }catch (ServiceException e){
            log.debug("查询出错");
        }
        return "/tpls/prod/list";
    }

    @RequestMapping("/add")
    @ResponseBody
    public JsonResult add(ProdMix prodMix, UserSession userSession){
        JsonResult jsonResult=new JsonResult(false);
        prodMix.setCreatorId(userSession.getUid());
        if(userSession.getUid()==null){
            prodMix.setCreatorId(1L);
        }
        try {
            prodMixService.addProdMix(prodMix);
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
    public JsonResult update(ProdMix prodMix){
        JsonResult jsonResult=new JsonResult(false);
        try {
            prodMixService.modifyProdMix(prodMix);
            jsonResult.setSuccess(true);
        }catch (ServiceException e){
            if(e.getErrorCode()==ServiceErrorCode.NameExists){
                jsonResult.setCode(-2);
            }
        }
        return jsonResult;
    }


}
