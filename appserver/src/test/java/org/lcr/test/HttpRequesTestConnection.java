package org.lcr.test;

import java.io.IOException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.dom4j.DocumentException;

/**
 * 测试
 * 
 * @author FSHITD148
 *
 */
public class HttpRequesTestConnection {

	private static CookieStore cookieStore = null;

	private static String token() throws IOException, DocumentException {

		String url="";
		long start = 0L;
		String response = "";

		url = "http://localhost:8081/appserver/app/token.do?login_name=admin&password=system";

		start = System.currentTimeMillis();
		response = HttpClientUtil.get(url);
		cookieStore = HttpClientUtil.getCookieStore();

		if (cookieStore != null && cookieStore.getCookies() != null) {
			for(Cookie cookie : cookieStore.getCookies()) {
				System.out.println("key:" + cookie.getName()
						+ "  value:" + cookie.getValue()
						+ "  domain:" + cookie.getDomain()
						+ "  path:" + cookie.getPath());
			}
		} else {
			System.out.println("cookie null");
		}

		System.out.println("token接口处理时间：["+(System.currentTimeMillis() - start) + "ms"+"]");

		System.out.println("token接口处理结果：["+response+"]");
		Map<String, Object> map = JSONObject.parseObject(response);

		Map<String, Object> subMap = (Map<String, Object>) map.get("entity");

		System.out.println("token：["+subMap.get("accessToken")+"]");

		return subMap.get("accessToken").toString();
	}

	private static void search(String token, String cookievalue) throws IOException {

		String url="";
		long start = 0L;
		String response = "";

		url = String.format("http://localhost:8081/appserver/app/search.do?access_token=%s", token);

		start = System.currentTimeMillis();
		if (StringUtils.isNotEmpty(cookievalue)) {
			HttpClientUtil.addCookie("JSESSIONID", cookievalue, "localhost", "/appserver/");
		} else if (cookieStore != null && cookieStore.getCookies() != null) {
			for(Cookie cookie : cookieStore.getCookies()) {
				HttpClientUtil.addCookie(cookie);
			}
		}

		response = HttpClientUtil.get(url);
		System.out.println("search接口处理时间：["+(System.currentTimeMillis() - start) + "ms"+"]");

		System.out.println("search接口处理结果：["+response+"]");
	}

	public static void main(String[] args) throws IOException, DocumentException {

		for (int i = 0;i<10;i++)
		token();

		//search("AC15147F62EB47818B81396DF733D63A", "86F47DFD04B927F7717212FAF9C24D96");

	}
}
