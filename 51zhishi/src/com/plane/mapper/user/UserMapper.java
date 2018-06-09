package com.plane.mapper.user;

import com.plane.entity.FileData;
import com.plane.entity.User;
import com.plane.web.vo.user.UserFileVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface UserMapper
{
     void insertUser(User user);

     List<User> queryAllUser(UserFileVO userFileVO);

     Long queryIdByUserName(User user);

     User queryUserById(Long id);

     Long queryIdByEmail(User user);

     void updateUser(User user);

     void updateStateUserById(Long id);

     void updatePassWordById(User user);

}
