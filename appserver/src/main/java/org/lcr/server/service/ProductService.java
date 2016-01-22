package org.lcr.server.service;

import org.lcr.server.vo.ResponseVo;
import org.lcr.server.vo.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    /**
     * 获取商品列表
     *
     * @param key 模糊查询条件
     * @return ResponseVo
     */
    public ResponseVo getProduct(String key);

}
