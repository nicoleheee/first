package com.plane.mapper.doors;

import com.plane.entity.User;
import com.plane.entity.doors.Doors;
import com.plane.web.vo.doors.DoorsFileVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liubin on 2018/3/1.
 */
@Repository
public interface DoorsMapper {

    List<Doors> queryDoorsByParentId(DoorsFileVO doorsFileVO);

    Doors queryDoorsById(Long id);

    void insertDoors(Doors doors);

    Long checkDoors(Doors doors);

    void updateDoors(Doors doors);

    Long checkUser(User user);

}
