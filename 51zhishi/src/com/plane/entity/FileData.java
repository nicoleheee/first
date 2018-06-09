package com.plane.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件对象，存储于数据库
 * Created by 苏正坤 on 2016/9/3.
 */
public class FileData implements Serializable
{
    private long fileId;
    private String fileName;
    private String originalFileName;
    private long userId;
    private long fileLength;
    private boolean imageFile;
    private String mimeType;
    private int width;
    private int height;
    private Date uploadDate;
    private String fileUrl;

    private Long imgNative;

    public Long getImgNative() {
        return imgNative;
    }

    public void setImgNative(Long imgNative) {
        this.imgNative = imgNative;
    }

    public String getFileUrl()
    {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl)
    {
        this.fileUrl = fileUrl;
    }

    public long getFileId()
    {
        return fileId;
    }

    public void setFileId(long fileId)
    {
        this.fileId = fileId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getOriginalFileName()
    {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName)
    {
        this.originalFileName = originalFileName;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public long getFileLength()
    {
        return fileLength;
    }

    public void setFileLength(long fileLength)
    {
        this.fileLength = fileLength;
    }

    public boolean isImageFile()
    {
        return imageFile;
    }

    public void setImageFile(boolean imageFile)
    {
        this.imageFile = imageFile;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    public Date getUploadDate()
    {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate)
    {
        this.uploadDate = uploadDate;
    }
}
