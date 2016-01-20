package org.lcr.server.https.action;

import com.alibaba.fastjson.JSONObject;
import org.lcr.server.common.Message;
import org.lcr.server.tools.AccessTokenUtils;
import org.lcr.server.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ProductController {

  private static Logger logger = LoggerFactory.getLogger(ProductController.class);

  @ResponseBody
  @RequestMapping(value = "/search", produces = "application/json; charset=utf-8", method = {
      RequestMethod.GET})
  public String token(@RequestParam(value = "access_token", required = true) String accessToken,
      @RequestParam(value = "prod_name", required = false) String prodName,
      HttpServletRequest request, HttpServletResponse response) throws IOException {

    logger.debug("token start");

    long start = System.currentTimeMillis();

    ResponseVo result = new ResponseVo();

    if (!AccessTokenUtils.checkAccessToken(request, accessToken)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      result.setCode(Message.AUTH_ERROR_CODE);
      result.setMessage(Message.AUTH_ERROR_MSG);
    } else {
      result.setCode(Message.SUCCESS_CODE);
      result.setMessage(Message.SUCCESS_MSG);
    }

    result.setMillis(System.currentTimeMillis() - start);

    logger.debug("token end");

    return JSONObject.toJSONString(result);

  }
}
