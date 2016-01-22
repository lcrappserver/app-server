package org.lcr.server.dao;

import org.apache.ibatis.annotations.Param;
import org.lcr.server.vo.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {

    /**
     * 获取商品列表
     *
     * @param key 模糊查询条件
     * @return 商品列表
     */
    List<ProductEntity> getProduct(@Param(value = "key") String key);
}
