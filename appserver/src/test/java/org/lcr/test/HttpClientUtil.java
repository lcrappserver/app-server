package org.lcr.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http 请求
 * 
 * @author Administrator
 *
 */
public class HttpClientUtil {
	private static Logger logger = LoggerFactory
			.getLogger(HttpClientUtil.class);

	private static RequestConfig config = null;
	private static HttpClientContext httpContext = null;
	private static Registry<ConnectionSocketFactory> registry = null;
	private static CookieStore cookieStore = null;

	// Increase socket timeout connection to 20s
	private static final int DEFAULT_SOCKET_TIMEOUT = 20000;
	// Increase max per route to 400
	private static final int DEFAULT_MAX_PER_ROUTE = 400;
	// Increase max total to 800
	private static final int DEFAULT_MAX_TOTAL = 800;
	// Increase connect timeout to 20s
	private static final int DEFAULT_CONNECT_TIMEOUT = 20000;
	// Increase connect request timeout to 20s
	private static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 20000;

	static {
		init();
	}

	private static void init() {
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
				.getSocketFactory();
		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
				.getSocketFactory();
		registry = RegistryBuilder
				.<ConnectionSocketFactory> create().register("http", plainsf)
				.register("https", sslsf).build();
		httpContext = HttpClientContext.create();
		cookieStore = new BasicCookieStore();
		config = RequestConfig.custom()
				.setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
				.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT)
				.build();
	}

	/**
	 * 手动增加cookie
	 * @param name
	 * @param value
	 * @param domain
	 * @param path
	 */
	public static void addCookie(String name, String value, String domain, String path) {
		BasicClientCookie cookie = new BasicClientCookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookieStore.addCookie(cookie);
	}

	/**
	 * 手动增加cookie
	 * @param cookie
     */
	public static void addCookie(Cookie cookie) {
		cookieStore.addCookie(cookie);
	}

	/**
	 * 获取CookieStore
	 *
	 */
	public static CookieStore getCookieStore() {
		return cookieStore;
	}
	
	/**
	 * 测试webservice是否可以正常访问
	 * @param url
	 * @return
	 */
	public static boolean testWsdlConnection(String url) {
		boolean result = false;  
        try {  
            URL urlObj = new URL(url);  
            HttpURLConnection oc = (HttpURLConnection) urlObj.openConnection();  
            oc.setUseCaches(false);  
            oc.setConnectTimeout(3000); //设置超时时间  
            int status = oc.getResponseCode();//请求状态  
            if(200 == status){  
                return true;  
            }             
        } catch (MalformedURLException e) {  
            logger.error("请求地址不通。。。url:[{}]",url);  
        } catch (IOException e) {
            logger.error("请求地址不通。。。url:[{}]",url);  
        }catch (Exception e) {
            logger.error("请求地址不通。。。url:[{}]",url);  
        }  
        return result; 
	}

	//建立连接池
	private static HttpClientConnectionManager createConnectionManager (String url, boolean pool) {
		if (pool) {
			// 初始化连接管理器
			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
			connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL);
			connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
			SocketConfig socketConfig = SocketConfig.custom()
					.setSoTimeout(DEFAULT_SOCKET_TIMEOUT).build();
			connectionManager.setDefaultSocketConfig(socketConfig);
			// 增加目标主机的最大连接数
			connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost(url)),
					DEFAULT_MAX_PER_ROUTE);
			return connectionManager;
		} else {
			return new BasicHttpClientConnectionManager(registry);
		}
	}
	
	public static String get(String url)
			throws IOException {
		return get(url, false);
	}

	public static String get(String url, boolean pool)
			throws IOException {
		Long timeA = System.currentTimeMillis();
		//建立连接池
		HttpClientConnectionManager connectionManager = createConnectionManager(url, pool);
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.setConnectionManagerShared(true)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setRedirectStrategy(new DefaultRedirectStrategy())
				.setDefaultRequestConfig(config)
				.setDefaultCookieStore(cookieStore).build();
		HttpGet httpGet = new HttpGet(url);
		try {
			httpGet.setConfig(config);
			logger.debug("post [{}] ----- Start.", url);
			CloseableHttpResponse response = httpClient.execute(httpGet,
					httpContext);
			cookieStore = httpContext.getCookieStore();
			HttpEntity entity = response.getEntity();
			InputStream instream = entity.getContent();
			logger.debug("post [{}] ----- End.", url);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					instream, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String data;
			while ((data = in.readLine()) != null) {
				sb.append(data);
				sb.append("\n");
			}
			
			in.close();
			instream.close();
			response.close();
				
			return sb.toString().trim();
		} finally {
			Long timeB = System.currentTimeMillis();
			logger.debug("post [{}] 耗时:{}",
					new Object[] { url, (timeB - timeA) });
			if (httpClient != null)
				httpClient.close();
			connectionManager.shutdown();
		}
	}

	public static String postJSON(String url, String jsonString)
			throws IOException {
		return postJSON(url, jsonString, false);
	}

	public static String postJSON(String url, String jsonString, boolean pool)
			throws IOException {
		Long timeA = System.currentTimeMillis();
		//建立连接池
		HttpClientConnectionManager connectionManager = createConnectionManager(url, pool);
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.setConnectionManagerShared(true)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setRedirectStrategy(new DefaultRedirectStrategy())
				.setDefaultRequestConfig(config)
				.setDefaultCookieStore(cookieStore).build();
		HttpPost httpPost = new HttpPost(url);
		try {
			if (StringUtils.isNotEmpty(jsonString)) {
				StringEntity params = new StringEntity(jsonString, "UTF-8");
				httpPost.addHeader("content-type", "application/json");
				httpPost.setEntity(params);
			}
			httpPost.setConfig(config);
			logger.debug("post [{}] ----- Start.", url);
			CloseableHttpResponse response = httpClient.execute(httpPost,
					httpContext);
			HttpEntity entity = response.getEntity();
			InputStream instream = entity.getContent();
			logger.debug("post [{}] ----- End.", url);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					instream, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String data;
			while ((data = in.readLine()) != null) {
				sb.append(data);
				sb.append("\n");
			}
			
			in.close();
			instream.close();
			response.close();
				
			return sb.toString().trim();
		} finally {
			Long timeB = System.currentTimeMillis();
			logger.debug("post [{}] 耗时:{}",
					new Object[] { url, (timeB - timeA) });
			if (httpClient != null)
				httpClient.close();
			connectionManager.shutdown();
		}
	}

	public static String postXml(String url, String xmlString,
			Integer soTimeout, Integer connectionTimeout, String charset, boolean pool)
			throws IOException {
		Long timeA = System.currentTimeMillis();
		//建立连接池
		HttpClientConnectionManager connectionManager = createConnectionManager(url, pool);
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.setConnectionManagerShared(true)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setRedirectStrategy(new DefaultRedirectStrategy())
				.setDefaultRequestConfig(config)
				.setDefaultCookieStore(cookieStore).build();
		HttpPost httpPost = new HttpPost(url);		
		try {
			StringEntity params = new StringEntity(xmlString, charset);
			params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/xml"));
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(soTimeout)
					.setConnectTimeout(connectionTimeout).build();
			httpPost.setConfig(requestConfig);
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/xml");
			httpPost.setEntity(params);
			logger.debug("post [{}] ----- Start.", url);
			CloseableHttpResponse response = httpClient.execute(httpPost,
					httpContext);
			HttpEntity entity = response.getEntity();
			logger.debug("post [{}] ----- End.", url);
			xmlString = EntityUtils.toString(entity, charset);
			
			response.close();
			
			return xmlString;
		} finally {
			Long timeB = System.currentTimeMillis();
			logger.debug("post [" + url + "] 耗时:" + (timeB - timeA));
			if (httpClient != null)
				httpClient.close();
			connectionManager.shutdown();
		}
	}
}
