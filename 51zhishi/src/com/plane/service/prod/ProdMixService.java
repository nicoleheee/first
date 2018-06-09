package com.plane.service.prod;

import com.plane.entity.prod.ProdMix;
import com.plane.service.ServiceException;
import com.plane.web.vo.prod.ProdMixFileVO;

import java.util.List;

/**
 * Created by liubin on 2018/3/1.
 */
public interface ProdMixService {

    List<ProdMix> findProdMixByParentId(ProdMixFileVO prodMixFileVO)throws ServiceException;

    List<ProdMix> findProdMixListById(Long id)throws ServiceException;

    ProdMix findProdMixById(Long id)throws ServiceException;

    void addProdMix(ProdMix prodMix)throws ServiceException;

    void modifyProdMix(ProdMix prodMix)throws ServiceException;

}
