package org.lcr.server.vo;

import org.apache.http.NameValuePair;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/13.
 */
public class TokenRequestVo implements Serializable {

    private static final long serialVersionUID = 1606949021304358350L;

    private String loginName;

    private String password;

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

    public void namevalueBind(List<NameValuePair> params) {
        Map<String, String> map = new HashMap<String, String>();
        for (NameValuePair param : params) {
            map.put(param.getName(), param.getValue());
        }
        this.loginName = map.get("loginName");
        this.password = map.get("password");
    }
}
