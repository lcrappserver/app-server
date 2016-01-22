package org.lcr.server.service;

import org.lcr.server.common.Contact;
import org.lcr.server.common.Message;
import org.lcr.server.dao.TokenDao;
import org.lcr.server.vo.ResponseVo;
import org.lcr.server.vo.TokenRequestVo;
import org.lcr.server.vo.TokenVo;
import org.lcr.server.vo.entity.AccessTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

  @Autowired
  private TokenDao tokenDao;

  public ResponseVo token(TokenRequestVo vo) {

    ResponseVo result = new ResponseVo();

    AccessTokenEntity accessTokenEntity = new AccessTokenEntity();

    accessTokenEntity.setLoginName(vo.getLoginName());
    accessTokenEntity.setPassword(vo.getPassword());

    if (tokenDao.checkUserExits(accessTokenEntity)) {
      TokenVo tokenVo = new TokenVo();
      UUID uuid = UUID.randomUUID();
      String guid = uuid.toString().toUpperCase().replaceAll("-", "");
      tokenVo.setAccessToken(guid);
      tokenVo.setExpiresIn(Contact.TOKEN_EXPIRES_IN);
      result.setCode(Message.SUCCESS_CODE);
      result.setMessage(Message.SUCCESS_MSG);
      result.setEntity(tokenVo);
    } else {
      result.setCode(Message.USER_NOT_EXISTS_CODE);
      result.setMessage(Message.USER_NOT_EXISTS_MSG);
    }

    return result;
  }
}
