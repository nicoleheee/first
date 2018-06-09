package com.plane.service.impl;

import com.plane.entity.FileData;
import com.plane.entity.User;
import com.plane.mapper.UploadFileDataMapper;
import com.plane.service.FileStorageService;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import com.plane.web.util.CommonTools;
import com.plane.web.vo.Rectangle;
import com.sun.xml.internal.ws.util.UtilException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by User on 2016/9/2.
 */
@Service
@Lazy(false)
@Transactional
public class FileStorageServiceImpl implements FileStorageService
{
    private Path rootPath = Paths.get("uploaddir");
    private Log log = LogFactory.getLog(FileStorageServiceImpl.class);

    @Value("${file.types}")
    private List<String> fileExts = new ArrayList<>();

    @Value("${file.maxlength}")
    private String maxFileLength = "2M";

    @Value("${file.uploadRoot}")
    private String configRoot = null;

    @Autowired
    private UploadFileDataMapper uploadFileDataMapper;


    private long maxFileBytesLen = 0;

    @PostConstruct
    public void initialize()
    {


        maxFileBytesLen = CommonTools.getFileLength(maxFileLength);
        if (maxFileBytesLen == -1)
        {
            log.error("文件上传设置maxFileLength格式错误，请以000M方式设置大小,系统将使用默认大小2M限制");
            maxFileBytesLen = 2*1024 * 1024L;
        }
        if (configRoot != null)
        {
            rootPath = Paths.get(configRoot);
        }

        log.debug("文件上传路径为:" + rootPath.toString());
        try
        {
            if (Files.notExists(rootPath))
            {
                Files.createDirectories(rootPath);
                log.info("成功创建文件上传路径：" + rootPath.toString());
            }
        } catch (IOException e)
        {
            log.error("创建文件上传路径出错！！", e);
        }

    }


    @Override
    @Transactional
    public FileData store(MultipartFile file, Long fileId, long userId, Integer isTemporary) throws ServiceException
    {

        if (file.isEmpty())
        {
            throw new ServiceException("上传文件失败，文件为空", ServiceErrorCode.UploadErrorFileEmpty);
        }

        if (file.getSize() > maxFileBytesLen)
        {
            throw new ServiceException("上传文件失败，文件大小超出限制:" + maxFileLength, ServiceErrorCode.UploadErrorFileMaxLength);
        }
        String fileName = getNewFileName(file.getOriginalFilename());
        FileData fileData = new FileData();
        try
        {
            //删除旧文件
            if(fileId!=null)
            {
                deleteFile(fileId);
            }

            Path originalFileName = Paths.get(file.getOriginalFilename()).getFileName();
            fileData.setFileName(fileName);
            fileData.setUserId(userId);
            fileData.setFileLength(file.getSize());
            fileData.setOriginalFileName(originalFileName.toString());
            fileData.setUploadDate(new Date());

            //TODO:未完成对文件格式的验证
            Path filePath = rootPath.resolve(fileName);
            if (Files.notExists(filePath.getParent()))
            {
                Files.createDirectories(filePath.getParent());
            }
            ImageType imageType = resolveImage(file);
            if (imageType != null)
            {
                fileData.setImageFile(true);
                fileData.setWidth(imageType.width);
                fileData.setHeight(imageType.height);
            }
            Files.copy(file.getInputStream(), filePath);
            uploadFileDataMapper.insertFileData(fileData);
            Long id=fileData.getFileId();
            return fileData;

        } catch (IOException e)
        {
            log.error("上传文件保存失败", e);
            throw new ServiceException("保存文件失败", ServiceErrorCode.UploadErrorFileIO);
        } catch (DataAccessException dae)
        {
            log.error("保存文件数据库操作失败", dae);
            throw new ServiceException("保存文件数据库操作失败", ServiceErrorCode.UploadErrorFileIO);
        } catch (UtilException e)
        {
            log.error("保存文件操作失败", e);
            throw new ServiceException("保存文件操作失败,缩放裁剪文件错误！", ServiceErrorCode.UploadErrorFileIO);
        }

    }

   @Override
    public FileData store_wx_easy(User user, String url, Integer imgSize)throws Exception{
        /*URL imgUrl=new URL(url);
        InputStream is = null;
        BufferedImage image = null;
        String imgName="userHead_wx"+user.getId()+".jpg";
        String fileName = getNewFileName(imgName);
        FileData fileData = new FileData();
        Path filePath = rootPath.resolve(fileName);
        if (Files.notExists(filePath.getParent()))
        {
            Files.createDirectories(filePath.getParent());
        }
        Thumbnails.Builder<BufferedImage> builder;
        try {
            is = imgUrl.openStream();
            image = ImageIO.read(is);
            builder = Thumbnails.of(image).size(imgSize, imgSize);
            builder.outputFormat("jpg").toFile(filePath.toAbsolutePath().toString());
        } catch (Exception e) {
            log.error("读取图片文件出错："+e.getMessage());
            return fileData;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                return fileData;
            }

        }
        try
        {
            File file=new File(filePath.toAbsolutePath().toString());
            fileData.setFileName(fileName);
            fileData.setUserId(user.getId());
            fileData.setFileLength(file.length());
            fileData.setOriginalFileName(imgName);
            fileData.setUploadDate(new Date());
            fileData.setHeight(imgSize);
            fileData.setWidth(imgSize);
            uploadFileDataMapper.insertFileData(fileData);
            Long id=fileData.getFileId();
        } catch (Exception e)
        {
            log.error("保存文件数据库操作失败", e);
        }*/
        return null;
    }

    @Override
    @Transactional
    public long downLoadImg(Long uid, String url) throws Exception {
        URL imgUrl=new URL(url);
        InputStream is = null;
        BufferedImage image = null;
        Calendar now = Calendar.getInstance();
        String imgName="url_down_load"+uid+now.get(Calendar.YEAR)+"_"+(now.get(Calendar.MONTH) + 1)+"_"+now.get(Calendar.DAY_OF_MONTH)+".jpg";
        String fileName = getNewFileName(imgName);
        FileData fileData = new FileData();
        Path filePath = rootPath.resolve(fileName);
        if (Files.notExists(filePath.getParent()))
        {
            Files.createDirectories(filePath.getParent());
        }
        Thumbnails.Builder<BufferedImage> builder;
        try {
            is = imgUrl.openStream();
            image = ImageIO.read(is);
            System.out.println(image.getWidth());
            builder = Thumbnails.of(image).size(image.getWidth(),image.getHeight());
            builder.outputFormat("jpg").toFile(filePath.toAbsolutePath().toString());
        } catch (Exception e) {
            log.error("读取图片文件出错："+e.getMessage());
            return fileData.getFileId();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                return fileData.getFileId();
            }

        }
        try
        {
            File file=new File(filePath.toAbsolutePath().toString());
            fileData.setFileName(fileName);
            fileData.setUserId(uid);
            fileData.setFileLength(file.length());
            fileData.setOriginalFileName(imgName);
            fileData.setUploadDate(new Date());
            fileData.setHeight(image.getHeight());
            fileData.setWidth(image.getWidth());
            uploadFileDataMapper.insertFileData(fileData);
            Long id=fileData.getFileId();
        } catch (Exception e)
        {
            log.error("保存文件数据库操作失败", e);
        }
        return fileData.getFileId();
    }


    private void deleteFile(long fileId)throws ServiceException
    {

        FileData data = uploadFileDataMapper.loadFileByID(fileId);
        if (data == null)
        {
            return ;
        }
        Path filePath =rootPath.resolve(data.getFileName());
        try
        {
            Files.deleteIfExists(filePath);
            uploadFileDataMapper.deleteFile(fileId);

        } catch (IOException e)
        {
           throw new ServiceException("删除文件操作失败",ServiceErrorCode.UploadErrorFileIO);
        }

    }





    @Override
    public FileData loadFileData(FileData fileData) throws ServiceException
    {
        try
        {
            FileData data =null;
            Rectangle rect=new Rectangle();
            if(fileData.getWidth()==0&&fileData.getHeight()==0){
                data = uploadFileDataMapper.loadFileByID(fileData.getFileId());
            }else
            {
                //只传宽度(默认高宽相同)
                if(fileData.getHeight()==0&&fileData.getWidth()!=0){
                    rect.setWidth(fileData.getWidth());
                    rect.setHeight(fileData.getWidth());
                }
                //只传高度(默认高宽相同)
                else if(fileData.getWidth()==0&&fileData.getHeight()!=0){
                    rect.setWidth(fileData.getHeight());
                    rect.setHeight(fileData.getHeight());
                } //传高度和宽度
                else{
                    rect.setHeight(fileData.getHeight());
                    rect.setWidth(fileData.getWidth());
                }
                Rectangle rectangle=FileStorageServiceImpl.imgSizeNear(rect,new ArrayList(),new ArrayList());
                fileData.setWidth(rectangle.getWidth());
                fileData.setHeight(rectangle.getHeight());
                fileData.setImgNative(fileData.getFileId());
                data = uploadFileDataMapper.queryFileByWidthAndHeightAndNative(fileData);
                //之前没有上传多个尺寸的图片取原图
                if (data == null)
                {
                    data=uploadFileDataMapper.loadFileByID(fileData.getFileId());
                }
            }

            if (data == null)
            {
                return null;
            }
            data.setOriginalFileName(rootPath.resolve(data.getFileName()).toAbsolutePath().toString());
            return data;
        } catch (DataAccessException dae)
        {
            log.error("读取数据库发生错误", dae);
            throw new ServiceException("读取数据库发生错误", ServiceErrorCode.DataBaseError);
        }

    }
    @Override
    @Transactional
    public void delFileImg(long fileId,Long uid) throws ServiceException {
        try {
            FileData fileData=uploadFileDataMapper.loadFileByID(fileId);
            if(fileData==null){
                throw new ServiceException(ServiceErrorCode.NullError);
            }
            if(fileData.getUserId()!=uid){
                throw new ServiceException(ServiceErrorCode.UploadErrorNonSelf);
            }
            deleteFile(fileId);
        }catch (DataAccessException e){
            log.error("删除图片失败", e);
            throw new ServiceException("删除图片失败", ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public void modifyFileDataTemporary(Long fileId) throws ServiceException {
        try {

        }catch (DataAccessException e){
            log.error("修改图片为临时状体出错", e);
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public void modifyFileDataUse(Long fileId) throws ServiceException {
        try {
        }catch (DataAccessException e){
            log.error("修改图片为使用出错", e);
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public void delTemporaryImg() throws ServiceException {
        try {
        }catch (DataAccessException e){
            log.error("删除临时图片出错", e);
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    /**
     * 获取新文件名
     */
    private synchronized String getNewFileName(String fileName)
    {
        String fileExt = fileName.substring(fileName.lastIndexOf('.'));

        DateFormat formatter = new SimpleDateFormat("yyMMdd");

        return String.format("%s/%s" + fileExt, formatter.format(new Date()), CommonTools.keyByTimes());

    }


    /**
     * 侦测图片大小
     */
    private ImageType resolveImage(MultipartFile file)
    {
        if (!file.getContentType().startsWith("image"))
        {
            log.warn("不是图片格式" + file.getContentType());
            return null;
        }
        ImageReader reader = null;
        try
        {
            int suffixIndex = file.getOriginalFilename().lastIndexOf('.');
            String suffix = "jpg";
            if (suffixIndex != -1)
            {
                suffix = file.getOriginalFilename().substring(suffixIndex + 1);
            }

            Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(suffix);
            if (!readers.hasNext())
            {
                log.warn("未能找到对应的图片reader,图片格式不正确");
                return null;
            }
            reader = readers.next();
            reader.setInput(new MemoryCacheImageInputStream(file.getInputStream()));
            ImageType imageType = new ImageType();
            imageType.height = reader.getWidth(0);
            imageType.width = reader.getHeight(0);

            return imageType;
        } catch (IOException e)
        {

            log.warn("上传的文件不是图片");

        } finally
        {
            if (reader != null)
            {
                reader.dispose();
            }

        }

        return null;
    }



    protected static class ImageType
    {

        protected int width;
        protected int height;
        protected int type;

    }


    protected static Rectangle imgSizeNear(Rectangle rectangle,List width,List height){
        List list=new ArrayList();
        Integer index=0;
        for(int i=0;i<width.size();i++){
            list.add(Math.abs(rectangle.getWidth()-Integer.parseInt((String) width.get(i)))+
                    Math.abs(rectangle.getHeight()-Integer.parseInt((String)  height.get(i))));
        }
        Integer w=(Integer) Collections.min(list);
        for(int j=0;j<list.size();j++){
            if(list.get(j)==w){
                index=j;
                break;
            }
        }
        rectangle.setHeight(Integer.parseInt((String)height.get(index)));
        rectangle.setWidth(Integer.parseInt((String)width.get(index)));
        return rectangle;
    }


}
