/**
 * BEYONDSOFT.COM INC
 */
package com.wentuo.crab.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.shiro.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;


/**
 * @author loujinglong
 * @version $Id: ImageUtils.java, v 0.1 2017年9月29日 下午3:53:22 loujinglong Exp $
 */
public class OssUtil {

    @Autowired
    private Environment environment;

    // OOS地址 Endpoint以杭州为例，其它Region请按实际情况填写
    private static final String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private static final String accessKeyId = "LTAI4FiS5EnTeP5Qei138tS4";
    private static final String accessKeySecret = "wAoF3oDeR7jOqnKeSoSFL6zFUfpkuN";
    private static final String domain = "https://daikin-mini.oss-cn-hangzhou.aliyuncs.com/";
    private static final String bucketName = "daikin-mini";


    /**
     * 判断文件大小
     *
     * @param len  文件长度
     * @param size 限制大小
     * @param unit 限制单位（B,K,M,G）
     * @return
     */
    public static boolean checkFileSize(Long len, int size, String unit) {
//        long len = file.length();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }


    /**
     * 功能描述: 直接上传文件
     * @author wangbencheng
     * @since 2019/8/20 13:03
     * @param file
     * @return
     */
    public static String ossUpload(InputStream file) {
        String date = DateUtils.format(new Date(), DateUtils.DEFAULT1);
        String dateStr = DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS1);
        String pictureName = "img/" + date + "/" + UUID.randomUUID().toString() + dateStr + ".png";
        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "b6M7ONqcAbm2UV1K";
//        String accessKeySecret = "XkXXQSDIKnKdGXafL3SXGfK7FJa9oC";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        ossClient.putObject(bucketName, pictureName, file);
        // 关闭OSSClient。
        ossClient.shutdown();
        return domain + pictureName;
    }

    /**
     * 功能描述: 上传本地文件
     * @author wangbencheng
     * @since 2019/8/20 13:08
     * @param localFilePath
     * @return
     */
    public static String localFile(String localFilePath) {
        String date = DateUtils.format(new Date(), DateUtils.DEFAULT1);
        String dateStr = DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS1);
        String pictureName = "img/" + date + "/" + UUID.randomUUID().toString() + dateStr + ".png";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, pictureName, new File(localFilePath));
        System.out.println(putObjectResult);
        // 关闭OSSClient。
        ossClient.shutdown();
        return domain + pictureName;

    }

   /**
    * 功能描述: 直接上传文件
    * @author wangbencheng
    * @since 2019/8/20 13:09
    * @param file
    * @param prefix 文件尾缀
    * @return
    */
    public static String ossUploadFile(InputStream file, String prefix) {
        String date = DateUtils.format(new Date(), DateUtils.DEFAULT1);
        String dateStr = DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS1);
        String pictureName = "file/" + date + "/" + dateStr + "." + prefix;
        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "b6M7ONqcAbm2UV1K";
//        String accessKeySecret = "XkXXQSDIKnKdGXafL3SXGfK7FJa9oC";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        ossClient.putObject(bucketName, pictureName, file);
        // 关闭OSSClient。
        ossClient.shutdown();
        return domain + pictureName;
    }


    /**
     * 简单上传图片：根据图片路径保存图象。
     * author ljh
     * 2018年12月17日19:27:38
     *
     * @return
     */
    public static Boolean ossUploadPhoto(String file, String fileName) {
        try {
            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            // 上传网络流。
            InputStream inputStream = new URL(file).openStream();
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (IOException e) {
            e.getMessage();
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除阿里云文件
     * @param url
     * @return
     */
    public static String deleteFile(String url) {
        String ossKey = url.split(domain)[1];
        try {
            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            ossClient.deleteObject(bucketName, ossKey);
            // 关闭OSSClient。
            ossClient.shutdown();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
