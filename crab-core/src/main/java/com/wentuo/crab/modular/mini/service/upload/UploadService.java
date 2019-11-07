package com.wentuo.crab.modular.mini.service.upload;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import com.wentuo.crab.util.OssUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能描述： 上传实现相关
 *
 * @author wangbencheng
 * @version 1.0
 * @className UploadService
 * @since 2019/8/20 12:18
 */
@Service
public class UploadService {

    /**
     * 功能描述: 上传图片
     * @author wangbencheng
     * @since 2019/8/20 12:43
     * @param picture
     * @return
     */
    public Object uploadImage(MultipartFile picture) {
        String fileSavePath = "";
        String filename = picture.getOriginalFilename();
        try {
            boolean imageFile = OssUtil.checkFileSize(picture.getSize(), 20, "M");
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


    /**
     * 功能描述: 上传文件
     * @author wangbencheng
     * @since 2019/8/20 12:44
     * @param multipartFile
     * @return
     */
    public Object uploadFile(MultipartFile multipartFile) {
        String fileSavePath = "";
        String filename = multipartFile.getOriginalFilename();
        String prefix=filename.substring(filename.lastIndexOf(".")+1);
        try {
            boolean file = OssUtil.checkFileSize(multipartFile.getSize(), 100, "M");
            if (!file) {
                return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "上传文件不能超过100m");
            }
            fileSavePath = OssUtil.ossUploadFile(multipartFile.getInputStream(),prefix);
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }
        return ResponseData.success(fileSavePath);
    }

    /**
     * 删除文件
     * @param url
     * @return
     */
    public Object deleteFile(String url) {
        String result = OssUtil.deleteFile(url);
        if (result.equals("success")) {
            return ResponseData.success(result);
        } else {
            return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "删除失败");
        }
    }


}
