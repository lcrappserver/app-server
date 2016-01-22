package org.lcr.server.service;

import org.lcr.server.common.Message;
import org.lcr.server.dao.ProductDao;
import org.lcr.server.tools.ArrayListUtils;
import org.lcr.server.vo.ResponseVo;
import org.lcr.server.vo.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    public ResponseVo getProduct(String key) {

        ResponseVo result = new ResponseVo();

        List<ProductEntity> productList = productDao.getProduct(key);

        if (ArrayListUtils.isNotEmpty(productList)) {
            result.setCode(Message.SUCCESS_CODE);
            result.setMessage(Message.SUCCESS_MSG);
            result.setEntity(productList);
        } else {
            result.setCode(Message.NO_DATA_CODE);
            result.setMessage(Message.NO_DATA_MSG);
        }

        return result;
    }
}
