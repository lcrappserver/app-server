package org.lcr.server.tools;

import org.apache.commons.lang3.StringUtils;
import org.lcr.server.common.Contact;
import org.lcr.server.common.Message;
import org.lcr.server.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AccessTokenUtils {

    private static Logger logger = LoggerFactory.getLogger(AccessTokenUtils.class);

    /**
     * 设置accessToken的session
     *
     * @param request     HttpServletRequest
     * @param accessToken token值
     */
    public static void setAccessToken(HttpServletRequest request, HttpServletResponse response,
                                      String accessToken) {
        request.getSession().setAttribute(accessToken, new Date());
        request.getSession().setMaxInactiveInterval(Contact.TOKEN_EXPIRES_IN);
        boolean flag = false;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("JSESSIONID".equals(c.getName()) && StringUtils.isNotEmpty(c.getValue())) {
                    flag = true;
                    break;
                }
            }
        }

        if (!flag) {
            logger.debug("cookie JSESSIONID is null");
            response.addCookie(new Cookie("JSESSIONID", request.getSession().getId()));
        }
    }

    /**
     * 校验accessToken
     *
     * @param request     HttpServletRequest
     * @param accessToken token值
     * @return true:校验成功；false:校验失败
     */
    public static boolean checkAccessToken(HttpServletRequest request,
                                           String accessToken,
                                           HttpServletResponse response,
                                           ResponseVo result) {
        boolean ret = false;
        Object localValue = request.getSession().getAttribute(accessToken);
        if (localValue != null) {
            Date dt = (Date) localValue;
            if (new Date().compareTo(new Date(dt.getTime() + Contact.TOKEN_EXPIRES_IN * 1000)) > 0) {
                request.getSession().removeAttribute(accessToken);
            } else {
                ret = true;
            }
        }

        if (!ret) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            result.setCode(Message.AUTH_ERROR_CODE);
            result.setMessage(Message.AUTH_ERROR_MSG);
        }

        return ret;
    }
}
