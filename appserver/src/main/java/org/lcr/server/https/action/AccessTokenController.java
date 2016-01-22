package org.lcr.server.https.action;

import com.alibaba.fastjson.JSONObject;
import org.lcr.server.common.Message;
import org.lcr.server.service.AccessTokenService;
import org.lcr.server.tools.AccessTokenUtils;
import org.lcr.server.tools.EncodeUtils;
import org.lcr.server.vo.ResponseVo;
import org.lcr.server.vo.TokenRequestVo;
import org.lcr.server.vo.TokenVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/app")
public class AccessTokenController {

  private static Logger logger = LoggerFactory.getLogger(AccessTokenController.class);

  @Autowired
  private AccessTokenService accessTokenService;

  @ResponseBody
  @RequestMapping(value = "/token", produces = "application/json; charset=utf-8", method = {
      RequestMethod.GET})
  public String token(@RequestParam(value = "login_name", required = true) String loginName,
      @RequestParam(value = "password", required = true) String password,
      HttpServletRequest request, HttpServletResponse response) throws IOException {
    logger.debug("token start");

    long start = System.currentTimeMillis();

    TokenRequestVo requestVo = new TokenRequestVo();

    requestVo.setLoginName(loginName);
    requestVo.setPassword(EncodeUtils.shaEncode(password));

    ResponseVo result = accessTokenService.token(requestVo);

    if (Message.SUCCESS_CODE.equals(result.getCode())) {
      TokenVo tokenVo = (TokenVo) result.getEntity();
      AccessTokenUtils.setAccessToken(request, response, tokenVo.getAccessToken());
    }

    result.setMillis(System.currentTimeMillis() - start);

    logger.debug("token end");

    return JSONObject.toJSONString(result);
  }
}
