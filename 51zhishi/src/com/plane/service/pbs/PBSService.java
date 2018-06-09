package com.plane.service.pbs;


import com.plane.entity.pbs.PBS;
import com.plane.service.ServiceException;
import com.plane.web.vo.pbs.PBSFileVO;

import java.util.List;

/**
 * Created by liubin on 2018/3/1.
 */
public interface PBSService {

    List<PBS> findPBSByParentId(PBSFileVO pbsFileVO)throws ServiceException;

    List<PBS> findPBSListById(Long id)throws ServiceException;

    PBS findPBSById(Long id)throws ServiceException;

    void addPBS(PBS pbs)throws ServiceException;

    void modifyPBS(PBS pbs)throws ServiceException;

}
