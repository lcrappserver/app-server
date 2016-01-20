package org.lcr.server.dao;

public interface TokenDao {

  /**
   * 检查token
   * 
   * @param loginName 用户名
   * @param password 密码
   * @return true 验证成功 false 验证失败
   */
  boolean checkUserExits(String loginName, String password);

}
