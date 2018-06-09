package com.plane.mapper.pbs;

import com.plane.entity.doors.Doors;
import com.plane.entity.pbs.PBS;
import com.plane.web.vo.pbs.PBSFileVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liubin on 2018/3/1.
 */
@Repository
public interface PBSMapper {

    List<PBS> queryPBSByParentId(PBSFileVO pbsFileVO);

    PBS queryPBSById(Long id);

    void insertPBS(PBS pbs);

    Long checkPBS(PBS pbs);

    void updatePBS(PBS pbs);

}
