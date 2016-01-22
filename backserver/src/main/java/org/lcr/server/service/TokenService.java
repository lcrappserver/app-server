package org.lcr.server.service;

import org.lcr.server.vo.ResponseVo;
import org.lcr.server.vo.TokenRequestVo;

/**
 * 用户验证Service接口
 */
public interface TokenService {

  ResponseVo getToken(TokenRequestVo requestVo);

}
