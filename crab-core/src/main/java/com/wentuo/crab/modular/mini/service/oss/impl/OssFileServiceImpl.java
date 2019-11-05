package com.wentuo.crab.modular.mini.service.oss.impl;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import com.wentuo.crab.modular.mini.service.oss.OssFileService;
import com.wentuo.crab.util.OssUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能描述：阿里云Oss文件操作服务接口实现
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName OssFileServiceImpl
 * @since 2019/10/28 11:23
 */
public class OssFileServiceImpl implements OssFileService {

    @Override
    public Object uploadImage(MultipartFile picture, Integer size) {
        String fileSavePath = "";
        String filename = picture.getOriginalFilename();
        try {
            boolean imageFile = OssUtil.checkFileSize(picture.getSize(), size, "M");
            if (!imageFile) {
                return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "上传图片不能超过20m");
            }
            if (filename.toUpperCase().indexOf(".JPG") > 0 || filename.toUpperCase().indexOf(".PNG") > 0 || filename.toUpperCase().indexOf(".JPEG") > 0) {
                fileSavePath = OssUtil.ossUpload(picture.getInputStream());
            } else {
                return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "图片不符合格式");
            }
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }

        return ResponseData.success(fileSavePath);
    }

    @Override
    public Object uploadFile(MultipartFile multipartFile, Integer size) {
        String fileSavePath = "";
        String filename = multipartFile.getOriginalFilename();
        String prefix=filename.substring(filename.lastIndexOf(".")+1);
        try {
            boolean file = OssUtil.checkFileSize(multipartFile.getSize(), size, "M");
            if (!file) {
                return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "上传文件不能超过100m");
            }
            fileSavePath = OssUtil.ossUploadFile(multipartFile.getInputStream(),prefix);
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }
        return ResponseData.success(fileSavePath);
    }

    @Override
    public Object deleteFile(String url) {
        String result = OssUtil.deleteFile(url);
        if (result.equals("success")) {
            return ResponseData.success(result);
        } else {
            return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "删除失败");
        }
    }
}
