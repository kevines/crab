package com.wentuo.crab.modular.mini.service.oss;

import org.springframework.web.multipart.MultipartFile;

/**
 * 功能描述：阿里云oss文件操作相关
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName UploadService
 * @since 2019/10/28 11:20
 */
public interface OssFileService {

    /**
     * 上传图片接口
     * @param picture 图片文件
     * @param size 限定上传图片大小
     * @return
     */
    Object uploadImage(MultipartFile picture, Integer size);

    /**
     * 上传文件接口
     * @param multipartFile
     * @param size 限定上传文件大小
     * @return
     */
    Object uploadFile(MultipartFile multipartFile, Integer size);

    /**
     * 删除文件接口
     * @param url
     * @return
     */
    Object deleteFile(String url);
}
