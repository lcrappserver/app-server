package org.lcr.server.tools;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class XMLUtils {

  /**
   * 根据vo组装xml消息体，值对象仅支持基本数据类型、String、BigInteger、BigDecimal，以及包含元素为上述支持数据类型的Map
   *
   * @param object vo对象
   * @param rootElement 根路径名
   * @return xml
   */
  public static String toXmlString(Object object, String rootElement) {
    Document doc = DocumentHelper.createDocument();
    Element root = DocumentHelper.createElement(rootElement);
    doc.add(root);
    __buildMap2xmlBody(root, object);
    return doc.asXML();
  }

  private static void __buildMap2xmlBody(Element body, Object object) {
    Map<String, Object> toMap = JSONObject.parseObject(JSONObject.toJSONString(object));
    if (toMap != null) {
      for (String key : toMap.keySet()) {
        if (StringUtils.isNotEmpty(key)) {
          Object obj = toMap.get(key);
          Element element = DocumentHelper.createElement(key);
          if (obj != null) {
            if (obj instanceof String) {
              element.setText((String) obj);
            } else {
              if (obj instanceof Character || obj instanceof Boolean || obj instanceof Number) {
                element.setText(String.valueOf(obj));
              } else if (obj instanceof Map) {
                __buildMap2xmlBody(element, new BeanMap(obj));
              }
            }
          }
          body.add(element);
        }
      }
    }
  }

  /**
   * 根据xml消息体转化为vo
   *
   * @param xml xml
   * @param rootElement xml的根名称
   * @return vo
   * @throws DocumentException
   */
  public static <T> T toVoObject(String xml, String rootElement, Class<T> clazz)
      throws DocumentException {
    Document doc = DocumentHelper.parseText(xml);
    Element root = (Element) doc.selectSingleNode("/" + rootElement);
    Map<String, Object> vo = __buildXmlBody2map(root);
    return JSONObject.parseObject(JSONObject.toJSONString(vo), clazz);
  }

  /**
   * 根据xml消息体转化为Map
   *
   * @param xml xml
   * @param rootElement xml的根名称
   * @return Map
   * @throws DocumentException
   */
  public static Map<String, Object> toVoMap(String xml, String rootElement)
      throws DocumentException {
    Document doc = DocumentHelper.parseText(xml);
    Element root = (Element) doc.selectSingleNode("/" + rootElement);
    return __buildXmlBody2map(root);
  }

  private static Map<String, Object> __buildXmlBody2map(Element body) {
    Map<String, Object> vo = new HashMap<String, Object>();
    if (body != null) {
      List elements = body.elements();
      for (Object obj : elements) {
        Element element = (Element) obj;
        String key = element.getName();
        if (StringUtils.isNotEmpty(key)) {
          Object value = null;
          if (element.elements() != null && element.elements().size() > 0) {
            value = __buildXmlBody2map(element);
          } else {
            String type = element.attributeValue("type", "java.lang.String");
            String text = element.getText().trim();
            if (String.class.getCanonicalName().equals(type)) {
              value = text;
            } else if (Character.class.getCanonicalName().equals(type)) {
              value = text.charAt(0);
            } else if (Boolean.class.getCanonicalName().equals(type)) {
              value = Boolean.valueOf(text);
            } else if (Short.class.getCanonicalName().equals(type)) {
              value = Short.parseShort(text);
            } else if (Integer.class.getCanonicalName().equals(type)) {
              value = Integer.parseInt(text);
            } else if (Long.class.getCanonicalName().equals(type)) {
              value = Long.parseLong(text);
            } else if (Float.class.getCanonicalName().equals(type)) {
              value = Float.parseFloat(text);
            } else if (Double.class.getCanonicalName().equals(type)) {
              value = Double.parseDouble(text);
            } else if (java.math.BigInteger.class.getCanonicalName().equals(type)) {
              value = new java.math.BigInteger(text);
            } else if (java.math.BigDecimal.class.getCanonicalName().equals(type)) {
              value = new java.math.BigDecimal(text);
            } else if (Map.class.getCanonicalName().equals(type)) {
              value = __buildXmlBody2map(element);
            }
          }
          vo.put(key, value);
        }
      }
    }
    return vo;
  }
}
