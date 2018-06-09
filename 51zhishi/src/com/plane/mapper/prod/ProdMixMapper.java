package com.plane.mapper.prod;

import com.plane.entity.prod.ProdMix;
import com.plane.web.vo.prod.ProdMixFileVO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProdMixMapper {

    List<ProdMix> queryProdMixByParentId(ProdMixFileVO prodMixFileVO);

    ProdMix queryProdMixById(Long id);

    void insertProdMix(ProdMix prodMix);

    Long checkProdMix(ProdMix prodMix);

    void updateProdMix(ProdMix prodMix);

}
