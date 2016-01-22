package org.lcr.server.service;

import org.lcr.server.common.Message;
import org.lcr.server.dao.TokenDao;
import org.lcr.server.vo.entity.BackTokenEntity;
import org.lcr.server.vo.entity.BackUserInfoEntity;
import org.lcr.server.vo.ResponseVo;
import org.lcr.server.vo.TokenRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户验证Service类
 */
@Service
public class TokenServiceImpl implements TokenService {

  @Autowired
  private TokenDao tokenDao;

  public ResponseVo getToken(TokenRequestVo requestVo) {
    BackTokenEntity vo = new BackTokenEntity();

    vo.setLoginName(requestVo.getLoginName());
    vo.setPassword(requestVo.getPassword());

    BackTokenEntity ret1 = tokenDao.getToken(vo);

    ResponseVo result = new ResponseVo();

    if (ret1 == null) {
      result.setCode(Message.USER_NOT_EXISTS_CODE);
      result.setMessage(Message.USER_NOT_EXISTS_MSG);
    } else {

      BackUserInfoEntity ret2 = tokenDao.getUserInfo(vo);

      if (ret2 == null) {
        result.setCode(Message.USER_NOT_EXISTS_CODE);
        result.setMessage(Message.USER_NOT_EXISTS_MSG);
      } else {
        result.setCode(Message.SUCCESS_CODE);
        result.setMessage(Message.SUCCESS_MSG);
        result.setEntity(ret2);
      }
    }

    return result;
  }
}
