package org.lcr.server.vo;

import java.io.Serializable;

/**
 * 统一返回vo
 */
@SuppressWarnings("unused")
public class ResponseVo implements Serializable {

  private static final long serialVersionUID = 687847351282216522L;

  private String code;

  private Object entity;

  private String message;

  private long millis;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Object getEntity() {
    return entity;
  }

  public void setEntity(Object entity) {
    this.entity = entity;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public long getMillis() {
    return millis;
  }

  public void setMillis(long millis) {
    this.millis = millis;
  }
}
