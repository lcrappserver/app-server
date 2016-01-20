package org.lcr.server.entity;

import java.io.Serializable;

/**
 * 用户验证
 */
@SuppressWarnings("unused")
public class BackTokenEntity implements Serializable {

  private static final long serialVersionUID = -934811494644746426L;

  private String loginName;

  private String password;

  private String accessToken;

  private String userId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
