package org.lcr.server.https.action;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.lcr.server.vo.entity.BackUserInfoEntity;
import org.lcr.server.tools.JsonValidator;
import org.lcr.server.common.Contants;
import org.lcr.server.common.Message;
import org.lcr.server.service.TokenService;
import org.lcr.server.vo.ResponseVo;
import org.lcr.server.vo.TokenRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Controller
@RequestMapping(value = "/back")
public class TokenController {

  private static Logger logger = LoggerFactory.getLogger(TokenController.class);

  @Autowired
  private TokenService tokenService;

  @ResponseBody
  @RequestMapping(value = "/token.do", produces = "application/json; charset=utf-8", method = {
      RequestMethod.POST})
  public String token(@RequestBody String requestBody, HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    logger.debug("token start");

    long start = System.currentTimeMillis();

    JsonValidator json = new JsonValidator();
    TokenRequestVo requestVo = new TokenRequestVo();

    if (json.validate(requestBody)) {
      requestVo = JSONObject.parseObject(requestBody, TokenRequestVo.class);
    } else {
      List<NameValuePair> params = URLEncodedUtils.parse(requestBody, Charset.forName("UTF-8"));
      requestVo.namevalueBind(params);
    }

    ResponseVo result = tokenService.getToken(requestVo);

    if (!Message.SUCCESS_CODE.equals(result.getCode())) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    } else {
      response.setStatus(HttpServletResponse.SC_OK);
      BackUserInfoEntity entity = (BackUserInfoEntity) result.getEntity();
      request.getSession().setAttribute(Contants.LOGIN_USER_SESSION,
          entity.getBackTokenEntity().getLoginName());
      request.getSession().setMaxInactiveInterval(1800);
    }

    result.setMillis(System.currentTimeMillis() - start);

    logger.debug("token end");

    return JSONObject.toJSONString(result);
  }

}
