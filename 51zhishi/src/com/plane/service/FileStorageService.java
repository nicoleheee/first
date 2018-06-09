package com.plane.service;


import com.plane.entity.FileData;
import com.plane.entity.User;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件存储服务
 * Created by 苏正坤 on 2016/9/2.
 */
public interface FileStorageService
{

    /**
     *
     * @param file spring文件对象
     * @param userId 上传文件的用户id
     * @return
     * @throws ServiceException
     */
    FileData store(MultipartFile file,Long fileId,long userId,Integer isTemporary) throws ServiceException;

    FileData store_wx_easy(User user, String url,Integer imgSize)throws Exception;

    FileData loadFileData(FileData fileData) throws ServiceException;

    void delFileImg(long fileId,Long uid) throws ServiceException;

    long downLoadImg(Long uid,String url)throws Exception;

    /**
     * 修改为临时图片
     * @param fileId
     */
    void modifyFileDataTemporary(Long fileId)throws ServiceException;

    /**
     * 修改为使用的图片
     * @param fileId
     */
    void modifyFileDataUse(Long fileId)throws ServiceException;

    /**
     * 查询带删除的图片（临时图片）
     * @throws ServiceException
     */
    void delTemporaryImg()throws ServiceException;



}
