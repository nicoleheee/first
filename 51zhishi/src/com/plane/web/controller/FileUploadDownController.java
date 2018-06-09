package com.plane.web.controller;

import com.plane.entity.FileData;
import com.plane.service.BasicConstant;
import com.plane.service.FileStorageService;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import com.plane.web.vo.JsonResult;
import com.plane.web.vo.UserSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;


/**
 * 文件上传下载控制器 Created by 苏正坤 on 2016/9/2.
 */
@Controller
@RequestMapping("/file")
public class FileUploadDownController implements ServletContextAware
{
    private Log log = LogFactory.getLog(FileUploadDownController.class);

    @Autowired
    private FileStorageService fileStorageService;

    private ServletContext context;



    @RequestMapping("/page")
    public String demoPage()
    {
        return "/tpls/uploadDemo";
    }


    /**
     * 文件上传，支持缩放和裁剪功能 如果上传的参数中给定了w,h 未给定x ,y 则采取等比例缩放的方式，缩放到制定的w,h 宽高 如果给定的x,y,w,h 则采取裁剪指定位置和宽高的图片，暂未实现缩放+裁剪
     *
     * @param fid 如果客户端给出了fid，那么将执行图片替换
     */
    @RequestMapping("/upload")
    @ResponseBody
    public JsonResult upload(MultipartFile file, Integer w, Integer h, Integer x, Integer y, Long fid,
                             UserSession user, HttpSession session, Integer isTemporary)
    {

        JsonResult result = new JsonResult(false);
        if (file.isEmpty())
        {
            result.setErrorMsg("文件内容为空！！");
            return result;
        }

        try
        {
           /* Rectangle rect = new Rectangle();
            if (w != null)
            {
                rect = new Rectangle(x, y, w, h);
            }*/

            FileData fileData = fileStorageService.store(file,fid, user.getUid(),isTemporary);
            result.setSuccess(true);
            result.setExtendData(fileData.getFileId());
            //更新session
           /* UserSession us = new UserSession(user.getUid(), user.getLoginName());
            //us.setUserLogoId(fileData.getFileId());
            session.setAttribute(UserSession.SESSION_KEY, us);*/
            return result;
        } catch (ServiceException e)
        {
            result.setErrorMsg(e.getMessage());
            return result;
        }

    }


    @RequestMapping(value = "/img/{fileId:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getImage(@PathVariable Integer fileId,Integer w,Integer h, WebRequest request)
    {
        FileData fileData=new FileData();
        if (fileId == null)
        {
            log.warn("获取图片失败，未能获得fileId");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        try
        {
            FileData data = null;
            Path path = null;
            if (fileId <= 0)
            {
                path = Paths.get(WebUtils.getRealPath(context,"/image/null-logo.jpg"));
            }
            else
            {
                fileData.setFileId(fileId);
                if(w==null){
                    fileData.setWidth(0);
                }else {
                    fileData.setWidth(w);
                }
                if(h==null){
                    fileData.setHeight(0);
                }else {
                    fileData.setHeight(h);
                }
                data = fileStorageService.loadFileData(fileData);
                if (data != null)
                {
                    path = Paths.get(data.getOriginalFileName());
                }
            }

            if (path==null||Files.notExists(path))
            {
               path = Paths.get(WebUtils.getRealPath(context,"/image/nullpic.png"));
            }
            FileTime fileTime = Files.getLastModifiedTime(path);
            if (request.checkNotModified(fileTime.toMillis()))
            {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/webp"));
            headers.setContentLength(Files.size(path));
            headers.setLastModified(fileTime.toMillis());
            headers.setCacheControl("max-age=604800, public");
            FileSystemResource imageResource = new FileSystemResource(path.toFile());
            return new ResponseEntity<Resource>(imageResource, headers, HttpStatus.OK);

        } catch (IOException e)
        {
            log.error("传输图像时获取文件大小出错");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceException e)
        {
            log.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delimg/{fileId:\\d+}",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delImg(@PathVariable Integer fileId,UserSession userSession){
        JsonResult jsonResult=new JsonResult(false);
        try {
            fileStorageService.delFileImg(fileId,userSession.getUid());
            jsonResult.setSuccess(true);
            return jsonResult;
        }catch (ServiceException e){
            log.error("删除图片出错");
            switch (e.getErrorCode()){
                case ServiceErrorCode.DataBaseError:
                    jsonResult.setCode(BasicConstant.ResultState.ERROR_STATE);
                    break;
                case ServiceErrorCode.UploadErrorNonSelf:
                    jsonResult.setCode(BasicConstant.ResultState.NON_SELF);
                    break;
                case ServiceErrorCode.NullError:
                    jsonResult.setCode(BasicConstant.ResultState.NULL_STATE);
                    break;
            }
            return jsonResult;
        }
    }
    @Override
    public void setServletContext(ServletContext servletContext)
    {
            this.context = servletContext;
    }
}
