package org.lcr.server.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeUtils {

    private static Logger logger = LoggerFactory.getLogger(EncodeUtils.class);

    /**
     * 将摘要信息转换为相应的编码
     * @param code 编码类型
     * @param message 摘要信息
     * @return 相应的编码字符串
     */
    private static String Encode(String code,String message){
        MessageDigest md;
        String encode = "";
        try {
            md = MessageDigest.getInstance(code);
            byte[] bts = md.digest(message.getBytes());
            String tmp = null;
            for (int i = 0; i < bts.length; i++) {
                tmp = (Integer.toHexString(bts[i] & 0xFF));
                if (tmp.length() == 1) {
                    encode += "0";
                }
                encode += tmp;
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        return encode;
    }

    /**
     * 将摘要信息转换成MD5编码
     * @param message 摘要信息
     * @return MD5编码之后的字符串
     */
    public static String md5Encode(String message){
        return Encode("MD5",message);
    }
    /**
     * 将摘要信息转换成SHA编码
     * @param message 摘要信息
     * @return SHA编码之后的字符串
     */
    public static String shaEncode(String message){
        return Encode("SHA",message);
    }
    /**
     * 将摘要信息转换成SHA-256编码
     * @param message 摘要信息
     * @return SHA-256编码之后的字符串
     */
    public static String sha256Encode(String message){
        return Encode("SHA-256",message);
    }
    /**
     * 将摘要信息转换成SHA-512编码
     * @param message 摘要信息
     * @return SHA-512编码之后的字符串
     */
    public static String sha512Encode(String message){
        return Encode("SHA-512",message);
    }

}
