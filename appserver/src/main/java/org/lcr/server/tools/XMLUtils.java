package org.lcr.server.tools;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
            Iterator<String> it = toMap.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (StringUtils.isNotEmpty(key)) {
                    Object obj = toMap.get(key);
                    Element element = DocumentHelper.createElement(key);
                    if (obj != null) {
                        if (obj instanceof java.lang.String) {
                            element.setText((String) obj);
                        } else {
                            if (obj instanceof java.lang.Character || obj instanceof java.lang.Boolean || obj instanceof java.lang.Number
                                    || obj instanceof java.math.BigInteger || obj instanceof java.math.BigDecimal) {
                                element.setText(String.valueOf(obj));
                            } else if (obj instanceof java.util.Map) {
                                __buildMap2xmlBody(element, (Map<String, Object>) obj);
                            } else {
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
     * @param xml
     * @param rootElement
     * @return
     * @throws DocumentException
     */
    public static <T> T toVoObject(String xml, String rootElement, Class<T> clazz) throws DocumentException {
        org.dom4j.Document doc = DocumentHelper.parseText(xml);
        Element root = (Element) doc.selectSingleNode("/" + rootElement);
        Map<String, Object> vo = __buildXmlBody2map(root);
        return JSONObject.parseObject(JSONObject.toJSONString(vo), clazz);
    }

    /**
     * 根据xml消息体转化为Map
     *
     * @param xml
     * @param rootElement
     * @return
     * @throws DocumentException
     */
    public static Map<String, Object> toVoMap(String xml, String rootElement) throws DocumentException {
        Document doc = DocumentHelper.parseText(xml);
        Element root = (Element) doc.selectSingleNode("/" + rootElement);
        return __buildXmlBody2map(root);
    }

    private static Map<String, Object> __buildXmlBody2map(Element body) {
        Map<String, Object> vo = new HashMap<String, Object>();
        if (body != null) {
            List<Element> elements = body.elements();
            for (Element element : elements) {
                String key = element.getName();
                if (StringUtils.isNotEmpty(key)) {
                    Object value = null;
                    if (element.elements() != null && element.elements().size() > 0) {
                        value = __buildXmlBody2map(element);
                    } else {
                        String type = element.attributeValue("type", "java.lang.String");
                        String text = element.getText().trim();
                        if (java.lang.String.class.getCanonicalName().equals(type)) {
                            value = text;
                        } else if (java.lang.Character.class.getCanonicalName().equals(type)) {
                            value = new java.lang.Character(text.charAt(0));
                        } else if (java.lang.Boolean.class.getCanonicalName().equals(type)) {
                            value = new java.lang.Boolean(text);
                        } else if (java.lang.Short.class.getCanonicalName().equals(type)) {
                            value = java.lang.Short.parseShort(text);
                        } else if (java.lang.Integer.class.getCanonicalName().equals(type)) {
                            value = java.lang.Integer.parseInt(text);
                        } else if (java.lang.Long.class.getCanonicalName().equals(type)) {
                            value = java.lang.Long.parseLong(text);
                        } else if (java.lang.Float.class.getCanonicalName().equals(type)) {
                            value = java.lang.Float.parseFloat(text);
                        } else if (java.lang.Double.class.getCanonicalName().equals(type)) {
                            value = java.lang.Double.parseDouble(text);
                        } else if (java.math.BigInteger.class.getCanonicalName().equals(type)) {
                            value = new java.math.BigInteger(text);
                        } else if (java.math.BigDecimal.class.getCanonicalName().equals(type)) {
                            value = new java.math.BigDecimal(text);
                        } else if (java.util.Map.class.getCanonicalName().equals(type)) {
                            value = __buildXmlBody2map(element);
                        } else {
                        }
                    }
                    vo.put(key, value);
                }
            }
        }
        return vo;
    }
}
