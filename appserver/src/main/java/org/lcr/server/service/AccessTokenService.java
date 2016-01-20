package org.lcr.server.service;

import org.lcr.server.vo.ResponseVo;
import org.lcr.server.vo.TokenRequestVo;

public interface AccessTokenService {

  ResponseVo token(TokenRequestVo vo);
}
