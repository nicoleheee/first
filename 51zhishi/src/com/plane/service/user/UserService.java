package com.plane.service.user;

import com.plane.entity.User;
import com.plane.service.ServiceException;
import com.plane.web.vo.user.UserFileVO;

import java.util.List;


public interface UserService {

    void addUser(User user) throws ServiceException;

    List<User> findAllUser(UserFileVO userFileVO) throws ServiceException;

    User findUserById(Long id) throws ServiceException;

    void modifyUser(User user) throws ServiceException;

    void deleteUserById(Long id) throws ServiceException;

    void resetPassWordById(Long id) throws ServiceException;

}
