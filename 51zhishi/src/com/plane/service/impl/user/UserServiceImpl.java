package com.plane.service.impl.user;

import com.plane.entity.User;
import com.plane.mapper.user.UserMapper;
import com.plane.service.BasicConstant;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import com.plane.service.user.UserService;
import com.plane.web.util.MD5;
import com.plane.web.vo.user.UserFileVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private Log log= LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(User user)throws ServiceException {
        try {
            Long id = userMapper.queryIdByUserName(user);
            if (id==null) {

                if(id==userMapper.queryIdByEmail(user)){

                    user.setPassWord(MD5.getMD5Password("123456"));
                    userMapper.insertUser(user);
                    return;
                }
                throw new ServiceException(ServiceErrorCode.EmailExists);

            }

            throw new ServiceException(ServiceErrorCode.NameExists);
        } catch (DataAccessException e) {
            log.error("添加用户失败", e);
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUser(UserFileVO userFileVO) throws ServiceException {
        try{
            return userMapper.queryAllUser(userFileVO);
        }catch(DataAccessException e){
            log.error("查询所有用户出错",e);
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }


    @Override
    public User findUserById(Long id) throws ServiceException{
        try {
            return userMapper.queryUserById(id);
        } catch (DataAccessException e){
            log.error("根据id查询用户数据出错",e);
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }

    }

    @Override
    public void modifyUser(User user) throws ServiceException {
        try {
            Long id=userMapper.queryIdByUserName(user);
            if(id==null){
                if(id==userMapper.queryIdByEmail(user)){
                    userMapper.updateUser(user);
                    return;
                }
               throw new ServiceException(ServiceErrorCode.EmailExists);
            }
            throw new ServiceException(ServiceErrorCode.NameExists);
        }catch (DataAccessException e){
            log.error("修改数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public void  deleteUserById(Long id) throws ServiceException{
        try{
            userMapper.updateStateUserById(id);

        }catch(DataAccessException e){
            log.error("删除数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }

    }

    @Override
    public void resetPassWordById(Long id) throws ServiceException {
        try {

            User user=userMapper.queryUserById(id);
            user.setPassWord(MD5.getMD5Password("123456"));
            userMapper.updatePassWordById(user);
            return;

        }catch (DataAccessException e){
            log.error("重置密码出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }



}
