package org.lcr.server.https.action;

import org.lcr.server.common.Contants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 菜单页跳转
 */
@Controller
public class MenuController {

  @RequestMapping(value = "/menu", method = {RequestMethod.GET})
  public String menu() {
    return "menu";
  }

  @RequestMapping(value = "/logout", method = {RequestMethod.GET})
  public String logout(HttpServletRequest req) {
    req.getSession().removeAttribute(Contants.LOGIN_USER_SESSION);
    return "redirect:/login";
  }
}
