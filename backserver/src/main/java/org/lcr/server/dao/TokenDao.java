package org.lcr.server.dao;

import org.lcr.server.entity.BackTokenEntity;
import org.lcr.server.entity.BackUserInfoEntity;

/**
 * Created by Administrator on 2016/1/13.
 */
public interface TokenDao {

    public BackTokenEntity getToken(BackTokenEntity vo);

    public BackUserInfoEntity getUserInfo(BackTokenEntity vo);

}
