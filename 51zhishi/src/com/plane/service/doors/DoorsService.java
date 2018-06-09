package com.plane.service.doors;

import com.plane.entity.User;
import com.plane.entity.doors.Doors;
import com.plane.service.ServiceException;
import com.plane.web.vo.doors.DoorsFileVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liubin on 2018/3/1.
 */
public interface DoorsService {

    List<Doors> findDoorsByParentId(DoorsFileVO doorsFileVO)throws ServiceException;

    List<Doors> findDoorsListById(Long id)throws ServiceException;

    Doors findDoorsById(Long id)throws ServiceException;

    void addDoors(Doors doors)throws ServiceException;

    void modifyDoors(Doors doors)throws ServiceException;

    Long checkUser(User user)throws ServiceException ;

}
