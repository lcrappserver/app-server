package org.lcr.server.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/18.
 */
public class TokenVo implements Serializable{

    private static final long serialVersionUID = 1190411849865542449L;

    private String accessToken;

    private Integer expiresIn;

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
