package com.plane.mapper;

import com.plane.entity.FileData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by User on 2016/9/3.
 */
@Repository
public interface UploadFileDataMapper
{

    /**
     * 插入文件数据
     * @param fileData
     */
    void insertFileData(FileData fileData);

    FileData loadFileByID(@Param("fileId") long fileId);

    void  deleteFile(@Param("fileId") long fileId);

    FileData queryFileByWidthAndHeightAndNative(FileData fileData);

}
