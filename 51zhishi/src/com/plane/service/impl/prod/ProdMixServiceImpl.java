package com.plane.service.impl.prod;


import com.plane.entity.prod.ProdMix;
import com.plane.mapper.prod.ProdMixMapper;
import com.plane.service.ServiceErrorCode;
import com.plane.service.ServiceException;
import com.plane.service.prod.ProdMixService;
import com.plane.web.vo.prod.ProdMixFileVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ProdMixServiceImpl implements ProdMixService {

    private Log log= LogFactory.getLog(ProdMixServiceImpl.class);

    @Autowired
    private ProdMixMapper prodMixMapper;

    @Override
    public List<ProdMix> findProdMixByParentId(ProdMixFileVO prodMixFileVO) throws ServiceException {
        try {
            if(prodMixFileVO.getParentId()==null){
                prodMixFileVO.setParentId(0L);
            }
            return prodMixMapper.queryProdMixByParentId(prodMixFileVO);
        }catch (DataAccessException e){
            log.error("查询子数据出错"+e.getMessage());
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public List<ProdMix> findProdMixListById(Long id) throws ServiceException {
        try {
            List<ProdMix> list=new ArrayList<>();
            if(id==null){
                id=0L;
            }
            Long parentId=id;
            while(parentId!=0){
                ProdMix prodMix=prodMixMapper.queryProdMixById(parentId);
                if(prodMix==null){
                    break;
                }
                parentId=prodMix.getParentId();
                list.add(prodMix);
            }
            Collections.reverse(list);
            return list;
        }catch (DataAccessException e){
            log.error("查询子数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public ProdMix findProdMixById(Long id) throws ServiceException {
        try {
            return prodMixMapper.queryProdMixById(id);
        }catch (DataAccessException e){
            log.error("查询数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public void addProdMix(ProdMix prodMix) throws ServiceException {
        try {
            Long id=prodMixMapper.checkProdMix(prodMix);
            if(id==null){
                prodMixMapper.insertProdMix(prodMix);
                return;
            }
            throw new ServiceException(ServiceErrorCode.NameExists);
        }catch (DataAccessException e){
            log.error("添加数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }

    @Override
    public void modifyProdMix(ProdMix prodMix) throws ServiceException {
        try {
            Long id=prodMixMapper.checkProdMix(prodMix);
            if(id==null){
                prodMixMapper.updateProdMix(prodMix);
                return;
            }
            throw new ServiceException(ServiceErrorCode.NameExists);
        }catch (DataAccessException e){
            log.error("修改数据出错");
            throw new ServiceException(ServiceErrorCode.DataBaseError);
        }
    }
}
