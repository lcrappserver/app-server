package org.lcr.server.vo.entity;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("unused")
@Alias(value = "AccessTokenEntity")
public class AccessTokenEntity implements Serializable {

    private static final long serialVersionUID = 3204873801685152409L;

    private String loginName;

    private String password;

    private String accessToken;

    private Integer expiresIn;

    private Date startDate;

    private String userId;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

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

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
