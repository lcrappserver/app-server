package org.lcr.server.dao;


import org.lcr.server.entity.AccessTokenEntity;
import org.lcr.server.vo.TokenRequestVo;

/**
 * Created by Administrator on 2016/1/13.
 */
public interface TokenDao {

    public boolean checkUserExits(String loginName, String password);

}
