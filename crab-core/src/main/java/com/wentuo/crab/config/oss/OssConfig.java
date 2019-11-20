package com.wentuo.crab.config.oss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述：
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName OssConfuig
 * @since 2019/11/20 16:15
 */
@Configuration
public class OssConfig {

    //阿里云API的外网域名
    public static String ENDPOINT;
    //阿里云API的密钥Access Key ID
    public static String ACCESS_KEY_ID;
    //阿里云API的密钥Access Key Secret
    public static String ACCESS_KEY_SECRET;
    //阿里云API的bucket名称
    public static String BUCKET_NAME;
    //阿里云API的文件夹名称
    public static String FOLDER;
    /**
     * bucket绑定的域名
     */
    public static String DOMAIN;

    @Value("${aliyun.oss.endpoint.out}")
    public void setENDPOINT(String ENDPOINT) {
        OssConfig.ENDPOINT = ENDPOINT;
    }

    @Value("${aliyun.oss.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        OssConfig.ACCESS_KEY_ID = accessKeyId;
    }

    @Value("${aliyun.oss.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        OssConfig.ACCESS_KEY_SECRET = accessKeySecret;
    }

    @Value("${aliyun.oss.bucket}")
    public void setBucketName(String bucketName) {
        BUCKET_NAME = bucketName;
    }

    @Value("${aliyun.oss.folder}")
    public void setFOLDER(String FOLDER) {
        OssConfig.FOLDER = FOLDER;
    }
    @Value("${aliyun.oss.endpoint.domain}")
    public void setDOMAIN(String DOMAIN) {
        OssConfig.DOMAIN = DOMAIN;
    }

}
