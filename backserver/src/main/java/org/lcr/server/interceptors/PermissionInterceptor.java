package org.lcr.server.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/***
 * 全局拦截器
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

	/**
	 * controller 方法调用执行前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Object backUser =  request.getSession().getAttribute("backUser");
		String currUrl=request.getRequestURI();
		if(backUser==null){
			boolean flg=true;
			flg=filterChar(currUrl);
		
			if(flg){
				logger.error("not logged in , request "+currUrl +" to response:login");
				throw new ModelAndViewDefiningException(new ModelAndView("redirect:/login"));
			}
		}
		return super.preHandle(request, response, handler);
	}

	/**
	 * controller 方法调用执行后
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
			super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * 过滤请求
	 * @param currUrl
	 * @return
	 */
	private boolean filterChar(String currUrl){
		String []urlList={"/login","/","","/action/"};//不过滤的登录白名单
		String []urlFixs={"css","js","jpg","woff","png"};
		currUrl=currUrl.substring(5);
		boolean flg=true;
		lable :for (int i = 0; i < urlList.length; i++) {
			String dburl=urlList[i];
			String prefix="";
			if(currUrl.length()>0){
				int idex=currUrl.lastIndexOf('.');
				if(idex!=-1){
					prefix=currUrl.substring(idex, currUrl.length());
					for (int j = 0; j < urlFixs.length; j++) {
						if(prefix.equals(prefix)){
							flg=false;
							break lable;
						}
					}
				}
			}
			if(currUrl.equals(dburl)){
				flg=false;
				break; 
			}
			if(flg==false){
				break;
			}
		}
		
		return flg;
	}

}
