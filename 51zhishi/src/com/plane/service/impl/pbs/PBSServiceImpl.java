package com.plane.service.impl.pbs;

import com.plane.entity.pbs.PBS;
import com.plane.mapper.pbs.PBSMapper;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import com.plane.service.pbs.PBSService;
import com.plane.web.vo.pbs.PBSFileVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by liubin on 2018/3/1.
 */
@Service
@Transactional
public class PBSServiceImpl implements PBSService {

    private Log log= LogFactory.getLog(PBSServiceImpl.class);

    @Autowired
    private PBSMapper pbsMapper;

    @Override
    public List<PBS> findPBSByParentId(PBSFileVO pbsFileVO) throws ServiceException {
        try {
            if(pbsFileVO.getParentId()==null){
                pbsFileVO.setParentId(0L);
            }
            return pbsMapper.queryPBSByParentId(pbsFileVO);
        }catch (DataAccessException e){
            log.error("查询子数据出错"+e.getMessage());
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public List<PBS> findPBSListById(Long id) throws ServiceException {
        try {
            List<PBS> list=new ArrayList<>();
            if(id==null){
                id=0L;
            }
            Long parentId=id;
            while(parentId!=0){
                PBS pbs=pbsMapper.queryPBSById(parentId);
                if(pbs==null){
                    break;
                }
                parentId=pbs.getParentId();
                list.add(pbs);
            }
            Collections.reverse(list);
            return list;
        }catch (DataAccessException e){
            log.error("查询子数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public PBS findPBSById(Long id) throws ServiceException {
        try {
            return pbsMapper.queryPBSById(id);
        }catch (DataAccessException e){
            log.error("查询数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public void addPBS(PBS pbs) throws ServiceException {
        try {
            Long id=pbsMapper.checkPBS(pbs);
            if(id==null){
                pbsMapper.insertPBS(pbs);
                return;
            }
            throw new ServiceException(ServiceErrorCode.NameExists);
        }catch (DataAccessException e){
            log.error("添加数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public void modifyPBS(PBS pbs) throws ServiceException {
        try {
            Long id=pbsMapper.checkPBS(pbs);
            if(id==null){
                pbsMapper.updatePBS(pbs);
                return;
            }
            throw new ServiceException(ServiceErrorCode.NameExists);
        }catch (DataAccessException e){
            log.error("修改数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

}
