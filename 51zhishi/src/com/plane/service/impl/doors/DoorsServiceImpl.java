package com.plane.service.impl.doors;

import com.plane.entity.User;
import com.plane.entity.doors.Doors;
import com.plane.mapper.doors.DoorsMapper;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import com.plane.service.doors.DoorsService;
import com.plane.web.util.MD5;
import com.plane.web.vo.doors.DoorsFileVO;
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
public class DoorsServiceImpl implements DoorsService {

    private Log log= LogFactory.getLog(DoorsServiceImpl.class);

    @Autowired
    private DoorsMapper doorsMapper;

    @Override
    public List<Doors> findDoorsByParentId(DoorsFileVO doorsFileVO) throws ServiceException {
        try {
            if(doorsFileVO.getParentId()==null){
                doorsFileVO.setParentId(0L);
            }
            return doorsMapper.queryDoorsByParentId(doorsFileVO);
        }catch (DataAccessException e){
            log.error("查询子数据出错"+e.getMessage());
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public List<Doors> findDoorsListById(Long id) throws ServiceException {
        try {
            List<Doors> list=new ArrayList<>();
            if(id==null){
                id=0L;
            }
            Long parentId=id;
            while(parentId!=0){
                Doors doors=doorsMapper.queryDoorsById(parentId);
                if(doors==null){
                    break;
                }
                parentId=doors.getParentId();
                list.add(doors);
            }
            Collections.reverse(list);
            return list;
        }catch (DataAccessException e){
            log.error("查询子数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public Doors findDoorsById(Long id) throws ServiceException {
        try {
            return doorsMapper.queryDoorsById(id);
        }catch (DataAccessException e){
            log.error("查询数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public void addDoors(Doors doors) throws ServiceException {
        try {
            Long id=doorsMapper.checkDoors(doors);
            if(id==null){
                doorsMapper.insertDoors(doors);
                return;
            }
            throw new ServiceException(ServiceErrorCode.NameExists);
        }catch (DataAccessException e){
            log.error("添加数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public void modifyDoors(Doors doors) throws ServiceException {
        try {
            Long id=doorsMapper.checkDoors(doors);
            if(id==null){
                doorsMapper.updateDoors(doors);
                return;
            }
            throw new ServiceException(ServiceErrorCode.NameExists);
        }catch (DataAccessException e){
            log.error("修改数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public Long checkUser(User user)throws ServiceException  {
        try {
            user.setPassWord(MD5.getMD5Password(user.getPassWord()));
            return doorsMapper.checkUser(user);
        }catch (DataAccessException e){
            log.error("修改数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }
}
