package org.lcr.server.dao;

import org.lcr.server.vo.entity.AccessTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDao {

  /**
   * 检查token
   *
   * @param accessTokenEntity 用户名密码
   * @return true 验证成功 false 验证失败
   */
  boolean checkUserExits(AccessTokenEntity accessTokenEntity);

}
