package com.wentuo.crab.util.aes;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

/**
 * 2018年7月4日13:49:56
 * @author ngh
 * AES128 算法
 *
 * CBC 模式
 *
 * PKCS7Padding 填充模式
 *
 * CBC模式需要添加一个参数iv--对称解密算法初始向量 iv
 *
 * 介于java 不支持PKCS7Padding，只支持PKCS5Padding 但是PKCS7Padding 和 PKCS5Padding 没有什么区别
 * 要实现在java端用PKCS7Padding填充，需要用到bouncycastle组件来实现
 */
public class Pkcs7Encoder {
   
    private static final String WATERMARK = "watermark";
    private static final String APPID = "appid";
    
    public static String decrypt(String appId,String encryptedData, String sessionKey, String iv){
        String result = "";
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                result = new String(WxPKCS7Encoder.decode(resultByte));
                JSONObject jsonObject = JSONObject.fromObject(result);
                String decryptAppid = jsonObject.getJSONObject(WATERMARK).getString(APPID);
                if(!appId.equals(decryptAppid)){
                    result = "";
                }
            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }
    
}