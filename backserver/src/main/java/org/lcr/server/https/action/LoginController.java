package org.lcr.server.https.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登录页面跳转
 */
@Controller
public class LoginController {

  @RequestMapping(value = "/login", method = {RequestMethod.GET})
  public String login() {
    return "login";
  }
}
