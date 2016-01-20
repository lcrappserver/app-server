package org.lcr.server.entity;

import java.io.Serializable;

/**
 * 用户信息
 */
@SuppressWarnings("unused")
public class BackUserInfoEntity implements Serializable {

  private static final long serialVersionUID = -4446903570090253959L;

  private String userId;

  private String email;

  private String mobile;

  private String realName;

  private String icon;

  private String sex;

  private String degree;

  private BackTokenEntity backTokenEntity;

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public BackTokenEntity getBackTokenEntity() {
    return backTokenEntity;
  }

  public void setBackTokenEntity(BackTokenEntity backTokenEntity) {
    this.backTokenEntity = backTokenEntity;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }
}
