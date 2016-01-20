package org.lcr.server.service;

import org.lcr.server.vo.ResponseVo;
import org.lcr.server.vo.TokenRequestVo;

/**
 * Created by Administrator on 2016/1/18.
 */
public interface AccessTokenService {

    public ResponseVo token(TokenRequestVo vo);
}
