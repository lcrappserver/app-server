package org.lcr.server.dao;

import org.lcr.server.entity.BackTokenEntity;
import org.lcr.server.entity.BackUserInfoEntity;

/**
 * 用户验证Dao
 */
public interface TokenDao {

  /**
   * 验证用户
   * 
   * @param vo 验证信息
   * @return 验证结果
   */
  BackTokenEntity getToken(BackTokenEntity vo);

  /**
   * 获取用户信息
   * 
   * @param vo 验证信息
   * @return 用户信息
   */
  BackUserInfoEntity getUserInfo(BackTokenEntity vo);

}
